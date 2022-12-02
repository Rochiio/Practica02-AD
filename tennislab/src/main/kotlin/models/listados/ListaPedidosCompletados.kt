package models.listados

import kotlinx.serialization.Serializable
import models.pedidos.Pedido

/**
 * Listado de pedidos completados, serializable para pasar a JSON.
 */
@Serializable
data class ListaPedidosCompletados(
    private val pedidos: List<Pedido>
) {
}