package models.maquinas

import models.Maquina
import models.Maquinas
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Personalizaciones: IntIdTable() {
    val maniobrabilidad = integer("maniobrabilidad")
    val balance = float("balance")
    val rigidez = integer("rigidez")
    val maquina = reference("maquina_id", Maquinas)
}

class Personalizacion(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Personalizacion>(Personalizaciones)
    var maniobrabilidad by Personalizaciones.maniobrabilidad
    var balance by Personalizaciones.balance
    var rigidez by Personalizaciones.rigidez
    var maquina by Maquina referencedOn Personalizaciones.maquina
    override fun toString(): String {
        return "Personalizacion(maniobrabilidad=$maniobrabilidad kg, balance=$balance cm, rigidez=$rigidez, maquina=$maquina)"
    }

}