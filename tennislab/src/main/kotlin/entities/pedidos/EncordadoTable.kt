package entities.pedidos

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad encordado para la base de datos.
 */
object EncordadoTable : IntIdTable() {
    val tarea = reference("tarea", TareaTable)
    var tensionH = integer("tensionH")
    var tensionV = integer("tensinV")
    var nudosH = integer("nudosH")
    var nudosV = integer("nudosV")
    var cordajeH = varchar("cordajeH", 50)
    var cordajeV = varchar("cordajeV", 50)

}


class EncordadoDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EncordadoDAO>(EncordadoTable)

    var tarea by TareaDAO referencedOn EncordadoTable.tarea

    var tensionH by EncordadoTable.tensionH
    var tensionV by EncordadoTable.tensionV
    var nudosH by EncordadoTable.nudosH
    var nudosV by EncordadoTable.nudosV

    var cordajeH by EncordadoTable.cordajeH
    var cordajeV by EncordadoTable.cordajeV

}