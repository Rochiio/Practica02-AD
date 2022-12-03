package entities.usuarios

import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import entities.usuarios.TrabajadorTable.autoGenerate
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

/**
 * Entidad de clientes para la base de datos.
 */
object ClienteTable: IntIdTable() {
    val uuid = ClienteTable.uuid("uuid").autoGenerate()
    val nombre = ClienteTable.varchar("nombre", 50)
    val apellido= ClienteTable.varchar("apellido", 50)
    val email  = ClienteTable.varchar("email", 50)
    val password = ClienteTable.varchar("password", 200)
    val pedido = reference("pedidos", PedidoTable, onDelete = ReferenceOption.CASCADE).nullable()
}

class ClienteDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<ClienteDAO>(ClienteTable)
    var uuid by ClienteTable.uuid
    var nombre by ClienteTable.nombre
    var apellido by ClienteTable.apellido
    var email by ClienteTable.email
    var password by ClienteTable.password
    val pedidos by PedidoDAO optionalReferencedOn ClienteTable.pedido
    override fun toString(): String {
        return "ClienteDAO(uuid=$uuid, nombre='$nombre', apellido='$apellido', email='$email', password='$password')"
    }



}