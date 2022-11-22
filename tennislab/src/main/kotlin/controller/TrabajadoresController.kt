package controller

import exception.TrabajadorError
import models.usuarios.Trabajador
import repositories.usuarios.TrabajadorRepository


class TrabajadoresController(var repository: TrabajadorRepository) {

    @Throws(TrabajadorError::class)
    fun getTrabajadorByEmailAndPassword(email: String, password: String): Trabajador?{
        var find = repository.findByEmail(email)
        find?.let {

        }?: run {

        }
        return null
    }
}