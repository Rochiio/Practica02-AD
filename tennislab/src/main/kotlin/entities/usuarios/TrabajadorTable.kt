package entities.usuarios

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.IntIdTable

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

/**
 * Entidad trabajador para la base de datos.
 */
object TrabajadorTable: IntIdTable() {
    val uuid = uuid("uuid").autoGenerate()
    val nombre = varchar("nombre",50)
    val apellido= varchar("apellido",50)
    val email  = varchar("email",50)
    val password = varchar("password",200)
    val disponible = bool("disponible")
    val administrador = bool("administrador")
}

class TrabajadorDAO(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<TrabajadorDAO>(TrabajadorTable)
    var uuid by TrabajadorTable.uuid
    var nombre by TrabajadorTable.nombre
    var apellido by TrabajadorTable.apellido
    var email by TrabajadorTable.email
    var password by TrabajadorTable.password
    var disponible by TrabajadorTable.disponible
    var administrador by TrabajadorTable.administrador

    override fun toString(): String {
        return "TrabajadorDAO(uuid=$uuid, nombre='$nombre', apellido='$apellido', email='$email', password='$password', disponible=$disponible, administrador=$administrador)"
    }


}