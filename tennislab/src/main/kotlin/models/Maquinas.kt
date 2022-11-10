package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.UUID

object Maquinas : IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val modelo = varchar("modelo", 50)
    val fechaAdquisicion = date("fechaAdquisicion")
    val disponible = bool("disponible")
}

class Maquina(id : EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<Maquina>(Maquinas)

    var uuid by Maquinas.uuid
    var modelo by Maquinas.modelo
    var fechaAdquisicion by Maquinas.fechaAdquisicion
    var disponible by Maquinas.disponible


    override fun toString(): String {
        return "Maquina(uuid=$uuid, modelo='$modelo', fechaAdquisicion=$fechaAdquisicion)"
    }
}

