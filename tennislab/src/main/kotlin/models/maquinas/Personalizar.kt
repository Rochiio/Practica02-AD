package models.maquinas

/**
 * Clase personalizar.
 */
data class Personalizar(
    val maquina: Maquina,
    val maniobrabilidad: Boolean,
    val balance: Boolean,
    val rigidez: Boolean
) {
}