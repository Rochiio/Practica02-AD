package repositories.pedidos


import models.pedidos.Pedido
import models.pedidos.Tarea
import models.usuarios.Cliente
import models.usuarios.Trabajador
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import repositories.usuarios.ClienteRepositoryImpl
import java.time.LocalDate
import java.util.*

internal class PedidoRepositoryImplTest {
    private var clienteTest = Cliente(
        uuid = UUID.fromString("e337c6ed-1735-4177-8239-c712e86f2e2d"),
        nombre = "Prueba",
        apellido = "Test",
        email = "pepe@gmailo",
        password = "1234",
        pedido = mutableListOf()
    )
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
    private var repository = PedidoRepositoryImpl()




    @BeforeEach
    fun setUp() {

        repository.deleteAll()

    }

    @Test
    fun findById() {

        var guardado = repository.save(pedidoTest)
        var encontrado = repository.findById(guardado.uuid)
        println(guardado)
        println(encontrado)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(guardado.uuid, encontrado?.uuid) },
            { assertEquals(guardado.estado, encontrado?.estado) },
            { assertEquals(guardado.cliente, encontrado?.cliente) },
            { assertEquals(guardado.fechaEntrada, encontrado?.fechaEntrada) },
            { assertEquals(guardado.fechaFinal, encontrado?.fechaFinal) },
            { assertEquals(guardado.fechaSalida, encontrado?.fechaSalida) },
            { assertEquals(guardado.precioTotal, encontrado?.precioTotal) },
            { assertEquals(guardado.topeEntrega, encontrado?.topeEntrega) }
        )
    }

    @Test
    fun save() {

        var guardado = repository.save(pedidoTest)

        assertAll(
            { assertNotNull(pedidoTest) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.estado, pedidoTest.estado) },
            { assertEquals(guardado.cliente, pedidoTest.cliente) },
            { assertEquals(guardado.fechaEntrada, pedidoTest.fechaEntrada) },
            { assertEquals(guardado.fechaFinal, pedidoTest.fechaFinal) },
            { assertEquals(guardado.fechaSalida, pedidoTest.fechaSalida) },
            { assertEquals(guardado.precioTotal, pedidoTest.precioTotal) },
            { assertEquals(guardado.topeEntrega, pedidoTest.topeEntrega) }
        )
    }

    @Test
    fun delete() {

        var guardado = repository.save(pedidoTest)
        var eliminado = repository.delete(guardado)
        var encontrado = repository.findById(guardado.uuid)

        assertTrue(eliminado)
        assertNull(encontrado)
    }

    @Test
    fun findAll() {

        var guardado = repository.save(pedidoTest)
        var lista = repository.findAll()


        assertAll(
            { assertNotNull(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].estado, guardado.estado) },
            { assertEquals(lista[0].cliente, guardado.cliente) },
            { assertEquals(lista[0].fechaEntrada, guardado.fechaEntrada) },
            { assertEquals(lista[0].fechaFinal, guardado.fechaFinal) },
            { assertEquals(lista[0].fechaSalida, guardado.fechaSalida) }
        )
    }

    @Test
    fun deleteAll() {
        repository.save(pedidoTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}