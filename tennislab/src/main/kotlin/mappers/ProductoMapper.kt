package mappers

import entities.pedidos.ProductoDAO
import models.pedidos.Producto

/**
 * Mappeador de producto DAO a producto
 */
fun ProductoDAO.fromProductoDaoToProducto():Producto{
    return Producto(
        id = id.value,
        uuid = uuid,
        tipo = tipo,
        marca= marca,
        modelo= modelo,
        precio= precio,
        stock= stock
    )
}