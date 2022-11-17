package models.usuarios

import models.Turno
import java.util.UUID

/**
 * Clase trabajador.
 */
data class Trabajador(
    val id : UUID,
    val usuario: Usuario,
    val administrador: Boolean,
    val turno: Turno
) {
}