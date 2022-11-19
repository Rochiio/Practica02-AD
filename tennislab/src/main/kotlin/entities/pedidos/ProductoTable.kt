package entities.pedidos

import entities.enums.TipoProduct
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductoTable: UUIDTable() {
    val tipo = enumeration("tipoProducto",TipoProduct::class)
    val marca = varchar("marca",100)
    val modelo = varchar("modelo",100)
    val precio = float("precio")
    val stock = integer("stock")
}

class ProductoDAO(id: EntityID<UUID>): UUIDEntity(id){
    companion object: UUIDEntityClass<ProductoDAO>(ProductoTable)
    var tipo by ProductoTable.tipo
    var marca by ProductoTable.marca
    var modelo by ProductoTable.modelo
    var precio by ProductoTable.precio
    var stock by ProductoTable.stock
    override fun toString(): String {
        return "ProductoDAO(tipo=$tipo, marca='$marca', modelo='$modelo', precio=$precio, stock=$stock)"
    }


}