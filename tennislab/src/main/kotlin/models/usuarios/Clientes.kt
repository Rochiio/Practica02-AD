package models.usuarios

import models.Encordador.Companion.referrersOn
import models.Usuario
import models.Usuarios
import models.pedidos.Pedido
import models.pedidos.Pedidos
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Clientes: IntIdTable() {
    val usuario = reference("usuario", Usuarios)
    val pedidos = reference("pedidos", Pedidos)
}

class Cliente(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Cliente>(Clientes)
    var usuario by Usuario referencedOn Clientes.usuario
    val pedidos by Pedido referrersOn Clientes.pedidos

    override fun toString(): String {
        return "Cliente(usuario=$usuario, pedidos=$pedidos)"
    }
}