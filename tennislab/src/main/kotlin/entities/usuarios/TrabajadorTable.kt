package entities.usuarios

import entities.TurnoDAO
import entities.TurnoTable
import entities.UsuarioDAO
import entities.UsuarioTable
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
 * Entidad trabajador para la base de datos.
 */
object TrabajadorTable: UUIDTable() {
    val usuario = reference("usuario",UsuarioTable)
    val iID : Column<Int> = integer("iID").autoIncrement()
    val administrador = bool("administrador")
    val turno = reference("turno", TurnoTable)
}

class TrabajadorDAO(id: EntityID<UUID>) : UUIDEntity(id){
    companion object: UUIDEntityClass<TrabajadorDAO>(TrabajadorTable)
    var usuario by UsuarioDAO referencedOn TrabajadorTable.usuario
    val iID by TrabajadorTable.iID
    var administrador by TrabajadorTable.administrador
    var turno by TurnoDAO referencedOn TrabajadorTable.turno
    override fun toString(): String {
        return "Trabajador(usuario=$usuario, uuid=$id, administrador=$administrador, turno=$turno)"
    }


}