package models.pedidos

import entities.enums.TipoProduct
import kotlinx.serialization.Serializable
import utils.serializer.UUIDSerializer
import java.util.*

/**
 * Modelo de producto. Con serializacion para pasar a JSON.
 */
@Serializable
data class Producto(
    val id:Int?,
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID?,
    val tipo: TipoProduct,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val stock: Int
) {
}