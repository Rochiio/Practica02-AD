package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Encordadores: IntIdTable() {
    val automatico = bool("automatico")
    val tensionMaxima = integer("tensionMaxima")
    val tensionMinima = integer("tensionMinima")
    val maquina = reference("maquinas_uuid", Maquinas)
}


class Encordador(id : EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<Encordador>(Encordadores)
    var automatico by Encordadores.automatico
    var tensionMaxima by Encordadores.tensionMaxima
    var tensionMinima by Encordadores.tensionMinima
    var maquina by Maquina referencedOn Encordadores.maquina

    override fun toString(): String {
        return "Encordador(automatico=$automatico, tensionMaxima=$tensionMaxima kg, tensionMinima=$tensionMinima kg, maquina=$maquina)"
    }


}