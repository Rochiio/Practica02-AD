package repositories.maquinas

import entities.MaquinaDAO
import models.maquinas.Maquina
import repositories.ICRUD
import java.util.UUID

interface MaquinaRepository : ICRUD< Maquina, Int> {
}