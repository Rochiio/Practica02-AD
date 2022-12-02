package entities.pedidos

import entities.enums.TipoTarea
import entities.maquinas.MaquinaTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.pedidos.Tarea
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

/**
 * Entidad tarea para la base de datos.
 */
object TareaTable : IntIdTable() {
    var uuid = uuid("uuid").autoGenerate()
    var id_trabajador = reference("id_trabajador", TrabajadorTable)
    var id_maquina = uuid("id_maquina").nullable()
    var id_pedido = reference("id_pedido", PedidoTable, onDelete = ReferenceOption.CASCADE).nullable()
    var precio = long("precio")
    var tipoTarea = enumeration("tipoTarea", TipoTarea::class)
    var descripcion = varchar("descripcion", 200)
    var disponible = bool("disponible")


}

class TareaDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TareaDAO>(TareaTable)

    var uuid by TareaTable.uuid
    var idTrabajador by TrabajadorDAO referencedOn TareaTable.id_trabajador
    var idMaquina by TareaTable.id_maquina
    var idPedido by PedidoDAO optionalReferencedOn TareaTable.id_pedido
    var precio by TareaTable.precio
    var tipoTarea by TareaTable.tipoTarea
    var descripcion by TareaTable.descripcion
    var disponible by TareaTable.disponible
    override fun toString(): String {
        return "TareaDAO(uuid=$uuid,idPedido=$idPedido, idTrabajador=$idTrabajador, idMaquina=$idMaquina, precio=$precio, tipoTarea=$tipoTarea, descripcion='$descripcion')"
    }


}