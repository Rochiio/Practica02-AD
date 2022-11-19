package entities.pedidos

import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
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
object PedidoTable: UUIDTable() {
    //val uuid = uuid("uuid").autoGenerate()
    //val trabajador = reference("trabajador",TrabajadorTable)
    val estado = varchar("estado",10)
    val fechaEntrada = date("fechaEntrada")
    val fechaSalida = date("fechaSalida")
    val fechaFinal = date("fechaFinal")
    val precioTotal = float("precioFinal")
    val topeEntrega = date("topeEntrega")
    // TODO tareas
}

class PedidoDAO(id:EntityID<UUID>): UUIDEntity(id){
    companion object: UUIDEntityClass<PedidoDAO>(PedidoTable)
    // uuid by PedidoTable.uuid
    //var trabajador by TrabajadorDAO referencedOn PedidoTable.trabajador
    var estado by PedidoTable.estado
    var fechaEntrada by PedidoTable.fechaEntrada
    var fechaSalida by PedidoTable.fechaSalida
    var fechaFinal by PedidoTable.fechaFinal
    var precioTotal by PedidoTable.precioTotal
    var topeEntrega by PedidoTable.topeEntrega

    override fun toString(): String {
        return "Pedido(uuid=${id.value}, estado='$estado', entrega=$fechaEntrada, fechaSalida=$fechaSalida, fechaFinal=$fechaFinal, precioTotal=$precioTotal, topeEntrega=$topeEntrega)"
    }


}