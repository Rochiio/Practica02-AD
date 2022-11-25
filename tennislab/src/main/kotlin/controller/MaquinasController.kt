package controller

import exception.MaquinaError
import exception.TrabajadorError
import models.maquinas.Encordador
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
    fun deleteEncordadora(encordador: Encordador) {
        var correcto =encordadoraRepo.delete(encordador)
        if(correcto){
            println("Encordadora eliminada correctamente")
        }else{
            throw  TrabajadorError("Problemas al eliminar la encordadora")
        }
    }


}