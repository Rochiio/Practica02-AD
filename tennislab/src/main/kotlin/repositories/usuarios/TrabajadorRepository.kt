package repositories.usuarios

import entities.usuarios.TrabajadorDAO
import models.usuarios.Trabajador
import repositories.ICRUD

interface TrabajadorRepository: ICRUD<TrabajadorDAO, Trabajador,Int> {
}