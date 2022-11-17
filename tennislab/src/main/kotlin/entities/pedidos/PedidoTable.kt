package entities.pedidos

import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object PedidoTable: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val trabajador = reference("trabajador",TrabajadorTable)
    val estado = varchar("estado",10)
    val entrada = date("fechaEntrada")
    val fechaSalida = date("fechaSalida")
    val fechaFinal = date("fechaFinal")
    val precioTotal = float("precioFinal")
    val topeEntrega = date("topeEntrega")
    // TODO tareas
}

class PedidoDAO(id:EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<PedidoDAO>(PedidoTable)
    var uuid by PedidoTable.uuid
    var trabajador by TrabajadorDAO referencedOn PedidoTable.trabajador
    var estado by PedidoTable.estado
    var entrega by PedidoTable.entrada
    var fechaSalida by PedidoTable.fechaSalida
    var fechaFinal by PedidoTable.fechaFinal
    var precioTotal by PedidoTable.precioTotal
    var topeEntrega by PedidoTable.topeEntrega

    override fun toString(): String {
        return "Pedido(uuid=$uuid, trabajador=$trabajador, estado='$estado', entrega=$entrega, fechaSalida=$fechaSalida, fechaFinal=$fechaFinal, precioTotal=$precioTotal, topeEntrega=$topeEntrega)"
    }


}