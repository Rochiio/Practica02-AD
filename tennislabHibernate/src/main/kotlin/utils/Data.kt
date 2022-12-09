package utils

import models.listados.AsignacionesEncordadores
import models.pedidos.Pedido
import models.pedidos.Tarea

/**
 * Datos necesarios para realizar distintas acciones.
 */
object Data {
    var asignaciones= mutableListOf<AsignacionesEncordadores>()
    var pedidosCompletados = mutableListOf<Pedido>()
    var pedidosPendientes = mutableListOf<Pedido>()
    var tareasCreadas = mutableListOf<Tarea>()
    var servicios =  listOf("Personalizacion","Encordado")
    var productos = listOf("RAQUETA", "CORDAJE", "COMPLEMENTO")
}