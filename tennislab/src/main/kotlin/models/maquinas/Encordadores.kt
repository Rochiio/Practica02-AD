package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Encordadores: IntIdTable() {
    val automatico = bool("automatico")
    val tensionMaxima = integer("tensionMaxima")
    val tensionMinima = integer("tensionMinima")
    val maquina = uuid("uuid")
}


class Encordador(id : EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<Encordador>(Encordadores)
    var modelo by Maquinas.modelo
    var uuid by Maquinas.uuid
    var fechaAdquisicion by Maquinas.fechaAdquisicion
    var disponible by Maquinas.disponible
    var automatico by Encordadores.automatico
    var tensionMaxima by Encordadores.tensionMaxima
    var tensionMinima by Encordadores.tensionMinima
    //var maquina by Maquina referencedOn Encordadores.maquina

    override fun toString(): String {
        return "Encordador(modelo='$modelo', uuid=$uuid, fechaAdquisicion=$fechaAdquisicion, disponible=$disponible, automatico=$automatico, tensionMaxima=$tensionMaxima, tensionMinima=$tensionMinima)"
    }


}