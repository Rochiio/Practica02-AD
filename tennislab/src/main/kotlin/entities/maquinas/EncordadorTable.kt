package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

/**
 * Entidad de encordador para la base de datos.
 */
object EncordadorTable: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val modelo = varchar("modelo", 50)
    val marca = varchar("marca", 50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val disponible = bool("disponible")
    val automatico = bool("automatico")
    val tensionMaxima = integer("tensionMaxima")
    val tensionMinima = integer("tensionMinima")
}


class EncordadorDAO(id : EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<EncordadorDAO>(EncordadorTable)
    var uuid by EncordadorTable.uuid
    var modelo by EncordadorTable.modelo
    var marca by EncordadorTable.marca
    var fechaAdquisicion by EncordadorTable.fechaAdquisicion
    var disponible by EncordadorTable.disponible
    var automatico by EncordadorTable.automatico
    var tensionMaxima by EncordadorTable.tensionMaxima
    var tensionMinima by EncordadorTable.tensionMinima

    override fun toString(): String {
        return "Encordador(uuid=$uuid, modelo='$modelo', marca=$marca, fechaAdquisicion=$fechaAdquisicion," +
                " disponible=$disponible, automatico=$automatico, tensionMaxima=$tensionMaxima, tensionMinima=$tensionMinima)"
    }


}