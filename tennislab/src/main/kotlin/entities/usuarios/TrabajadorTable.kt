package entities.usuarios

import entities.TurnoDAO
import entities.TurnoTable
import entities.UsuarioDAO
import entities.UsuarioTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad trabajador para la base de datos.
 */
object TrabajadorTable: IntIdTable() {
    val usuario = reference("usuario",UsuarioTable)
    val administrador = bool("administrador")
    //val turno = reference("turno", TurnoTable)
}

class TrabajadorDAO(id: EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<TrabajadorDAO>(TrabajadorTable)
    var usuario by UsuarioDAO referencedOn TrabajadorTable.usuario
    var administrador by TrabajadorTable.administrador
    //var turno by TurnoDAO referencedOn TrabajadorTable.turno
    override fun toString(): String {
        return "Trabajador(usuario=$usuario, administrador=$administrador)"
    }


}