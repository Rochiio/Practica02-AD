package models.pedidos

import kotlinx.serialization.Serializable

@Serializable
data class ListaPedidosCompletados(
    val pedidos: List<Pedido>
) {
}