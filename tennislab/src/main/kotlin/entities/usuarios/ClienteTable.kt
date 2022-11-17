package entities.usuarios

import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entidad de clinetes para la base de datos.
 */
object ClienteTable: IntIdTable() {
    val usuario = reference("usuario", UsuarioTable)
    val pedidos = reference("pedidos", PedidoTable)
}

class ClienteDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<ClienteDAO>(ClienteTable)
    var usuario by UsuarioDAO referencedOn ClienteTable.usuario
    val pedidos by PedidoDAO referrersOn ClienteTable.pedidos

    override fun toString(): String {
        return "Cliente(usuario=$usuario, pedidos=$pedidos)"
    }
}