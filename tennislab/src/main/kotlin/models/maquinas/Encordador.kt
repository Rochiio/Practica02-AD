package models.maquinas

import entities.enums.TipoMaquina

/**
 * Modelo de encordador
 */
data class Encordador(
    val maquina: Maquina,
    val automatico: TipoMaquina,
    val tensionMaxima: Int,
    val tensionMinima: Int
) {
}