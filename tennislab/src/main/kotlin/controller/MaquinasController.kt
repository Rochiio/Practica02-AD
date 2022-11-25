package controller

import exception.MaquinaError
import exception.TrabajadorError
import models.maquinas.Encordador
import models.maquinas.Personalizadora
import repositories.maquinas.EncordadoRepository
import repositories.maquinas.PersonalizadoraRepository
import java.util.*

class MaquinasController(
    var encordadoraRepo: EncordadoRepository,
    var personalizadoraRepo: PersonalizadoraRepository
) {


       /**
     * Añadir una encordadora
     */
    @Throws(MaquinaError::class)
    fun addEncordadora(item: Encordador){
        var existe = item.uuid?.let { encordadoraRepo.findByUUID(it) }
        existe?.let {
            throw  MaquinaError("Ya existe una encordadora con este UUID")
        }?: run{
            encordadoraRepo.add(item)
        }
    }


    /**
     * Actualizar una encordadora
     */
    @Throws(MaquinaError::class)
    fun getEncordadoraByUUID(uuid: UUID): Encordador {
        var existe = encordadoraRepo.findByUUID(uuid)
        existe?.let {
            return it
        }?: run{
            throw MaquinaError("No existe la encordadora con este UUID")
        }
    }


    /**
     * Actualizar una encordadora
     */
    fun updateEncordadora(item: Encordador){
        encordadoraRepo.save(item)
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
    fun deleteEncordadora(encordador: Encordador) {
        var correcto =encordadoraRepo.delete(encordador)
        if(correcto){
            println("Encordadora eliminada correctamente")
        }else{
            throw  MaquinaError("Problemas al eliminar la encordadora")
        }
    }


    /**
     * Añadir una personalizadora
     */
    @Throws(MaquinaError::class)
    fun addPersonalizadora(item: Personalizadora){
        var existe = item.uuid?.let { personalizadoraRepo.findByUUID(it) }
        existe?.let {
            throw  MaquinaError("Ya existe una personalizadora con este UUID")
        }?: run{
            personalizadoraRepo.add(item)
        }
    }


    /**
     * Actualizar una personalizadora
     */
    @Throws(MaquinaError::class)
    fun getPersonalizadoraByUUID(uuid: UUID): Personalizadora {
        var existe = personalizadoraRepo.findByUUID(uuid)
        existe?.let {
            return it
        }?: run{
            throw MaquinaError("No existe la personalizadora con este UUID")
        }
    }


    /**
     * Actualizar una personalizadora
     */
    fun updatePersonalizadora(item: Personalizadora){
        personalizadoraRepo.save(item)
    }


    /**
     * Conseguir todas las personalizadoras.
     */
    fun getAllPersonalizadoras(): List<Personalizadora> {
        return personalizadoraRepo.findAll()
    }


    /**
     * Eliminar una personalizadora.
     */
    @Throws(MaquinaError::class)
    fun deletePersonalizadora(personalizadora: Personalizadora) {
        var correcto = personalizadoraRepo.delete(personalizadora)
        if(correcto){
            println("Personalizadora eliminada correctamente")
        }else{
            throw  MaquinaError("Problemas al eliminar la personalizadora")
        }
    }



}