package repositories.maquinas

import models.Maquina
import repositories.ICRUD
import java.util.UUID

interface MaquinaRepository : ICRUD<Maquina, Int> {
}