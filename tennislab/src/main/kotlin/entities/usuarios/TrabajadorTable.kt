package entities.usuarios

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

/**
 * Entidad trabajador para la base de datos.
 */

object TrabajadorTable: UUIDTable() {
    val iID : Column<Int> = integer("iID").autoIncrement()
    val usuario = reference("usuario", UsuarioTable)
    val administrador = bool("administrador")
    //val turno = reference("turno", TurnoTable)
}

class TrabajadorDAO(id: EntityID<UUID>) : UUIDEntity(id){
    companion object: UUIDEntityClass<TrabajadorDAO>(TrabajadorTable)
    val iID by TrabajadorTable.iID
    var usuario by UsuarioDAO referencedOn TrabajadorTable.usuario
    var administrador by TrabajadorTable.administrador
    //var turno by TurnoDAO referencedOn TrabajadorTable.turno
    override fun toString(): String {
        return "Trabajador(usuario_uuid=${usuario.toStringHerencia()}, uuid=$id, administrador=$administrador)"
    }


}