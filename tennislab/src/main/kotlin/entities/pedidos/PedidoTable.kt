package entities.pedidos

import entities.EncordadorTable.nullable
import entities.pedidos.PedidoDAO.Companion.referrersOn
import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.pedidos.Tarea
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

/**
 * Entidad pedido para la  base de datos.
 */
object PedidoTable : IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()

    val estado = varchar("estado", 10)
    val cliente = reference("cliente", ClienteTable).nullable()
    val fechaEntrada = date("fechaEntrada")
    val fechaSalida = date("fechaSalida").nullable()
    val fechaFinal = date("fechaFinal").nullable()
    val precioTotal = float("precioFinal")
    val topeEntrega = date("topeEntrega")
}

class PedidoDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PedidoDAO>(PedidoTable)

    var uuid by PedidoTable.uuid

    //var trabajador by TrabajadorDAO referencedOn PedidoTable.trabajador
    var estado by PedidoTable.estado
    var fechaEntrada by PedidoTable.fechaEntrada
    var fechaSalida by PedidoTable.fechaSalida
    var fechaFinal by PedidoTable.fechaFinal
    var precioTotal by PedidoTable.precioTotal
    var topeEntrega by PedidoTable.topeEntrega
    var cliente by ClienteDAO optionalReferencedOn  PedidoTable.cliente
    val tareas by TareaDAO optionalReferrersOn  TareaTable.id_pedido

    override fun toString(): String {
        return "Pedido(uuid=$uuid, estado='$estado', entrega=$fechaEntrada, fechaSalida=$fechaSalida, fechaFinal=$fechaFinal, precioTotal=$precioTotal, topeEntrega=$topeEntrega, tareas=${tareas.count()})"
    }
    fun getTareas() : String {
        var s = ""
        tareas.forEach { s += it }
        return s
    }


}