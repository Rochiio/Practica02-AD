package models.pedidos

import entities.enums.Estado
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.serializer
import utils.serializer.LocalDateSerializer
import utils.serializer.UUIDSerializer
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

/**
 * Modelo de pedido. Con serializacion para pasar a JSON.
 */
@Serializable
data class Pedido(
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID?,
    val estado: Estado,
    @Serializable(with = LocalDateSerializer::class)
    val fechaEntrada: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val fechaSalida: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val fechaFinal: LocalDate,
    val precioTotal: Float,
    @Serializable(with = LocalDateSerializer::class)
    val topeEntrega: LocalDate,
    var tareas : ArrayList<Tarea>
    ) {
}