package models.maquinas

import models.Maquina
import models.Maquinas
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Personalizadoras : IntIdTable("personalizadoras") {

    val automatico = bool("automatico")
    val tensionMaxima = integer("tensionMaxima")
    val tensionMinima = integer("tensionMinima")
    val maniobrabilidad = integer("maniobrabilidad")
    val balance = float("balance")
    val rigidez = integer("rigidez")
    val maquina = uuid("uuid")
        .uniqueIndex().references(Maquinas.uuid)
}

class Personalizadora(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Personalizadora>(Personalizadoras)

    val automatico by Personalizadoras.automatico
    val tensionMaxima by Personalizadoras.tensionMaxima
    val tensionMinima by Personalizadoras.tensionMinima
    var maniobrabilidad by Personalizadoras.maniobrabilidad
    var balance by Personalizadoras.balance
    var rigidez by Personalizadoras.rigidez
    var maquina by Personalizadoras.maquina
    override fun toString(): String {
        return "Personalizadora(automatico=$automatico, tensionMaxima=$tensionMaxima, tensionMinima=$tensionMinima, maniobrabilidad=$maniobrabilidad, balance=$balance, rigidez=$rigidez, maquina=$maquina)"
    }


}