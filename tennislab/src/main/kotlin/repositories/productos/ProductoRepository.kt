package repositories.productos

import models.pedidos.Producto
import repositories.ICRUD
import java.util.*

interface ProductoRepository: ICRUD<Producto, Int> {
}