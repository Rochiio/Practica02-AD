package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Turnos: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val comienzoTurno = varchar("comienzoTurno",5)
    val finTurno = varchar("finTurno",5)
    val maquina = reference("maquina",Maquinas)
    //TODO falta los pedidos
}

class Turno(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Turno>(Turnos)
    val uuid by Turnos.uuid
    var comienzoTurno by Turnos.comienzoTurno
    var finTurno by Turnos.finTurno
    var maquina by Maquina referencedOn Turnos.maquina
    override fun toString(): String {
        return "Turno(uuid=$uuid, comienzoTurno='$comienzoTurno', finTurno='$finTurno', maquina=$maquina)"
    }

}