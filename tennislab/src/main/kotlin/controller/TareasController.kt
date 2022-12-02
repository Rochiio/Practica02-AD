package controller

import exception.TareaError
import models.pedidos.Pedido
import models.pedidos.Tarea
import repositories.pedidos.TareaRepository


import java.util.*

class TareasController(private var repository: TareaRepository) {

    /**
     * Añade un pedido
     */
    fun addTarea(tarea: Tarea): Tarea {
        tarea.uuid?.let { repository.findByUUID(it) }
            ?.let {
                repository.save(tarea)
                return tarea
            } ?: run {
            throw TareaError("Ya existe un tarea con el mismo UUID")

        }
    }

    /**
     * Recupera todos los pedidos guardados. Una lista vacía si no hay pedidos
     */
    fun getTareas() = repository.findAll()

    /**
     * Actualiza un pedido ya existente o lo guarda si no existe
     */
    fun updateTarea(tarea: Tarea) = repository.save(tarea)

    /**
     * Elimina un pedido
     */
    fun deleteTarea(Tarea: Tarea): Boolean {
        if (repository.delete(Tarea)) {
            println("Tarea eliminado correctamente")
            return true
        } else {
            throw TareaError("Error al eliminar el cliente")
        }
    }

    /**
     * Busca un pedido por su UUID
     */
    fun getTareaByUUID(uuid: UUID) = repository.findByUUID(uuid)


    fun addPedidoId(tarea : Tarea, pedido : Pedido) : Tarea{
        //tarea.idPedido = pedido.uuid
        val find = getTareaByUUID(tarea.uuid!!)
        find?.let {
            updateTarea(tarea)
        } ?: run{
            addTarea(tarea)
        }
        return tarea
    }
}