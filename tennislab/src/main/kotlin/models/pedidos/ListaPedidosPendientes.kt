package models.pedidos

import kotlinx.serialization.Serializable

@Serializable
data class ListaPedidosPendientes(
    val pedidos: List<Pedido>
    ) {
}