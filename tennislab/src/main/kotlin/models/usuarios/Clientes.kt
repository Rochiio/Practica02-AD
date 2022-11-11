package models.usuarios

import models.Usuario
import models.Usuarios
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Clientes: IntIdTable() {
    val usuario = reference("usuario", Usuarios)
    //TODO faltan pedidos
}

class Cliente(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Cliente>(Clientes)
    var usuario by Usuario referencedOn Clientes.usuario
}