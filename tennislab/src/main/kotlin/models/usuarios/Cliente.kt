package models.usuarios

import java.util.*

/**
 * Modelo de cliente.
 */
data class Cliente(
    var id: Int?,
    var uuid: UUID?,
    var nombre: String,
    var apellido:String,
    var email:String,
    var password:String
    //val pedidos: List<Pedido> TODO hacer modelo de pedidos
) {
}