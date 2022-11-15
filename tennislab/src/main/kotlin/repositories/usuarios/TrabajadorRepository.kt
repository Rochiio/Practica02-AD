package repositories.usuarios

import entities.usuarios.TrabajadorDAO
import repositories.ICRUD

interface TrabajadorRepository: ICRUD<TrabajadorDAO,Int> {
}