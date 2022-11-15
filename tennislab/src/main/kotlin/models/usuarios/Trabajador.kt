package models.usuarios

/**
 * Clase trabajador.
 */
data class Trabajador(
    val usuario: Usuario,
    val administrador: Boolean,
    //val turno: Turno TODO hacer turno modelo
) {
}