package entities.maquinas

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad de personalizar para la base de datos.
 */
object PersonalizadorTable : IntIdTable("personalizadoras") {

    val maquina = reference("maquina", MaquinaTable)
    val maniobrabilidad = bool("maniobrabilidad")
    val balance = bool("balance")
    val rigidez = bool("rigidez")

}

class PersonalizadorDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonalizadorDAO>(PersonalizadorTable)
    val maquina by MaquinaDAO referencedOn PersonalizadorTable.maquina
    var maniobrabilidad by PersonalizadorTable.maniobrabilidad
    var balance by PersonalizadorTable.balance
    var rigidez by PersonalizadorTable.rigidez

    override fun toString(): String {
        return "Personalizadora(maquina=$maquina, maniobrabilidad=$maniobrabilidad, balance=$balance, rigidez=$rigidez)"
    }


}