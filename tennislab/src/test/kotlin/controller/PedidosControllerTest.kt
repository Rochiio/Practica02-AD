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

    private var test = Pedido(null, Estado.EN_PROCESO, LocalDate.now(), LocalDate.now(), null, 500.52f,
        LocalDate.now(), null, ArrayList<Tarea>())

    init {
        MockKAnnotations.init(this)
    }

    /*@Test
    fun addPedido() {
        every{ repository.findByUUID(test.uuid!!)} returns null
        every { repository.save(test) } returns test
         var add = controller.addPedido(test)
        assertAll(
            { assertEquals(add.uuid, test.uuid)},
            { assertEquals(add.estado, test.estado)},
            { assertEquals(add.fechaEntrada, test.fechaEntrada)},
            { assertEquals(add.fechaFinal, test.fechaFinal)},
            { assertEquals(add.fechaSalida, test.fechaSalida)},
            { assertEquals(add.precioTotal, test.precioTotal)},
            { assertEquals(add.topeEntrega, test.topeEntrega)},
            { assertEquals(add.cliente, test.cliente)},
            { assertEquals(add.tareas, test.tareas)}
        )

        verify(exactly=1){repository.findByUUID(test.uuid!!)}
        verify(exactly=1){repository.save(test)}
    }*/

    /*@Test
    fun addPedidoYaExiste() {
        every { repository.findByUUID(test.uuid!!) } returns test
        var exception = assertThrows(PedidoError::class.java){
            controller.addPedido(test)
        }

        assertEquals("Ya existe un pedido con el mismo UUID", exception.item)

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }*/

    @Test
    fun getPedidos() {
        every{ repository.findAll() } returns listOf(test)
        var lista = controller.getPedidos()
        assertAll(
            { assertTrue(lista.isNotEmpty())},
            { assertEquals(lista[0].uuid, test.uuid)},
            { assertEquals(lista[0].estado, test.estado)},
            { assertEquals(lista[0].fechaEntrada, test.fechaEntrada)},
            { assertEquals(lista[0].fechaFinal, test.fechaFinal)},
            { assertEquals(lista[0].fechaSalida, test.fechaSalida)},
            { assertEquals(lista[0].precioTotal, test.precioTotal)},
            { assertEquals(lista[0].topeEntrega, test.topeEntrega)},
            { assertEquals(lista[0].cliente, test.cliente)},
            { assertEquals(lista[0].tareas, test.tareas)}
        )

        verify(exactly=1){ repository.findAll() }
    }

    @Test
    fun deletePedido() {
        every { repository.delete(test) } returns true
        var eliminado = controller.deletePedido(test)
        assertTrue(eliminado)
        verify(exactly=1){repository.delete(test)}
    }

    @Test
    fun deletePedidoError(){
        every{ repository.delete(test)} returns false
        var exception = assertThrows(PedidoError::class.java){
            controller.deletePedido(test)
        }
        assertEquals("Error al eliminar el pedido", exception.item)
        verify(exactly=1){repository.delete(test)}
    }

    /*@Test
    fun getPedidoByUUID() {
        every { repository.findByUUID(test.uuid!!) } returns test
        var find = controller.getPedidoByUUID(test.uuid!!)
        assertAll(
            { assertNotNull(find)},
            { assertEquals(find?.uuid, test.uuid)},
            { assertEquals(find?.estado, test.estado)},
            { assertEquals(find?.fechaEntrada, test.fechaEntrada)},
            { assertEquals(find?.fechaFinal, test.fechaFinal)},
            { assertEquals(find?.fechaSalida, test.fechaSalida)},
            { assertEquals(find?.precioTotal, test.precioTotal)},
            { assertEquals(find?.topeEntrega, test.topeEntrega)},
            { assertEquals(find?.cliente, test.cliente)},
            { assertEquals(find?.tareas, test.tareas)}
        )

        verify(exactly=1){ repository.findByUUID(test.uuid!!)}
    }*/
}