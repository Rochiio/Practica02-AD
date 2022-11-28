package repositories.pedidos

import models.pedidos.TareaPersonalizado
import repositories.ICRUD

interface PersonalizadoRepository: ICRUD<TareaPersonalizado, Int> {
}