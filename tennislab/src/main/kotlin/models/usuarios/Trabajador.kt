package models.usuarios

import java.util.UUID

/**
 * Modelo trabajador.
 */
data class Trabajador(
    val uuid : UUID?,
    val usuario: Usuario,
    val administrador: Boolean,
    //val turno: Turno
) {
}