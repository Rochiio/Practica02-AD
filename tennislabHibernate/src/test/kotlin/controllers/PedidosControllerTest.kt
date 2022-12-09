package controllers

import controller.PedidosController
import exception.PedidoError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import models.pedidos.Pedido
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedidos.PedidoRepositoryImpl
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
        estado = "RECIBIDO",
        cliente = null,
        fechaEntrada = LocalDate.now(),
        fechaFinal = LocalDate.now(),
        fechaSalida = LocalDate.now(),
        precioTotal = 100F,
        topeEntrega = LocalDate.now(),
        tareas = mutableListOf()
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addPedido() {
        every { repository.findById(pedidoTest.uuid) } returns null
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

    }

    @Test
    fun addPedidoError() {
        every { repository.findById(pedidoTest.uuid) } returns pedidoTest
        var exception = assertThrows(PedidoError::class.java) {
            controller.addPedido(pedidoTest)
        }

        assertEquals("Ya existe un pedido con el mismo UUID", exception.item)
    }

    @Test
    fun getPedidos() {
        every { repository.findAll() } returns listOf(pedidoTest)
        every { repository.save(pedidoTest) } returns pedidoTest

        val res = controller.getPedidos()
        assertTrue(res.isNotEmpty())
        assertEquals(res[0], pedidoTest)
    }

    @Test
    fun updatePedido() {
        every { repository.save(pedidoTest) } returns pedidoTest
        pedidoTest.precioTotal = 200F
        val res = controller.updatePedido(pedidoTest)
        assertEquals(pedidoTest, res)
    }

    @Test
    fun deletePedido() {
        every { repository.delete(pedidoTest) } returns true
        val res = controller.deletePedido(pedidoTest)
        assertTrue(res)
    }

    @Test
    fun deletePedidoError() {
        every { repository.delete(pedidoTest) } returns false
        var exception = assertThrows(PedidoError::class.java) {
            controller.deletePedido(pedidoTest)
        }
        assertEquals("Error al eliminar el pedido", exception.item)
    }

    @Test
    fun getPedidoByUUID() {
        every { repository.findById(pedidoTest.uuid) } returns pedidoTest
        val res = controller.getPedidoByUUID(pedidoTest.uuid)

        assertEquals(pedidoTest, res)
    }
}