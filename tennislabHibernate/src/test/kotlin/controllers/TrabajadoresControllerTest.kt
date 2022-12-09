package controllers

import exception.TrabajadorError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.*
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuarios.Trabajador
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.PasswordParser
import java.util.*

@ExtendWith(MockKExtension::class)
internal class TrabajadoresControllerTest {
    @MockK
    private lateinit var repository: TrabajadorRepositoryImpl

    @InjectMockKs
    private lateinit var controller: TrabajadoresController

    private var test = Trabajador(UUID.fromString("27c1af75-3bd1-4a71-be4c-a498ab5e7d85"),
        "Test","Prueba","test@gmail.com","1234", disponible = true, administrador = false, mutableListOf()
    )

    init {
        MockKAnnotations.init(this)
    }


    @Test
    fun getTrabajadorByEmailAndPassword() {
        every{repository.findByEmail(test.email)} returns test
        var find = controller.getTrabajadorByEmailAndPassword(test.email, test.password)

        assertAll(
            { assertNotNull(find) },
            { assertEquals(find?.uuid, test.uuid) },
            { assertEquals(find?.nombre, test.nombre) },
            { assertEquals(find?.apellido, test.apellido) },
            { assertEquals(find?.email, test.email) },
            { assertEquals(find?.password, test.password) },
            { assertEquals(find?.disponible, test.disponible) },
            { assertEquals(find?.administrador, test.administrador) },
        )

        verify(exactly=1){repository.findByEmail(test.email)}
    }

    @Test
    fun getTrabajadorByEmailAndPasswordErrorNoExiste(){
        every{repository.findByEmail(test.email)} returns null

        val exception = assertThrows(TrabajadorError::class.java) {
            controller.getTrabajadorByEmailAndPassword(test.email,test.password)
        }

        assertEquals("Trabajador no existe", exception.item)


        verify(exactly=1){repository.findByEmail(test.email)}
    }

    @Test
    fun getTrabajadorByEmailAndPasswordErrorIncorrecto(){
        every{repository.findByEmail(test.email)} returns test

        val exception = assertThrows(TrabajadorError::class.java) {
            controller.getTrabajadorByEmailAndPassword(test.email, PasswordParser.encriptar("111"))
        }

        assertEquals("Trabajador incorrecto", exception.item)


        verify(exactly=1){repository.findByEmail(test.email)}
    }

    @Test
    fun addTrabajador() {
        every { repository.findByEmail(test.email) } returns null
        every { repository.save(test) } returns test

        var add = controller.addTrabajador(test)

        assertAll(
            { assertEquals(add.uuid, test.uuid) },
            { assertEquals(add.nombre, test.nombre) },
            { assertEquals(add.apellido, test.apellido) },
            { assertEquals(add.email, test.email) },
            { assertEquals(add.password, test.password) },
            { assertEquals(add.disponible, test.disponible) },
            { assertEquals(add.administrador, test.administrador) },
        )

        verify(exactly=1){ repository.save(test)}
        verify(exactly=1){ repository.findByEmail(test.email) }
    }

    @Test
    fun addTrabajadorErrorExiste(){
        every { repository.findByEmail(test.email) } returns test
        var exception = assertThrows(TrabajadorError::class.java){
            controller.addTrabajador(test)
        }

        assertEquals("Ya existe un trabajador con este email", exception.item)

        verify(exactly=1){ repository.findByEmail(test.email)}
    }

    @Test
    fun getAllTrabajadores() {
        every { repository.findAll() } returns listOf(test)
        var lista = controller.getAllTrabajadores()
        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, test.uuid) },
            { assertEquals(lista[0].nombre, test.nombre) },
            { assertEquals(lista[0].apellido, test.apellido) },
            { assertEquals(lista[0].email, test.email) },
            { assertEquals(lista[0].password, test.password) },
            { assertEquals(lista[0].disponible, test.disponible) },
            { assertEquals(lista[0].administrador, test.administrador) },
        )

        verify(exactly=1){ repository.findAll() }
    }


    @Test
    fun deleteTrabajador() {
        every { repository.delete(test) } returns true
        var eliminado = controller.deleteTrabajador(test)
        assertTrue(eliminado)
        verify(exactly=1){ repository.delete(test) }
    }

    @Test
    fun deleteTrabajadorErrorNoExiste(){
        every { repository.delete(test) } returns false
        var exception = assertThrows(TrabajadorError::class.java){
            controller.deleteTrabajador(test)
        }

        assertEquals("Problemas al eliminar el trabajador", exception.item)

        verify(exactly=1){repository.delete(test)}
    }

    @Test
    fun getTrabajadorByUUID() {
        every{ repository.findById(test.uuid)} returns test
        var find = controller.getTrabajadorByUUID(test.uuid)
        assertAll(
            { assertEquals(find.uuid, test.uuid) },
            { assertEquals(find.nombre, test.nombre) },
            { assertEquals(find.apellido, test.apellido) },
            { assertEquals(find.email, test.email) },
            { assertEquals(find.password, test.password) },
            { assertEquals(find.disponible, test.disponible) },
            { assertEquals(find.administrador, test.administrador) },
        )

        verify(exactly=1){ repository.findById(test.uuid) }
    }

    @Test
    fun getTrabajadorByUUIDErrorNoExiste(){
        every{ repository.findById(test.uuid)} returns null
        var exception = assertThrows(TrabajadorError::class.java){
            controller.getTrabajadorByUUID(test.uuid)
        }

        assertEquals("No existe un trabajador con este UUID", exception.item)

        verify(exactly=1){ repository.findById(test.uuid)}
    }
}