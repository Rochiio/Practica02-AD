package controller

import exception.ClienteError
import exception.TrabajadorError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuarios.Cliente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuarios.ClienteRepository
import repositories.usuarios.ClienteRepositoryImpl
import java.util.*

@ExtendWith(MockKExtension::class)
internal class ClientesControllerTest {
    @MockK
    private lateinit var repository: ClienteRepositoryImpl

    @InjectMockKs
    private lateinit var controller: ClientesController

    private var test = Cliente(1, UUID.fromString("27c1af75-3bd1-4a71-be4c-a498ab5e7d85"),
    "Test","Pruebi","pruebi@gmail.com","123454")

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addCliente() {
        every {repository.findByUUID(test.uuid!!)} returns null
        every { repository.save(test) } returns test
        every { repository.add(test) } returns test

        var add = controller.addCliente(test)

        assertAll(
            { assertEquals(add.id, test.id) },
            { assertEquals(add.uuid, test.uuid) },
            { assertEquals(add.nombre, test.nombre) },
            { assertEquals(add.apellido, test.apellido) },
            { assertEquals(add.email, test.email) },
            { assertEquals(add.password, test.password) }
        )

        verify(exactly=1){repository.save(test)}
        verify(exactly=1){repository.findByUUID(test.uuid!!)}
    }

    @Test
    fun addClienteErrorExiste(){
        every { repository.findByUUID(test.uuid!!) } returns test
        var exception = assertThrows(ClienteError::class.java){
            controller.addCliente(test)
        }

        assertEquals("Ya existe un cliente con este UUID", exception.item)

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }

    @Test
    fun getAllClientes() {
        every { repository.findAll() } returns listOf(test)
        var lista = controller.getAllClientes()
        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, test.id) },
            { assertEquals(lista[0].uuid, test.uuid) },
            { assertEquals(lista[0].nombre, test.nombre) },
            { assertEquals(lista[0].apellido, test.apellido) },
            { assertEquals(lista[0].email, test.email) },
            { assertEquals(lista[0].password, test.password) }
        )

        verify(exactly=1){repository.findAll()}
    }


    @Test
    fun deleteCliente() {
        every{ repository.delete(test) } returns true
        var eliminado = repository.delete(test)
        assertTrue(eliminado)
        verify(exactly=1){repository.delete(test)}
    }

    @Test
    fun deleteClienteErrorNoEliminado(){
        every { repository.delete(test) } returns false
        var exception = assertThrows(ClienteError::class.java){
            controller.deleteCliente(test)
        }

        assertEquals("Problemas al eliminar el cliente", exception.item)
    }

    @Test
    fun getClienteByUUID() {
        every{ repository.findByUUID(test.uuid!!)} returns test
        var find = controller.getClienteByUUID(test.uuid!!)
        assertAll(
            { assertEquals(find.id, test.id) },
            { assertEquals(find.uuid, test.uuid) },
            { assertEquals(find.nombre, test.nombre) },
            { assertEquals(find.apellido, test.apellido) },
            { assertEquals(find.email, test.email) },
            { assertEquals(find.password, test.password) }
        )

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }

    @Test
    fun getClienteByUUIDNoExiste(){
        every{ repository.findByUUID(test.uuid!!)} returns null
        var exception = assertThrows(ClienteError::class.java){
            controller.getClienteByUUID(test.uuid!!)
        }

        assertEquals("No existe un cliente con este UUID", exception.item)

        verify(exactly=1){repository.findByUUID(test.uuid!!)}
    }
}