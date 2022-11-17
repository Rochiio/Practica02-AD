package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * Clase UsuarioTable
 */
object UsuarioTable : UUIDTable(){
    val nombre = varchar("nombre", 50)
    val apellido  = varchar("apellido", 50)
    val email = varchar("email", 50)
    val password  = varchar("password", 16) //hacer lo de la codificacion sha512
    val disponible = bool("disponible")
}

class UsuarioDAO(id: EntityID<UUID>): UUIDEntity(id){
    companion object: UUIDEntityClass<UsuarioDAO>(UsuarioTable)
    var nombre by UsuarioTable.nombre
    var apellido by UsuarioTable.apellido
    var email by UsuarioTable.email
    var password by UsuarioTable.password
    var disponible by UsuarioTable.disponible

    override fun toString(): String {
        return "Usuario(uuid='$id', nombre='$nombre', apellido='$apellido', email='$email', disponible='$disponible')"
    }


}