package repositories.maquinas

import models.maquinas.Personalizador
import repositories.ICRUD
import java.util.*

interface PersonalizadorRepository: ICRUD<Personalizador, UUID> {
}