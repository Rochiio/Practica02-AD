package entities

import entities.maquinas.MaquinaDAO
import entities.maquinas.MaquinaTable
import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

/**
 * Entidad turno para la base de datos.
 */
object TurnoTable: UUIDTable() {
    var iID : Column<Int> = integer("iID").autoIncrement()
    val comienzoTurno = varchar("comienzoTurno",5)
    val finTurno = varchar("finTurno",5)
    val maquina = reference("maquina", MaquinaTable).nullable()
    var pedidos = reference("pedidos", PedidoTable).nullable()
}

class TurnoDAO(id: EntityID<UUID>): UUIDEntity(id){
    companion object: UUIDEntityClass<TurnoDAO>(TurnoTable)
    var iID by TurnoTable.iID
    var comienzoTurno by TurnoTable.comienzoTurno
    var finTurno by TurnoTable.finTurno
    var maquina by MaquinaDAO optionalReferencedOn TurnoTable.maquina
    var pedidos by PedidoDAO optionalReferencedOn TurnoTable.pedidos

    override fun toString(): String {
        return "Turno(uuid=$id, comienzoTurno='$comienzoTurno', finTurno='$finTurno', maquina=$maquina, pedidos=$pedidos)"
    }

}