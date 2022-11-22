package controller

import exception.TrabajadorError
import models.usuarios.Trabajador
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.usuarios.TrabajadorRepository


class TrabajadoresController(var repository: TrabajadorRepository) {

    @Throws(TrabajadorError::class)
    fun getTrabajadorByEmailAndPassword(email: String, password: String): Trabajador?{
        var find = repository.findByEmail(email)
        find?.let {
            if (find.password == password){

            }else{
                throw TrabajadorError("Trabajador incorrecto")
            }
        }?: run {
                throw TrabajadorError("Trabajador no existe")
        }
        return find
    }
}