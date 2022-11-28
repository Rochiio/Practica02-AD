package models.maquinas

import java.time.LocalDate
import java.util.*

/**
 * Modelo personalizar.
 */
data class Personalizadora(
    var uuid : UUID?,
    val marca: String,
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val disponible: Boolean,
    val maniobrabilidad: Boolean,
    val balance: Boolean,
    val rigidez: Boolean
) {
}