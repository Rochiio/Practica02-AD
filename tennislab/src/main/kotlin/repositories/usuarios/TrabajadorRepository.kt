package repositories.usuarios

import entities.usuarios.TrabajadorDAO
import models.usuarios.Trabajador
import repositories.ICRUD
import java.util.*

interface TrabajadorRepository: ICRUD<Trabajador, UUID> {
}