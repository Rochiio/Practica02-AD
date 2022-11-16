package models.usuarios

import java.util.*


/**
 * Clase Usuario.
 */
data class Usuario(
    var uuid: UUID?,
    val nombre: String,
    val apellido:String,
    val email:String,
    val password:String,
    val disponible: Boolean
) {
}