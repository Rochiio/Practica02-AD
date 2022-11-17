package repositories.maquinas

import entities.maquinas.PersonalizadorDAO
import models.maquinas.Personalizar
import repositories.ICRUD

<<<<<<< HEAD
interface PersonalizadoraRepository : ICRUD<PersonalizadorDAO, Personalizar,Int> {
=======
interface PersonalizadoraRepository : ICRUD<Personalizar,Int> {
>>>>>>> 6bf9ce7639f84cffb5cd70e636c5af14c143252a
}