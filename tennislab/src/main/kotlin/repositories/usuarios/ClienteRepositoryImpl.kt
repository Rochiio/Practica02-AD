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

/**
 * Implementación del repositorio de clientes.
 */
class ClienteRepositoryImpl(
    private var clienteDAO: UUIDEntityClass<ClienteDAO>,
    private var usuarioDAO: UUIDEntityClass<UsuarioDAO>
) : ClienteRepository {

    private var logger = KotlinLogging.logger {}


    /**
     * Buscar un cliente por su id.
     * @param id por el que buscar al cliente.
     * @return cliente o no dependiendo de si existe.
     */
    override fun findById(id: UUID): Cliente? = transaction{
        logger.debug { "buscando Cliente con uuid: $id" }
        clienteDAO.findById(id)?.fromClienteDaoToCliente()
    }


    /**
     * Buscar un cliente por su uuid.
     * @param uuid por el que buscar el cliente.
     * @return cliente o no depenidendo de si exsite.
     */
    override fun findByUUID(uuid: UUID): Cliente? = transaction{
        logger.debug { "buscando Cliente con uuid: $uuid" }
        clienteDAO.findById(uuid)?.fromClienteDaoToCliente()
    }


    /**
     * Salvar un cliente.
     * @param item cliente a salvar.
     * @return cliente
     */
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


    /**
     * Añadir un cliente.
     * @param item cliente a añadir.
     * @return cliente
     */
    override fun add(item: Cliente): Cliente = transaction{
        logger.debug { "Add cliente"}
        clienteDAO.new {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
        }.fromClienteDaoToCliente()
    }


    /**
     * Actualizar un cliente.
     * @param item cliente modelo actualizado.
     * @param updateItem cliente DAO a actualizar.
     * @return cliente
     */
    fun update(item: Cliente, updateItem: ClienteDAO): Cliente = transaction{
        logger.debug { "actualizando cliente" }
        updateItem.apply {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
        }.fromClienteDaoToCliente()
    }


    /**
     * Eliminar un cliente.
     * @param item cliente a eliminar.
     * @return boolean si se ha eliminado o no.
     */
    override fun delete(item: Cliente): Boolean = transaction{
        val existe = item.uuid?.let { clienteDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando cliente: $item" }
        existe.delete()
        return@transaction true
    }


    /**
     * Buscar todos los clientes que hay.
     * @return lista de clientes.
     */
    override fun findAll(): List<Cliente> = transaction{
        logger.debug { "Buscando todos los clientes"}
        clienteDAO.all().map { it.fromClienteDaoToCliente()}.toList()
    }


    /**
     * Eliminar todos los clientes que existen.
     * @return boolean dependiendo de si se han eliminado o no.
     */
    override fun deleteAll(): Boolean = transaction{
        logger.debug { "Eliminando todos los clientes"}
        var num = ClienteTable.deleteAll()
        return@transaction num>0
    }

}