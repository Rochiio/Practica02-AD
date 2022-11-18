package repositories.maquinas

import models.maquinas.Maquina
import repositories.ICRUD
import java.util.UUID


interface MaquinaRepository : ICRUD<Maquina, UUID> {
}