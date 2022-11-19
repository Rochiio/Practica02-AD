package entities

import entities.usuarios.UsuarioTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.util.*

object TrabajadorTableUnica : IntIdTable(){
    val uuid = uuid("uuid")
    val nombre = varchar("nombre", 50)
    val apellido  = varchar("apellido", 50)
    val email = varchar("email", 50)
    val password  = varchar("password", 10) //hacer lo de la codificacion sha512
    val disponible = bool("disponible")
    val administrador = bool("admin")
}

class TrabajadorUnicoDAO (id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<TrabajadorUnicoDAO>(TrabajadorTableUnica)
    var uuid by TrabajadorTableUnica.uuid
    var nombre by TrabajadorTableUnica.nombre
    var apellido by TrabajadorTableUnica.apellido
    var email by TrabajadorTableUnica.email
    var password by TrabajadorTableUnica.password
    var disponible by TrabajadorTableUnica.disponible
    var administrador by TrabajadorTableUnica.administrador
}