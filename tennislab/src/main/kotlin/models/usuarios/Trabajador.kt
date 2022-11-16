package models.usuarios

import models.Turno

/**
 * Clase trabajador.
 */
data class Trabajador(
    val usuario: Usuario,
    val administrador: Boolean,
    val turno: Turno
) {
}