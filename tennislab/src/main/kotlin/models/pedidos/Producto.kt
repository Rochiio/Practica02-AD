package models.pedidos

import kotlinx.serialization.Serializable
import utils.serializer.UUIDSerializer
import java.util.*

@Serializable
data class Producto(
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID,
    val tipo: String,
    val marca: String,
    val modelo: String,
    val precio: Float,
    val stock: Int
) {
}