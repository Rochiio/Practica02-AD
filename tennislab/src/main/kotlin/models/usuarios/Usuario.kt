package models.usuarios


/**
 * Clase Usuario.
 */
data class Usuario(
    val nombre: String,
    val apellido:String,
    val email:String,
    val password:String
) {
}