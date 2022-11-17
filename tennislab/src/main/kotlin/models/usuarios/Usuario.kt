package models.usuarios

import java.util.*


/**
 * Clase Usuario.
 */
data class Usuario(
    var uuid: UUID?,
    var nombre: String,
    var apellido:String,
    var email:String,
    var password:String,
    var disponible: Boolean
) {
}