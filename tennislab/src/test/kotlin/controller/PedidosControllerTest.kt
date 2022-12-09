package controller

import entities.enums.Estado
import entities.enums.TipoTarea
import exception.PedidoError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.pedidos.Pedido
import models.pedidos.Tarea
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedidos.PedidoRepositoryImpl
import repositories.pedidos.TareaRepositoryImpl
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class PedidosControllerTest {

    @MockK
    private lateinit var repository: PedidoRepositoryImpl

    @InjectMockKs
    private lateinit var controller: PedidosController

    private val pedidoTest = Pedido(
        uuid = UUID.fromString("c2666cfe-f7cb-4990-8fa6-ab85fe44bb9e"),
        estado = Estado.RECIBIDO,
        cliente = null,
        fechaEntrada = LocalDate.now(),
        fechaFinal = LocalDate.now(),
        fechaSalida = LocalDate.now(),
        precioTotal = 100F,
        topeEntrega = LocalDate.now(),
        tareas = arrayListOf()
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addPedido() {
        every { repository.findByUUID(pedidoTest.uuid!!) } returns null
        every { repository.save(pedidoTest) } returns pedidoTest
        var res = controller.addPedido(pedidoTest)
        assertAll(
            { assertNotNull(res) },
            { assertEquals(pedidoTest.uuid, res.uuid) },
            { assertEquals(pedidoTest.estado, res.estado) },
            { assertEquals(pedidoTest.cliente, res.cliente) },
            { assertEquals(pedidoTest.fechaEntrada, res.fechaEntrada) },
            { assertEquals(pedidoTest.fechaFinal, res.fechaFinal) },
            { assertEquals(pedidoTest.fechaSalida, res.fechaSalida) },
            { assertEquals(pedidoTest.precioTotal, res.precioTotal) },
            { assertEquals(pedidoTest.topeEntrega, res.topeEntrega) }
        )

        verify(exactly=1){repository.findByUUID(pedidoTest.uuid!!)}
        verify(exactly=1){repository.save(pedidoTest)}
    }

    @Test
    fun addPedidoError() {
        every { repository.findByUUID(pedidoTest.uuid!!) } returns pedidoTest
        var exception = assertThrows(PedidoError::class.java) {
            controller.addPedido(pedidoTest)
        }

        assertEquals("Ya existe un pedido con el mismo UUID", exception.item)

        verify(exactly=1){repository.findByUUID(pedidoTest.uuid!!)}
    }

    @Test
    fun getPedidos() {
        every { repository.findAll() } returns listOf(pedidoTest)

        val res = controller.getPedidos()
        assertTrue(res.isNotEmpty())
        assertEquals(res[0], pedidoTest)

        verify(exactly=1){repository.findAll()}
    }

    @Test
    fun updatePedido() {
        every { repository.save(pedidoTest) } returns pedidoTest
        pedidoTest.precioTotal = 200F
        val res = controller.updatePedido(pedidoTest)
        assertEquals(pedidoTest, res)

        verify(exactly=1){repository.save(pedidoTest)}
    }

    @Test
    fun deletePedido() {
        every { repository.delete(pedidoTest) } returns true
        val res = controller.deletePedido(pedidoTest)
        assertTrue(res)
        verify(exactly=1){repository.delete(pedidoTest)}
    }

    @Test
    fun deletePedidoError() {
        every { repository.delete(pedidoTest) } returns false
        var exception = assertThrows(PedidoError::class.java) {
            controller.deletePedido(pedidoTest)
        }
        assertEquals("Error al eliminar el pedido", exception.item)
        verify(exactly=1){repository.delete(pedidoTest)}
    }

    @Test
    fun getPedidoByUUID() {
        every { repository.findByUUID(pedidoTest.uuid!!) } returns pedidoTest
        val res = controller.getPedidoByUUID(pedidoTest.uuid!!)

        assertEquals(pedidoTest, res)

        verify(exactly=1){repository.findByUUID(pedidoTest.uuid!!)}
    }
}