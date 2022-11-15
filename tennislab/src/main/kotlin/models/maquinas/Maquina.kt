package models.maquinas

import java.time.LocalDate

/**
 * Clase maquina
 */
data class Maquina(
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val disponible: Boolean
) {
}