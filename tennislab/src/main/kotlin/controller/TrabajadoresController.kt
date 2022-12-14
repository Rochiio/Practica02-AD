package controller

import exception.TrabajadorError
import models.usuarios.Trabajador
import repositories.usuarios.TrabajadorRepository
import java.util.*


class TrabajadoresController(private var repository: TrabajadorRepository) {

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
            if (find.password != password){
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
    fun addTrabajador(trabajador: Trabajador): Trabajador{
        var existe = repository.findByEmail(trabajador.email)
        existe?.let {

            throw  TrabajadorError("Ya existe un trabajador con este email")

        }?: run{
            repository.save(trabajador)
            return trabajador
        }
    }


    /**
     * Conseguir todos los trabajadores que existen.
     */
    fun getAllTrabajadores():List<Trabajador>{
        return repository.findAll()
    }


    /**
     * Actualizar un trabajador
     */
    //@Throws(TrabajadorError::class)
    fun updateTrabajador(trabajador:Trabajador){
        repository.save(trabajador)
    }


    /**
     * Eliminar un trabajador
     */
    @Throws(TrabajadorError::class)
    fun deleteTrabajador(trabajador:Trabajador):Boolean{
        var correcto =repository.delete(trabajador)
        if(correcto){
            println("Trabajador eliminado correctamente")
            return true
        }else{
            throw  TrabajadorError("Problemas al eliminar el trabajador")
        }
    }


    /**
     * Conseguir un trabajador por el uuid.
     */
    @Throws(TrabajadorError::class)
    fun getTrabajadorByUUID(uuid: UUID): Trabajador {
        var existe = repository.findByUUID(uuid)
        existe?.let {
            return existe
        }?: run{
            throw  TrabajadorError("No existe un trabajador con este UUID")
        }
    }
}