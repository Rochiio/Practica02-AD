package mappers

import entities.enums.Estado
import entities.pedidos.PedidoDAO
import entities.pedidos.TareaDAO
import entities.pedidos.TareaTable
import models.pedidos.Pedido
import models.pedidos.Tarea
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

fun TareaDAO.fromTareaDaoToTarea(): Tarea = Tarea(
    id = id.value,
    uuid = uuid,
    idMaquina = idMaquina,
    idTrabajador = idTrabajador.fromTrabajadorDaoToTrabajador(),

    descripcion = descripcion,
    precio = precio,
    tipoTarea = tipoTarea,
    disponible = disponible

)

fun PedidoDAO.fromPedidoDaoToPedido() : Pedido = Pedido(
    uuid = uuid,
    estado = Estado.valueOf(estado),
    fechaEntrada = fechaEntrada,
    fechaSalida = fechaSalida,
    fechaFinal = fechaFinal,
    precioTotal = precioTotal,
    topeEntrega = topeEntrega,
    tareas = TareaDAO.find { TareaTable.uuid eq uuid }.map { it.fromTareaDaoToTarea() } as ArrayList<Tarea>
)