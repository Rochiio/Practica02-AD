package entities.maquinas

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

/**
 * Entidad de personalizar para la base de datos.
 */
object PersonalizadorTable : IntIdTable("personalizadoras") {
    val uuid = uuid("uuid").autoGenerate()
    val modelo = varchar("modelo", 50)
    val marca = varchar("marca", 50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val disponible = bool("disponible")
    val maniobrabilidad = bool("maniobrabilidad")
    val balance = bool("balance")
    val rigidez = bool("rigidez")

}

class PersonalizadorDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonalizadorDAO>(PersonalizadorTable)

    var uuid by PersonalizadorTable.uuid
    var modelo by PersonalizadorTable.modelo
    var marca by PersonalizadorTable.marca
    var fechaAdquisicion by PersonalizadorTable.fechaAdquisicion
    var disponible by PersonalizadorTable.disponible
    var maniobrabilidad by PersonalizadorTable.maniobrabilidad
    var balance by PersonalizadorTable.balance
    var rigidez by PersonalizadorTable.rigidez

    override fun toString(): String {
        return "Personalizadora(uuid=$uuid, modelo='$modelo', fechaAdquisicion=$fechaAdquisicion," +
                "disponible=$disponible, maniobrabilidad=$maniobrabilidad, balance=$balance, rigidez=$rigidez)"
    }


}