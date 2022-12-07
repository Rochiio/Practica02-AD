package repositories.pedidos

import entities.enums.TipoTarea
import models.pedidos.Pedido
import models.pedidos.Tarea
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import java.util.UUID

class TareaRepositoryImplTest {


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

    private val repository = TareaRepositoryImpl()

    @BeforeEach
    fun setUp() {

        repository.deleteAll()

    }

    @Test
    fun findById() {

        var guardado = repository.save(tareaTest)
        var encontrado = repository.findById(guardado.uuid)
        println(guardado)
        println(encontrado)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(guardado, encontrado) }
        )
    }

    @Test
    fun save() {
        var guardado = repository.save(tareaTest)
        assertAll(
            { assertNotNull(guardado) },
            { assertNotNull(guardado.uuid) }

        )
    }

    @Test
    fun delete() {
        var guardado = repository.save(tareaTest)
        var eliminado = repository.delete(guardado)
        var encontrado = repository.findById(guardado.uuid)

        assertTrue(eliminado)
        assertNull(encontrado)
    }

    @Test
    fun findAll() {

        var guardado = repository.save(tareaTest)
        var lista = repository.findAll()


        assertAll(
            { assertNotNull(lista.isNotEmpty()) },
            { assertEquals(lista[0], guardado) })
    }

    @Test
    fun deleteAll() {
        repository.save(tareaTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}