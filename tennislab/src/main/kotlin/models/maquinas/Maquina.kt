package models.maquinas

import java.time.LocalDate
import java.util.UUID

/**
 * Clase maquina
 */
data class Maquina(
    val uuid : UUID?,
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val disponible: Boolean
) {
}