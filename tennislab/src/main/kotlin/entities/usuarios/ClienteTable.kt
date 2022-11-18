package entities.usuarios

import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * Entidad de clinetes para la base de datos.
 */
object ClienteTable: UUIDTable() {
    val usuario = reference("usuario", UsuarioTable)
    //val pedidos = reference("pedidos", PedidoTable)
}

class ClienteDAO(id: EntityID<UUID>): UUIDEntity(id){
    companion object: UUIDEntityClass<ClienteDAO>(ClienteTable)
    var usuario by UsuarioDAO referencedOn ClienteTable.usuario
    //val pedidos by PedidoDAO referrersOn ClienteTable.pedidos

    override fun toString(): String {
        return "Cliente(uuid=${id.value} usuario=${usuario.toStringHerencia()})"
    }
}