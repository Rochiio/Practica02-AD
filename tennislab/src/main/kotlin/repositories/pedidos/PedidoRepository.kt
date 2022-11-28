package repositories.pedidos

import models.pedidos.Pedido
import repositories.ICRUD
import java.util.*

interface PedidoRepository : ICRUD<Pedido, UUID> {
}