package entities.pedidos

import entities.EncordadorTable.autoGenerate
import entities.enums.TipoProduct
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * Entidad producto para la base de datos.
 */
object ProductoTable: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val tipo = enumeration("tipoProducto",TipoProduct::class)
    val marca = varchar("marca",100)
    val modelo = varchar("modelo",100)
    val precio = float("precio")
    val stock = integer("stock")
}

class ProductoDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<ProductoDAO>(ProductoTable)
    var uuid by ProductoTable.uuid
    var tipo by ProductoTable.tipo
    var marca by ProductoTable.marca
    var modelo by ProductoTable.modelo
    var precio by ProductoTable.precio
    var stock by ProductoTable.stock
    override fun toString(): String {
        return "ProductoDAO(uuid=$uuid, tipo=$tipo, marca='$marca', modelo='$modelo', precio=$precio, stock=$stock)"
    }


}