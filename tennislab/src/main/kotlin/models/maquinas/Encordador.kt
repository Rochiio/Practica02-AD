package models.maquinas

import entities.enums.TipoMaquina
import java.time.LocalDate
import java.util.*

/**
 * Modelo de encordador
 */
data class Encordador(
    var uuid : UUID?,
    val marca: String,
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val disponible: Boolean,
    val automatico: TipoMaquina,
    val tensionMaxima: Int,
    val tensionMinima: Int
)