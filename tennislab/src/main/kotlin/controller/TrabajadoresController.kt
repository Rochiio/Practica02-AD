package controller

import exception.TrabajadorError
import models.usuarios.Trabajador
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.usuarios.TrabajadorRepository


class TrabajadoresController(var repository: TrabajadorRepository) {

    /**
     * Saber si existe un trabajador con un email y password.
     * @param email email a buscar si existe
     * @param password a ver si es correcta
     * @return trabajador dependiendo de si existe
     */
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


    /**
     * Añadir un trabajador
     */
    @Throws(TrabajadorError::class)
    fun addTrabajador(trabajador: Trabajador){
        var existe = repository.findByEmail(trabajador.email)
        existe?.let {
            throw  TrabajadorError("Ya existe un trabajador con este email")
        }?: run{
            repository.add(trabajador)
        }
    }


    /**
     * Conseguir todos los trabajadores que existen.
     */
    fun getAllTrabajadores():List<Trabajador>{
        return repository.findAll()
    }
}