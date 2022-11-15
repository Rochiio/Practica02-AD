package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Clase Usuarios
 */
object Usuarios : IntIdTable(){
    //val id : Column<Int> = integer("id").autoIncrement()
    val uuid = uuid("uuid").autoGenerate()
    val nombre = varchar("nombre", 50)
    val apellido  = varchar("apellido", 50)
    val email = varchar("email", 50)
    val password  = varchar("password", 16) //hacer lo de la codificacion sha512
    val tipo = varchar("tipo",13)
    //override val primaryKey = PrimaryKey(id, name = "PK_Usuario_ID")
}

class Usuario(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Usuario>(Usuarios)
    var uuid by Usuarios.uuid
    var nombre by Usuarios.nombre
    var apellido by Usuarios.apellido
    var email by Usuarios.email
    var password by Usuarios.password
    var tipo by Usuarios.tipo
    override fun toString(): String {
        return "Usuario(uuid='$uuid', nombre='$nombre', apellido='$apellido', email='$email', tipo='$tipo')"
    }


}