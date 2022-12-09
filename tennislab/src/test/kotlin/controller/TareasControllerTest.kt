package controller

import entities.enums.TipoTarea
import exception.ClienteError
import exception.TareaError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.pedidos.Tarea
import models.usuarios.Cliente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedidos.TareaRepositoryImpl
import repositories.usuarios.ClienteRepositoryImpl
import java.util.*

@ExtendWith(MockKExtension::class)
class TareasControllerTest {
    @MockK
    private lateinit var repository: TareaRepositoryImpl

    @InjectMockKs
    private lateinit var controller: TareasController

    private var test = Tarea(1, UUID.randomUUID(), null, UUID.randomUUID(), "descripcion",
        12.50.toLong(), TipoTarea.ENCORDADO, true)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addTarea() {
        every { repository.findByUUID(test.uuid!!) } returns null
        every { repository.save(test) } returns test

        var add = controller.addTarea(test)

        assertAll(
            { assertEquals(add.id, test.id) },
            { assertEquals(add.uuid, test.uuid) },
            { assertEquals(add.idTrabajador, test.idTrabajador)},
            { assertEquals(add.idMaquina, test.idMaquina)},
            { assertEquals(add.descripcion, test.descripcion)},
            { assertEquals(add.precio, test.precio)},
            { assertEquals(add.tipoTarea, test.tipoTarea)},
            { assertEquals(add.disponible, test.disponible)}
        )

        verify(exactly=1){repository.findByUUID(test.uuid!!)}
        verify(exactly=1){repository.save(test)}
    }

    @Test
    fun addTareaYaExiste(){
        every { repository.findByUUID(test.uuid!!)} returns test
        var exception = assertThrows(TareaError::class.java){
            controller.addTarea(test)
        }

        assertEquals("Ya existe un tarea con el mismo UUID", exception.item)

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }

    @Test
    fun getTareas() {
        every { repository.findAll()} returns listOf(test)
        var lista = controller.getTareas()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, test.id) },
            { assertEquals(lista[0].uuid, test.uuid) },
            { assertEquals(lista[0].idTrabajador, test.idTrabajador)},
            { assertEquals(lista[0].idMaquina, test.idMaquina)},
            { assertEquals(lista[0].descripcion, test.descripcion)},
            { assertEquals(lista[0].precio, test.precio)},
            { assertEquals(lista[0].tipoTarea, test.tipoTarea)},
            { assertEquals(lista[0].disponible, test.disponible)}
        )

        verify(exactly=1){ repository.findAll() }
    }

    @Test
    fun deleteTarea() {
        every { repository.delete(test)} returns true
        var eliminado = controller.deleteTarea(test)

        assertTrue(eliminado)

        verify(exactly=1){ repository.delete(test)}
    }

    @Test
    fun deleteTareaErrorEliminar(){
        every { repository.delete(test) } returns false
        var exception = assertThrows(TareaError::class.java){
            controller.deleteTarea(test)
        }

        assertEquals("Error al eliminar la tarea", exception.item)

        verify(exactly=1){ repository.delete(test) }
    }

    @Test
    fun getTareaByUUID() {
        every { repository.findByUUID(test.uuid!!) } returns test
        var find = controller.getTareaByUUID(test.uuid!!)

        assertAll(
            { assertNotNull(find) },
            { assertEquals(find?.id, test.id) },
            { assertEquals(find?.uuid, test.uuid) },
            { assertEquals(find?.idTrabajador, test.idTrabajador)},
            { assertEquals(find?.idMaquina, test.idMaquina)},
            { assertEquals(find?.descripcion, test.descripcion)},
            { assertEquals(find?.precio, test.precio)},
            { assertEquals(find?.tipoTarea, test.tipoTarea)},
            { assertEquals(find?.disponible, test.disponible)}
        )

        verify(exactly=1){ repository.findByUUID(test.uuid!!)}
    }
}