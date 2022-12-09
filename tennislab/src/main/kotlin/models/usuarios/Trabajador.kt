package models.usuarios

import utils.serializer.UUIDSerializer
import java.util.UUID

/**
 * Modelo trabajador.
 */
@kotlinx.serialization.Serializable
data class Trabajador(
    var id: Int?,
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
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