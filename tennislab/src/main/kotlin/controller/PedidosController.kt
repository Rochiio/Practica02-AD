package controller

import exception.PedidoError
import models.pedidos.Pedido
import models.pedidos.Tarea
import repositories.pedidos.PedidoRepository
import repositories.pedidos.TareaRepository
import java.util.UUID

class PedidosController(private var repository: PedidoRepository, private var tareaRepository: TareaRepository) {

    /**
     * Añade un pedido
     */
    @Throws(PedidoError::class)
    fun addPedido(pedido: Pedido): Pedido {
        val find = pedido.uuid?.let { repository.findByUUID(it) }
        find?.let {

            throw PedidoError("Ya existe un pedido con el mismo UUID")

        } ?: run {
            return repository.save(pedido)

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
    @Throws(PedidoError::class)
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

    /*@Throws(PedidoError::class)
    fun addTareasToPedido(pedido: Pedido, tareas: ArrayList<Tarea>) {
        val found = pedido.uuid?.let { repository.findByUUID(it) }
        found?.let {
            //asigna a la tarea el pedido en el que está
            tareas.forEach { it.idPedido = pedido.uuid }
            pedido.tareas = tareas
            //actualiza el pedido
            updatePedido(pedido)
        } ?: run {
            throw PedidoError("No existe el pedido")
        }
    }

    fun addTareaToPedido(pedido: Pedido, tarea: Tarea) : Pedido{
        val found = pedido.uuid?.let { repository.findByUUID(it) }
        found?.let {
            //asigna a la tarea el pedido en el que está
            tarea.idPedido = pedido.uuid
            tareaRepository.save(tarea)
            pedido.tareas.add(tarea)
            //actualiza el pedido
            repository.save(pedido)
            return pedido
        } ?: run {
            throw PedidoError("No existe el pedido")
        }
    }*/
}