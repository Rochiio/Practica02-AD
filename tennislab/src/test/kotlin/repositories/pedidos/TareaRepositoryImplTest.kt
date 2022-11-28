package repositories.pedidos

import entities.enums.TipoTarea
import entities.pedidos.TareaDAO
import entities.pedidos.TareaTable
import entities.usuarios.TrabajadorTable
import models.pedidos.Tarea
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class TareaRepositoryImplTest {

    private var tareaTest = Tarea(null, null, UUID.randomUUID(), UUID.randomUUID(), "descripcion", 10, TipoTarea.ENCORDADO, true)
    private var repository = TareaRepositoryImpl(TareaDAO)

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(TareaTable)
            repository.deleteAll()
        }
    }

    @Test
    fun findById()  = transaction{
        SchemaUtils.create(TareaTable)
        var guardado = repository.save(tareaTest)
        var encontrado = repository.findById(guardado.id!!)
        println(guardado)
        println(encontrado)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(guardado.uuid, encontrado?.uuid) },
            { assertEquals(guardado.id, encontrado?.id) },
            { assertEquals(guardado.idMaquina, encontrado?.idMaquina) },
            { assertEquals(guardado.idTrabajador, encontrado?.idTrabajador) },
            { assertEquals(guardado.precio, encontrado?.precio) },
            {assertEquals(guardado.descripcion, encontrado?.descripcion)},
            { assertEquals(guardado.tipoTarea, encontrado?.tipoTarea) }
        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(TareaTable)
        var guardado = repository.save(tareaTest)
        var encontrado = repository.findByUUID(guardado.uuid!!)
        println(guardado)
        println(encontrado)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(guardado.uuid, encontrado?.uuid) },
            { assertEquals(guardado.id, encontrado?.id) },
            { assertEquals(guardado.idMaquina, encontrado?.idMaquina) },
            { assertEquals(guardado.idTrabajador, encontrado?.idTrabajador) },
            { assertEquals(guardado.precio, encontrado?.precio) },
            {assertEquals(guardado.descripcion, encontrado?.descripcion)},
            { assertEquals(guardado.tipoTarea, encontrado?.tipoTarea) }
        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(TareaTable)

        var guardado = repository.save(tareaTest)

        assertAll(
            { assertNotNull(tareaTest) },
            { assertEquals(guardado.id, 1) },
            { assertEquals(guardado.idMaquina, tareaTest.idMaquina) },
            { assertEquals(guardado.idTrabajador, tareaTest.idTrabajador) },
            { assertEquals(guardado.precio, tareaTest.precio) },
            {assertEquals(guardado.descripcion, tareaTest.descripcion)},
            { assertEquals(guardado.tipoTarea, tareaTest.tipoTarea) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(TareaTable)

        var guardado = repository.add(tareaTest)

        assertAll(
            { assertNotNull(tareaTest) },
            { assertEquals(guardado.id, 1) },
            { assertEquals(guardado.idMaquina, tareaTest.idMaquina) },
            { assertEquals(guardado.idTrabajador, tareaTest.idTrabajador) },
            { assertEquals(guardado.precio, tareaTest.precio) },
            {assertEquals(guardado.descripcion, tareaTest.descripcion)},
            { assertEquals(guardado.tipoTarea, tareaTest.tipoTarea) }
        )
    }

    @Test
    fun delete() = transaction{
        SchemaUtils.create(TareaTable)

        var guardado = repository.add(tareaTest)
        var eliminado = repository.delete(guardado)
        var encontrado = repository.findByUUID(guardado.uuid!!)

        assertTrue(eliminado)
        assertFalse(encontrado?.disponible!!)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(TareaTable)

        var guardado = repository.add(tareaTest)
        var lista = repository.findAll()


        assertAll(
            { assertNotNull(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].id, guardado.id) },
            { assertEquals(lista[0].idMaquina, guardado.idMaquina) },
            { assertEquals(lista[0].idTrabajador, guardado.idTrabajador) },
            { assertEquals(lista[0].precio, guardado.precio) },
            {assertEquals(lista[0].descripcion, guardado.descripcion)},
            { assertEquals(lista[0].tipoTarea, guardado.tipoTarea) }
        )
    }

    @Test
    fun deleteAll()  =transaction{
        SchemaUtils.create(TareaTable)
        repository.save(tareaTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}