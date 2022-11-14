package models.pedidos

import models.usuarios.Trabajador
import models.usuarios.Trabajadores
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Pedidos: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val trabajador = reference("trabajador",Trabajadores)
    val estado = varchar("estado",10)
    val entrada = date("fechaEntrada")
    val fechaSalida = date("fechaSalida")
    val fechaFinal = date("fechaFinal")
    val precioTotal = float("precioFinal")
    val topeEntrega = date("topeEntrega")
    // TODO tareas
}

class Pedido(id:EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Pedido>(Pedidos)
    var uuid by Pedidos.uuid
    var trabajador by Trabajador referencedOn Pedidos.trabajador
    var estado by Pedidos.estado
    var entrega by Pedidos.entrada
    var fechaSalida by Pedidos.fechaSalida
    var fechaFinal by Pedidos.fechaFinal
    var precioTotal by Pedidos.precioTotal
    var topeEntrega by Pedidos.topeEntrega

    override fun toString(): String {
        return "Pedido(uuid=$uuid, trabajador=$trabajador, estado='$estado', entrega=$entrega, fechaSalida=$fechaSalida, fechaFinal=$fechaFinal, precioTotal=$precioTotal, topeEntrega=$topeEntrega)"
    }


}