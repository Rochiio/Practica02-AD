package repositories.usuarios

import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import entities.usuarios.TrabajadorTable
import entities.usuarios.UsuarioDAO
import mappers.fromClienteDaoToCliente
import mappers.fromUsuarioDaoToUsuario
import models.usuarios.Cliente
import mu.KotlinLogging
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ClienteRepositoryImpl(
    private var clienteDAO: UUIDEntityClass<ClienteDAO>,
    private var usuarioDAO: UUIDEntityClass<UsuarioDAO>
) : ClienteRepository {

    private var logger = KotlinLogging.logger {}


    override fun findById(id: UUID): Cliente? = transaction{
        logger.debug { "buscando Cliente con uuid: $id" }
        clienteDAO.findById(id)?.fromClienteDaoToCliente()
    }

    override fun findByUUID(uuid: UUID): Cliente? = transaction{
        logger.debug { "buscando Cliente con uuid: $uuid" }
        clienteDAO.findById(uuid)?.fromClienteDaoToCliente()
    }

    override fun save(item: Cliente): Cliente = transaction{
        logger.debug { "Save cliente" }
        item.uuid?.let {
            clienteDAO.findById(it)?.let { update ->
                update(item, update)
            }
        } ?: run {
            add(item)
        }
    }

    override fun add(item: Cliente): Cliente = transaction{
        logger.debug { "Add cliente"}
        clienteDAO.new {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
        }.fromClienteDaoToCliente()
    }

    fun update(item: Cliente, updateItem: ClienteDAO): Cliente = transaction{
        logger.debug { "actualizando cliente" }
        updateItem.apply {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
        }.fromClienteDaoToCliente()
    }

    override fun delete(item: Cliente): Boolean = transaction{
        val existe = item.uuid?.let { clienteDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando cliente: $item" }
        existe.delete()
        return@transaction true
    }

    override fun findAll(): List<Cliente> = transaction{
        logger.debug { "Buscando todos los clientes"}
        clienteDAO.all().map { it.fromClienteDaoToCliente()}.toList()
    }


    override fun deleteAll(): Boolean = transaction{
        logger.debug { "Eliminando todos los clientes"}
        var num = ClienteTable.deleteAll()
        return@transaction num>0
    }

}