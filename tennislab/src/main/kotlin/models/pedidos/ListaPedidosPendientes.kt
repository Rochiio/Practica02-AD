package models.pedidos

import kotlinx.serialization.Serializable

/**
 * Lista de pedidos pendientes, serializable para pasar a JSON.
 */
@Serializable
data class ListaPedidosPendientes(
    val pedidos: List<Pedido>
    ) {
}