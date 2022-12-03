package utils

import models.listados.AsignacionesEncordadores
import models.pedidos.Pedido

/**
 * Datos necesarios para realizar distintas acciones.
 */
object Data {
    var asignaciones= mutableListOf<AsignacionesEncordadores>()
    var pedidosCompletados = mutableListOf<Pedido>()
    var pedidosPendientes = mutableListOf<Pedido>()
    var servicios =  listOf("Personalizacion","Encordado")
    var productos = listOf("RAQUETA", "CORDAJE", "COMPLEMENTO")
}