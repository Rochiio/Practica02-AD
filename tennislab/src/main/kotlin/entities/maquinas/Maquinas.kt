package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.UUID

/**
 * Entidad de maquinas para la base de datos.
 */
object MaquinaTable : UUIDTable("maquinas") {
    //val uuid = uuid("uuid").autoGenerate()
    val modelo = varchar("modelo", 50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val disponible = bool("disponible")
}

class MaquinaDAO(id : EntityID<UUID>) : UUIDEntity(id){
    companion object : UUIDEntityClass<MaquinaDAO>(MaquinaTable)

    //var uuid by MaquinaTable.uuid
    var modelo by MaquinaTable.modelo
    var fechaAdquisicion by MaquinaTable.fechaAdquisicion
    var disponible by MaquinaTable.disponible
    override fun toString(): String {
        return "Maquina(uuid=$id, modelo='$modelo', fechaAdquisicion=$fechaAdquisicion, disponible=$disponible)"
    }


}

