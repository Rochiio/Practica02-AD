package repositories.maquinas

import entities.maquinas.PersonalizadorDAO
import models.maquinas.Personalizar
import repositories.ICRUD

interface PersonalizadoraRepository : ICRUD<PersonalizadorDAO, Personalizar,Int> {
}