package entities.pedidos

import entities.enums.TipoTarea
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad tarea para la base de datos.
 */
object TareaTable : IntIdTable(){
    var precio = long("precio")
    var raqueta = varchar("raqueta", 50).nullable()
    var tipoTarea = enumeration("tipoTarea", TipoTarea::class)
}

class TareaDAO(id : EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<TareaDAO>(TareaTable)
    var precio by TareaTable.precio
    var raqueta by TareaTable.raqueta
    var tipoTarea by TareaTable.tipoTarea

    override fun toString(): String {
        return "Tarea(precio=$precio, raqueta='$raqueta', tipoTarea=$tipoTarea)"
    }


}