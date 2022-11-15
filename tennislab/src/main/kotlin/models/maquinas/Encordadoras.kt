package models

import models.maquinas.Personalizadoras.references
import models.maquinas.Personalizadoras.uniqueIndex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Encordadoras: IntIdTable() {
    val automatico = bool("automatico")
    val tensionMaxima = integer("tensionMaxima")
    val tensionMinima = integer("tensionMinima")
    val maquinaID = reference( "encordadoras_id", Maquinas)

}


class Encordador(id : EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<Encordador>(Encordadoras)
    var automatico by Encordadoras.automatico
    var tensionMaxima by Encordadoras.tensionMaxima
    var tensionMinima by Encordadoras.tensionMinima
    var maquinaID by Encordadoras.maquinaID
    override fun toString(): String {
        return "Encordador(automatico=$automatico, tensionMaxima=$tensionMaxima, tensionMinima=$tensionMinima, maquina=$maquinaID)"
    }


}