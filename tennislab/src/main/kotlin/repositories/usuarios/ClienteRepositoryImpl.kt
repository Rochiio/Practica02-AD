package repositories.usuarios

import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import mappers.fromClienteDaoToCliente
import models.usuarios.Cliente
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación del repositorio de clientes.
 */
class ClienteRepositoryImpl(
    private var clienteDAO: IntEntityClass<ClienteDAO>,
) : ClienteRepository {

    private var logger = KotlinLogging.logger {}


    /**
     * Buscar un cliente por su id.
     * @param id por el que buscar al cliente.
     * @return cliente o no dependiendo de si existe.
     */
    override fun findById(id: Int): Cliente? = transaction{
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
        clienteDAO.find { ClienteTable.uuid eq uuid }.firstOrNull()?.fromClienteDaoToCliente()
    }


    /**
     * Salvar un cliente.
     * @param item cliente a salvar.
     * @return cliente
     */
    override fun save(item: Cliente): Cliente = transaction{
        logger.debug { "Save cliente" }
        item.id?.let {
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
            nombre = item.nombre
            apellido = item.apellido
            email = item.email
            password = item.password
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
            nombre = item.nombre
            apellido = item.apellido
            email = item.email
            password = item.password
        }.fromClienteDaoToCliente()
    }


    /**
     * Eliminar un cliente.
     * @param item cliente a eliminar.
     * @return boolean si se ha eliminado o no.
     */
    override fun delete(item: Cliente): Boolean = transaction{
        val existe = item.id?.let { clienteDAO.findById(it) } ?: return@transaction false
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