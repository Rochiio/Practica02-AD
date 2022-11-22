package models.pedidos

import kotlinx.serialization.Serializable

/**
 * Listado de pedidos completados, serializable para pasar a JSON.
 */
@Serializable
data class ListaPedidosCompletados(
    val pedidos: List<Pedido>
) {
}