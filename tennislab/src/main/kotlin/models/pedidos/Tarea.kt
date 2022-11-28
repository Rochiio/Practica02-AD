package models.pedidos

import entities.enums.TipoTarea
import entities.pedidos.TareaTable
import entities.pedidos.TareaTable.autoGenerate
import entities.pedidos.TareaTable.nullable
import kotlinx.serialization.Serializable
import utils.serializer.UUIDSerializer
import java.util.*

@Serializable
data class Tarea(
    var id: Int?,
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID?,
    @Serializable(with = UUIDSerializer::class)
    var idTrabajador: UUID?,
    @Serializable(with = UUIDSerializer::class)
    var idMaquina: UUID?,
    var descripcion: String,
    var precio : Long,
    var tipoTarea: TipoTarea,
    var disponible: Boolean
)