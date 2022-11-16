package repositories.maquinas

import entities.EncordadorDAO
import models.maquinas.Encordador
import repositories.ICRUD

interface EncordadoRepository: ICRUD<EncordadorDAO, Encordador,Int> {
}