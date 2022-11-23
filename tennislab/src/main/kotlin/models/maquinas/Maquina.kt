package models.maquinas

import java.time.LocalDate
import java.util.UUID

/**
 * Modelo maquina
 */
open class Maquina(
    val uuid : UUID?,
    val marca: String,
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val numeroSerie: Int,
    val disponible: Boolean
) {
}