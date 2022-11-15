package repositories.maquinas

import entities.maquinas.PersonalizadorDAO
import repositories.ICRUD

interface PersonalizadoraRepository : ICRUD<PersonalizadorDAO, Int> {
}