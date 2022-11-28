package mappers

import entities.pedidos.TareaDAO
import models.pedidos.Tarea

fun TareaDAO.fromTareaDaoToTarea(): Tarea = Tarea(
    id = id.value,
    uuid = uuid,
    idMaquina = idMaquina,
    idTrabajador = idTrabajador,

    descripcion = descripcion,
    precio = precio,
    tipoTarea = tipoTarea,
    disponible = disponible

)