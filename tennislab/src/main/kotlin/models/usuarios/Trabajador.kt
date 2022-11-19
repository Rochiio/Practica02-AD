package models.usuarios

import java.util.UUID

/**
 * Modelo trabajador.
 */
data class Trabajador(
    var id: Int?,
    var uuid: UUID?,
    var nombre: String,
    var apellido:String,
    var email:String,
    var password:String,
    var disponible: Boolean,
    var administrador: Boolean
    //val turno: Turno
) {
}