package controller

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.*
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuarios.Trabajador
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuarios.TrabajadorRepositoryImpl
import java.util.*

@ExtendWith(MockKExtension::class)
internal class TrabajadoresControllerTest {
    @MockK
    private lateinit var repository: TrabajadorRepositoryImpl

    @InjectMockKs
    private lateinit var controller: TrabajadoresController

    private var test = Trabajador(1, UUID.fromString("27c1af75-3bd1-4a71-be4c-a498ab5e7d85"),
        "Test","Prueba","test@gmail.com","1234", disponible = true, administrador = false)

    init {
        MockKAnnotations.init(this)
    }


    @Test
    fun getTrabajadorByEmailAndPassword() {
        every{repository.findByEmail(test.email)} returns test
        var find = controller.getTrabajadorByEmailAndPassword(test.email, test.password)

        assertAll(
            { assertNotNull(find) },
            { assertEquals(find?.id, test.id) },
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
    fun addTrabajador() {
        every { repository.findByEmail(test.email) } returns null
        every { repository.save(test) } returns test
        every { repository.add(test) } returns test

        var add = controller.addTrabajador(test)

        assertAll(
            { assertEquals(add.id, test.id) },
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
    fun getAllTrabajadores() {
        every { repository.findAll() } returns listOf(test)
        var lista = controller.getAllTrabajadores()
        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, test.id) },
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
    fun getTrabajadorByUUID() {
        every{ repository.findByUUID(test.uuid!!)} returns test
        var find = controller.getTrabajadorByUUID(test.uuid!!)
        assertAll(
            { assertEquals(find.id, test.id) },
            { assertEquals(find.uuid, test.uuid) },
            { assertEquals(find.nombre, test.nombre) },
            { assertEquals(find.apellido, test.apellido) },
            { assertEquals(find.email, test.email) },
            { assertEquals(find.password, test.password) },
            { assertEquals(find.disponible, test.disponible) },
            { assertEquals(find.administrador, test.administrador) },
        )

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }
}