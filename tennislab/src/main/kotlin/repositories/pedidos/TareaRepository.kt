package repositories.pedidos

import models.pedidos.Tarea
import repositories.ICRUD
import java.util.UUID

interface TareaRepository : ICRUD<Tarea, Int> {
}