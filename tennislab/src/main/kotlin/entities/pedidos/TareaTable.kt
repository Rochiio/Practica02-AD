package entities.pedidos

import entities.enums.TipoTarea
import models.pedidos.Tarea
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad tarea para la base de datos.
 */
object TareaTable : IntIdTable(){
    var uuid = uuid("uuid").autoGenerate()
    var precio = long("precio")
    var raqueta = varchar("raqueta", 50)
    var tipoTarea = enumeration("tipoTarea", TipoTarea::class)
    var disponible = bool("disponible")
}

class TareaDAO(id : EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<TareaDAO>(TareaTable)
    var uuid by TareaTable.uuid
    var precio by TareaTable.precio
    var raqueta by TareaTable.raqueta
    var tipoTarea by TareaTable.tipoTarea
    var disponible by TareaTable.disponible

    override fun toString(): String {
        return "Tarea(uuid=$uuid, precio=$precio, raqueta='$raqueta', tipoTarea=$tipoTarea, disponible=$disponible)"
    }


}