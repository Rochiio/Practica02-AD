package controller

import exception.MaquinaError
import models.maquinas.Encordador
import models.maquinas.Personalizador
import repositories.maquinas.EncordadorRepository
import repositories.maquinas.PersonalizadorRepository
import java.util.*

class MaquinasController(
    private var encordadoraRepo: EncordadorRepository,
    private var personalizadoraRepo: PersonalizadorRepository
) {


       /**
     * Añadir una encordadora
     */
    @Throws(MaquinaError::class)
    fun addEncordadora(item: Encordador):Encordador{
        var existe = encordadoraRepo.findById(item.uuid)
        existe?.let {
            throw  MaquinaError("Ya existe una encordadora con este UUID")
        }?: run{
            encordadoraRepo.save(item)
            return item
        }
    }


    /**
     * Buscar una encordadora por su uuid.
     */
    @Throws(MaquinaError::class)
    fun getEncordadoraByUUID(uuid: UUID): Encordador {
        var existe = encordadoraRepo.findById(uuid)
        existe?.let {
            return it
        }?: run{
            throw MaquinaError("No existe la encordadora con este UUID")
        }
    }


    /**
     * Actualizar una encordadora
     */
    fun updateEncordadora(item: Encordador):Encordador{
        return encordadoraRepo.save(item)
    }


    /**
     * Conseguir todas las encordadoras.
     */
    fun getAllEncordadoras(): List<Encordador> {
        return encordadoraRepo.findAll()
    }


    /**
     * Eliminar una encordadora.
     */
    @Throws(MaquinaError::class)
    fun deleteEncordadora(encordador: Encordador):Boolean {
        var correcto =encordadoraRepo.delete(encordador)
        if(correcto){
            println("Encordadora eliminada correctamente")
            return true
        }else{
            throw  MaquinaError("Problemas al eliminar la encordadora")
        }
    }


    /**
     * Añadir una personalizadora
     */
    @Throws(MaquinaError::class)
    fun addPersonalizadora(item: Personalizador): Personalizador {
        var existe = personalizadoraRepo.findById(item.uuid)
        existe?.let {
            throw  MaquinaError("Ya existe una personalizadora con este UUID")
        }?: run{
            personalizadoraRepo.save(item)
            return item
        }
    }


    /**
     * Conseguir personalizadora por su uuid.
     */
    @Throws(MaquinaError::class)
    fun getPersonalizadoraByUUID(uuid: UUID): Personalizador {
        var existe = personalizadoraRepo.findById(uuid)
        existe?.let {
            return it
        }?: run{
            throw MaquinaError("No existe la personalizadora con este UUID")
        }
    }


    /**
     * Actualizar una personalizadora
     */
    fun updatePersonalizadora(item: Personalizador):Personalizador{
        return personalizadoraRepo.save(item)
    }


    /**
     * Conseguir todas las personalizadoras.
     */
    fun getAllPersonalizadoras(): List<Personalizador> {
        return personalizadoraRepo.findAll()
    }


    /**
     * Eliminar una personalizadora.
     */
    @Throws(MaquinaError::class)
    fun deletePersonalizadora(personalizadora: Personalizador):Boolean {
        var correcto = personalizadoraRepo.delete(personalizadora)
        if(correcto){
            println("Personalizadora eliminada correctamente")
            return true
        }else{
            throw  MaquinaError("Problemas al eliminar la personalizadora")
        }
    }



}