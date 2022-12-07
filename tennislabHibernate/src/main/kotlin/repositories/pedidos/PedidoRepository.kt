package repositories.pedidos

import models.pedidos.Pedido
import repositories.ICRUD
import java.util.UUID

interface PedidoRepository : ICRUD<Pedido, UUID> {
}