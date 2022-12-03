package repositories.pedidos

import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import mappers.fromPedidoDaoToPedido
import models.pedidos.Pedido
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Repositorio de pedidos
 */
class PedidoRepositoryImpl(
    private var pedidoDAO: IntEntityClass<PedidoDAO>
) : PedidoRepository {
    private var logger = KotlinLogging.logger {}


    override fun findById(id: Int): Pedido? = transaction {
        logger.debug { "buscando pedido por id" }
        pedidoDAO.findById(id)?.fromPedidoDaoToPedido()
    }

    override fun findByUUID(uuid: UUID): Pedido? = transaction {
        logger.debug { "buscando pedido por uuid" }
        pedidoDAO.find { PedidoTable.uuid eq uuid }.first().fromPedidoDaoToPedido()
    }

    override fun save(item: Pedido): Pedido = transaction {
        logger.debug { "Save pedido" }
        var result : PedidoDAO? = null
        item.uuid?.let {
            result = pedidoDAO.find { PedidoTable.uuid eq it }.firstOrNull()
        }

        println(result)
        result?.let {
            update(item, result!!)
        } ?: run {
            add(item)
        }
    }

    fun update(item: Pedido, updateItem: PedidoDAO): Pedido = transaction {
        logger.debug { "acualizando peedido" }
        updateItem.apply {
            estado = item.estado.toString()
            fechaSalida = item.fechaSalida
            fechaEntrada = item.fechaEntrada
            fechaFinal = item.fechaFinal
            precioTotal = item.precioTotal
            topeEntrega = item.topeEntrega


        }.fromPedidoDaoToPedido()
    }

    override fun add(item: Pedido): Pedido = transaction{
        logger.debug { "añadiendo pedido" }
        pedidoDAO.new {
            estado = item.estado.toString()
            fechaSalida = item.fechaSalida
            fechaEntrada = item.fechaEntrada
            fechaFinal = item.fechaFinal
            precioTotal = item.precioTotal
            topeEntrega = item.topeEntrega
            cliente = ClienteDAO.find { ClienteTable.uuid eq item.cliente?.uuid!! }.first()
        }.fromPedidoDaoToPedido()
    }

    override fun delete(item: Pedido): Boolean = transaction{
        val existe = item.uuid?.let {
            pedidoDAO.find { PedidoTable.uuid eq item.uuid!! }
        } ?: return@transaction false
        logger.debug { "eliminando pedido" }
        existe.first().delete()

        return@transaction true
    }

    override fun findAll(): List<Pedido> = transaction{
        logger.debug { "recuperando todos los pedidos" }
        pedidoDAO.all().map { it.fromPedidoDaoToPedido() }
    }

    override fun deleteAll(): Boolean = transaction{
        logger.debug { "eliminando todos los pedidos" }
        val num = PedidoTable.deleteAll()
        return@transaction num > 0
    }
}