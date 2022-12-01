package controller

import exception.PedidoError
import models.pedidos.Pedido
import repositories.pedidos.PedidoRepository
import java.util.UUID

class PedidosController(private var repository: PedidoRepository) {

    /**
     * Añade un pedido
     */
    fun addPedido(pedido: Pedido): Pedido {
        pedido.uuid?.let { repository.findByUUID(it) }
            ?.let {
                throw PedidoError("Ya existe un pedido con el mismo UUID")
            } ?: run {
            repository.save(pedido)
            return pedido
        }
    }

    /**
     * Recupera todos los pedidos guardados. Una lista vacía si no hay pedidos
     */
    fun getPedidos() = repository.findAll()

    /**
     * Actualiza un pedido ya existente o lo guarda si no existe
     */
    fun updatePedido(pedido: Pedido) = repository.save(pedido)

    /**
     * Elimina un pedido
     */
    fun deletePedido(pedido: Pedido): Boolean {
        if (repository.delete(pedido)) {
            println("Pedido eliminado correctamente")
            return true
        } else {
            throw PedidoError("Error al eliminar el cliente")
        }
    }

    /**
     * Busca un pedido por su UUID
     */
    fun getPedidoByUUID(uuid: UUID) = repository.findByUUID(uuid)
}