package models.listados

import kotlinx.serialization.Serializable
import models.pedidos.Pedido

/**
 * Lista de pedidos pendientes, serializable para pasar a JSON.
 */
@Serializable
data class ListaPedidosPendientes(
    private val pedidos: List<Pedido>
    ) {
}