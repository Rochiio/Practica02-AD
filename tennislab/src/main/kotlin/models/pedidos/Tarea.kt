package models.pedidos

import entities.enums.TipoTarea
import entities.pedidos.TareaTable
import entities.pedidos.TareaTable.autoGenerate
import entities.pedidos.TareaTable.nullable
import java.util.*

data class Tarea(
    var uuid : UUID?,
    var precio : Long,
    var raqueta : String?,
    var tipoTarea : TipoTarea
)