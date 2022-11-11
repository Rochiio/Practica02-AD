package models.usuarios

import models.Turno
import models.Turnos
import models.Usuario
import models.Usuarios
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Trabajadores: IntIdTable() {
    val administrador = bool("administrador")
    val usuario = reference("usuario",Usuarios)
    val turno = reference("turno", Turnos)
}

class Trabajador(id: EntityID<Int>) : IntEntity(id){
    companion object: IntEntityClass<Trabajador>(Trabajadores)
    var administrador by Trabajadores.administrador
    var usuario by Usuario referencedOn Trabajadores.usuario
    var turno by Turno referencedOn Trabajadores.turno
    override fun toString(): String {
        return "Trabajador(administrador=$administrador, usuario=$usuario, turno=$turno)"
    }

}