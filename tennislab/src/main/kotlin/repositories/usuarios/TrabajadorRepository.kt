package repositories.usuarios

import models.usuarios.Trabajador
import repositories.ICRUD
import java.util.*


interface TrabajadorRepository: ICRUD<Trabajador, Int> {
    fun findByEmail(email: String): Trabajador?
}