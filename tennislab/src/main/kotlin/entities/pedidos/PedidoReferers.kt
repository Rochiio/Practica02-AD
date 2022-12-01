package entities.pedidos

import entities.pedidos.PedidoRTable.autoGenerate
import entities.pedidos.PedidoTable.autoGenerate
import models.pedidos.Tarea
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object PedidoRTable : IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val estado = varchar("estado", 10)

}

class PedidoRDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PedidoRDAO>(PedidoRTable)

    var uuid by PedidoRTable.uuid

    //var trabajador by TrabajadorDAO referencedOn PedidoTable.trabajador
    var estado by PedidoRTable.estado
    val tareas by TareaRDAO referrersOn TareaRTable.id_pedido
    override fun toString(): String {
        return "PedidoRDAO(uuid=$uuid, estado='$estado', tareas=$tareas)"
    }

}


object TareaRTable : IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    var id_pedido = reference("id_pedido", PedidoRTable)
    val id_trabajador = uuid("id_trabajador")
    val id_maquina = reference("id_maquina", MaquinaRTable)
    val descripcion = varchar("descripcion", 50)
}


class TareaRDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TareaRDAO>(TareaRTable)

    var idPedido by PedidoRDAO referencedOn TareaRTable.id_pedido
    var idTrabajador by TareaRTable.id_trabajador
    var idMaquina by MaquinaRDAO referencedOn TareaRTable.id_maquina
    var descripcion by TareaRTable.descripcion
    override fun toString(): String {
        return "TareaRDAO(idPedido=$idPedido, idTrabajador=$idTrabajador, idMaquina=$idMaquina, descripcion='$descripcion')"
    }

}

object MaquinaRTable : IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val marca = varchar("marca", 10)
}

class MaquinaRDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MaquinaRDAO>(MaquinaRTable)

    var uuid by MaquinaRTable.uuid
    var marca by MaquinaRTable.marca
    override fun toString(): String {
        return "MaquinaRDAO(marca='$marca')"
    }

}

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        var idT = UUID.randomUUID()
        var idM = UUID.randomUUID()

        SchemaUtils.create(TareaRTable, PedidoRTable)
        var m = MaquinaRDAO.new {
            marca = "marca1"
        }
        var p = PedidoRDAO.new {
            estado = "F"
        }

        var t1 = TareaRDAO.new {
            idPedido = p
            idMaquina = m
            idTrabajador = idT
            descripcion = "DEscripcion 1"
        }


        var t2 = TareaRDAO.new {
            idPedido = p
            idMaquina = m
            idTrabajador = idT
            descripcion = "DEscripcion 1"
        }

        println("PEDIDO -> $p")
        p.tareas.forEach { println(it) }
    }
}


