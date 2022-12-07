package controllers

import controller.TareasController
import entities.enums.TipoTarea
import exception.PedidoError
import exception.TareaError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import models.pedidos.Tarea
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedidos.TareaRepositoryImpl
import java.util.*

@ExtendWith(MockKExtension::class)
class TareasControllerTest {
    @MockK
    private lateinit var repository: TareaRepositoryImpl

    @InjectMockKs
    private lateinit var controller: TareasController


    private val tareaTest = Tarea(
        UUID.fromString("8121a77f-9ad6-4dd9-a9c6-5210503b2b1f"),
        null,
        UUID.fromString("1e13e02c-c976-4c3b-90ef-9a4c8c0aace0"),
        null,
        100L,
        TipoTarea.PERSONALIZADO,
        "descripcion",
        true
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addTarea() {
        every { repository.findById(tareaTest.uuid) } returns null
        every { repository.save(tareaTest) } returns tareaTest
        var res = controller.addTarea(tareaTest)
        assertAll(
            { assertNotNull(res) },
            { assertEquals(tareaTest, res) }
        )

    }

    @Test
    fun addTareaError() {
        every { repository.findById(tareaTest.uuid) } returns tareaTest
        var exception = assertThrows(TareaError::class.java) {
            controller.addTarea(tareaTest)
        }

        assertEquals("Ya existe un tarea con el mismo UUID", exception.item)
    }

    @Test
    fun getPedidos() {
        every { repository.findAll() } returns listOf(tareaTest)
        every { repository.save(tareaTest) } returns tareaTest

        val res = controller.getTareas()
        assertTrue(res.isNotEmpty())
        assertEquals(res[0], tareaTest)
    }

    @Test
    fun updatePedido() {
        every { repository.save(tareaTest) } returns tareaTest
        tareaTest.tipoTarea = TipoTarea.ENCORDADO
        val res = controller.updateTarea(tareaTest)
        assertEquals(tareaTest, res)
    }

    @Test
    fun deletePedido() {
        every { repository.delete(tareaTest) } returns true
        val res = controller.deleteTarea(tareaTest)
        assertTrue(res)
    }

    @Test
    fun deletePedidoError() {
        every { repository.delete(tareaTest) } returns false
        var exception = assertThrows(TareaError::class.java) {
            controller.deleteTarea(tareaTest)
        }
        assertEquals("Error al eliminar la tarea", exception.item)
    }

    @Test
    fun getPedidoByUUID() {
        every { repository.findById(tareaTest.uuid) } returns tareaTest
        val res = controller.getTareaByUUID(tareaTest.uuid)

        assertEquals(tareaTest, res)
    }
}