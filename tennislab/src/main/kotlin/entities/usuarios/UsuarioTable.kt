package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

/**
 * Clase UsuarioTable
 */
object UsuarioTable : UUIDTable(){
    val iID : Column<Int> = integer("iID").autoIncrement()
    val nombre = varchar("nombre", 50)
    val apellido  = varchar("apellido", 50)
    val email = varchar("email", 50)
    val password  = varchar("password", 16) //hacer lo de la codificacion sha512
    val disponible = bool("disponible")
    //val tipo = varchar("tipo",13)
    //override val primaryKey = PrimaryKey(id, name = "PK_Usuario_ID")
}

class UsuarioDAO(id: EntityID<UUID>): UUIDEntity(id){
    companion object: UUIDEntityClass<UsuarioDAO>(UsuarioTable)
    var iID by UsuarioTable.iID
    var nombre by UsuarioTable.nombre
    var apellido by UsuarioTable.apellido
    var email by UsuarioTable.email
    var password by UsuarioTable.password
    var disponible by UsuarioTable.disponible

    override fun toString(): String {
        return "Usuario(uuid='$id', nombre='$nombre', apellido='$apellido', email='$email', disponible='$disponible')"
    }


}