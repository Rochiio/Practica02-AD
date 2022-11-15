package entities

import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TurnoTable: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val comienzoTurno = varchar("comienzoTurno",5)
    val finTurno = varchar("finTurno",5)
    val maquina = reference("maquina",MaquinaTable)
    val pedidos = reference("pedidos", PedidoTable)
}

class TurnoDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<TurnoDAO>(TurnoTable)
    val uuid by TurnoTable.uuid
    var comienzoTurno by TurnoTable.comienzoTurno
    var finTurno by TurnoTable.finTurno
    var maquina by MaquinaDAO referencedOn TurnoTable.maquina
    val pedidos by PedidoDAO referrersOn TurnoTable.pedidos

    override fun toString(): String {
        return "Turno(uuid=$uuid, comienzoTurno='$comienzoTurno', finTurno='$finTurno', maquina=$maquina, pedidos=$pedidos)"
    }

}