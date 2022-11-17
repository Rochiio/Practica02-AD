package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Clase UsuarioTable
 */
object UsuarioTable : IntIdTable(){
    //val id : Column<Int> = integer("id").autoIncrement()
    val uuid = uuid("uuid").autoGenerate()
    val nombre = varchar("nombre", 50)
    val apellido  = varchar("apellido", 50)
    val email = varchar("email", 50)
    val password  = varchar("password", 16) //hacer lo de la codificacion sha512
    val disponible = bool("disponible")
    //val tipo = varchar("tipo",13)
    //override val primaryKey = PrimaryKey(id, name = "PK_Usuario_ID")
}

class UsuarioDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<UsuarioDAO>(UsuarioTable)
    var uuid by UsuarioTable.uuid
    var nombre by UsuarioTable.nombre
    var apellido by UsuarioTable.apellido
    var email by UsuarioTable.email
    var password by UsuarioTable.password
    var disponible by UsuarioTable.disponible

    override fun toString(): String {
        return "Usuario(uuid='$uuid', nombre='$nombre', apellido='$apellido', email='$email', disponible='$disponible')"
    }


}