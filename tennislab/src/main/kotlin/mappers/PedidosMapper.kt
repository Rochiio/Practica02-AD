package mappers

import entities.pedidos.TareaDAO
import models.pedidos.Tarea

fun TareaDAO.fromTareaDaoToTarea() : Tarea = Tarea(
    uuid = uuid,
    precio = precio,
    raqueta = raqueta,
    tipoTarea = tipoTarea
)