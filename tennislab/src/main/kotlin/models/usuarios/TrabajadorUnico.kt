package models.usuarios

import java.util.*

data class TrabajadorUnico(
    var uuid: UUID?,
    var nombre: String,
    var apellido:String,
    var email:String,
    var password:String,
    val administrador: Boolean,
    var disponible: Boolean
)
