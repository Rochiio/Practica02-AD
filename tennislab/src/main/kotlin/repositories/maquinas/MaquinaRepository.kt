package repositories.maquinas

import entities.MaquinaDAO
import models.maquinas.Maquina
import repositories.ICRUD
import java.util.UUID

<<<<<<< HEAD
interface MaquinaRepository : ICRUD<MaquinaDAO, Maquina, Int> {
=======
interface MaquinaRepository : ICRUD< Maquina, Int> {
>>>>>>> 6bf9ce7639f84cffb5cd70e636c5af14c143252a
}