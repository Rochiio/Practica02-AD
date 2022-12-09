package models.usuarios

import kotlinx.serialization.Serializable
import models.pedidos.Pedido
import utils.serializer.UUIDSerializer
import java.util.*

/**
 * Modelo de cliente.
 */
@Serializable
data class Cliente(
    var id : Int?,
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID?,
    var nombre: String,
    var apellido:String,
    var email:String,
    var password:String,
    var pedidos: Pedido?
) {
    override fun toString(): String {
        return "Cliente(id=$id, uuid=$uuid, nombre='$nombre', apellido='$apellido', email='$email', password='$password', pedidos=$pedidos)"
    }
}