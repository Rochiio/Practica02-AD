package models.listados

import kotlinx.serialization.Serializable
import models.usuarios.Trabajador
import utils.serializer.LocalDateSerializer
import utils.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*

/**
 * Clase de asignacion de encordadores a pedidos para la lista.
 */
@Serializable
data class AsignacionesEncordadores(
    @Serializable(with = UUIDSerializer::class)
    val idPedido: UUID,
    val encordador: Trabajador,
    @Serializable(with= LocalDateSerializer::class)
    val fecha: LocalDate = LocalDate.now()
) {
}