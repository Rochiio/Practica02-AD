package entities.pedidos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object AdquisicionesTable : IntIdTable() {
    var tarea = reference("tarea", TareaTable)
    var producto = varchar("producto", 50)
}

class AdquisicionDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AdquisicionDAO>(AdquisicionesTable)

    val tarea by TareaDAO referencedOn AdquisicionesTable.tarea
    var product by AdquisicionesTable.producto
}