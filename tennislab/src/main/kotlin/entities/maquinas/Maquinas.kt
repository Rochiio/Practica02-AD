package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

/**
 * Entidad de maquinas para la base de datos.
 */
object MaquinaTable : IntIdTable("maquinas") {
    val uuid = uuid("uuid").autoGenerate()
    val modelo = varchar("modelo", 50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val disponible = bool("disponible")
}

class MaquinaDAO(id : EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<MaquinaDAO>(MaquinaTable)

    var uuid by MaquinaTable.uuid
    var modelo by MaquinaTable.modelo
    var fechaAdquisicion by MaquinaTable.fechaAdquisicion
    var disponible by MaquinaTable.disponible
    override fun toString(): String {
        return "Maquina(uuid=$uuid, modelo='$modelo', fechaAdquisicion=$fechaAdquisicion, disponible=$disponible)"
    }


}

