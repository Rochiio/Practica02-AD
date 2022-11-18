package models.pedidos

import entities.enums.Estado
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

//TODO hacer serializadores
@Serializable
data class Pedido(
    var uuid: UUID?,
    val estado: Estado,
    val fechaEntrada: LocalDate,
    val fechaSalida: LocalDate,
    val fechaFinal: LocalDate,
    val precioTotal: Float
    val topeEntrega: LocalDate,
    ) {
}