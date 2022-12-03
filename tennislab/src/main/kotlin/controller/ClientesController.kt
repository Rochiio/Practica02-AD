package controller

import exception.ClienteError
import exception.TrabajadorError
import models.usuarios.Cliente
import models.usuarios.Trabajador
import repositories.usuarios.ClienteRepository
import java.util.*

class ClientesController(private var repository: ClienteRepository) {

    /**
     * Añadir un cliente
     */
    @Throws(ClienteError::class)
    fun addCliente(cliente: Cliente): Cliente {
        var existe = cliente.uuid?.let { repository.findByUUID(it) }
        existe?.let {
            throw  ClienteError("Ya existe un cliente con este UUID")
        }?: run{
            return repository.save(cliente)
        }
    }
    @Throws(ClienteError::class)
    fun getClienteByEmailAndPassword(email: String, password: String): Cliente?{
        var find = repository.findByEmail(email)
        find?.let {
            if (find.password != password){
                throw ClienteError("Cliente incorrecto")
            }
        }?: run {
            throw ClienteError("Cliente no existe")
        }
        return find
    }

    /**
     * Conseguir todos los clientes que existen.
     */
    fun getAllClientes():List<Cliente>{
        return repository.findAll()
    }


    /**
     * Actualizar un cliente
     */
    fun updateCliente(cliente: Cliente){
        repository.save(cliente)
    }


    /**
     * Eliminar un cliente
     */
    @Throws(ClienteError::class)
    fun deleteCliente(cliente: Cliente):Boolean{
        var correcto =repository.delete(cliente)
        if(correcto){
            println("Cliente eliminado correctamente")
            return true
        }else{
            throw  ClienteError("Problemas al eliminar el cliente")
        }
    }


    /**
     * Conseguir un cliente por el uuid.
     */
    @Throws(ClienteError::class)
    fun getClienteByUUID(uuid: UUID): Cliente {
        var existe = repository.findByUUID(uuid)
        existe?.let {
            return existe
        }?: run{
            throw  ClienteError("No existe un cliente con este UUID")
        }
    }
}