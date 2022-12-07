package entities.maquinas

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

/**
 * Entidad de maquinas para la base de datos.
 * TODO Se deberia de quitar
 */
object MaquinaTable : UUIDTable("maquinas") {
    //val uuid = uuid("uuid").autoGenerate()
    val marca = varchar("marca", 50)
    val modelo = varchar("modelo", 50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val numeroSerie = integer("numeroSerie")
    val disponible = bool("disponible")
}

class MaquinaDAO(id : EntityID<UUID>) : UUIDEntity(id){
    companion object : UUIDEntityClass<MaquinaDAO>(MaquinaTable)

    //var uuid by MaquinaTable.uuid
    var marca by MaquinaTable.marca
    var modelo by MaquinaTable.modelo
    var fechaAdquisicion by MaquinaTable.fechaAdquisicion
    var numeroSerie by MaquinaTable.numeroSerie
    var disponible by MaquinaTable.disponible

    override fun toString(): String {
        return "MaquinaDAO(marca='$marca', modelo='$modelo', fechaAdquisicion=$fechaAdquisicion, numeroSerie=$numeroSerie, disponible=$disponible)"
    }


}

