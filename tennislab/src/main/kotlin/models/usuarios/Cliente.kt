package models.usuarios

import java.util.*

/**
 * Modelo de cliente.
 */
data class Cliente(
    var uuid: UUID?,
    val usuario: Usuario,
    //val pedidos: List<Pedido> TODO hacer modelo de pedidos
) {
}