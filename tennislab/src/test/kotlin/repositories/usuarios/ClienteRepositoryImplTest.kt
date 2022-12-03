package repositories.usuarios

import entities.usuarios.*
import models.usuarios.Cliente
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class ClienteRepositoryImplTest {

    private var clienteTest: Cliente = Cliente(1, UUID.randomUUID(),"Prueba","test","cliente@prueba.com","123456", null)
    private var repository = ClienteRepositoryImpl(ClienteDAO)


    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(ClienteTable)
            repository.deleteAll()
        }
    }


    @Test
    fun findById() = transaction{
        SchemaUtils.create(ClienteTable)

        var guardado = repository.save(clienteTest)
        var encontrado = repository.findById(guardado.id!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, guardado.id) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) }
        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(ClienteTable)

        var guardado = repository.save(clienteTest)
        var encontrado = repository.findByUUID(guardado.uuid!!)

        assertAll(
            { assertEquals(encontrado?.id, guardado.id) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) }
        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(ClienteTable)

        var guardado = repository.save(clienteTest)

        assertAll(
            { assertNotNull(guardado.id) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.nombre, clienteTest.nombre) },
            { assertEquals(guardado.apellido, clienteTest.apellido) },
            { assertEquals(guardado.email, clienteTest.email) },
            { assertEquals(guardado.password, clienteTest.password) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(ClienteTable)

        var guardado = repository.add(clienteTest)

        assertAll(
            { assertNotNull(guardado.id) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.nombre, clienteTest.nombre) },
            { assertEquals(guardado.apellido, clienteTest.apellido) },
            { assertEquals(guardado.email, clienteTest.email) },
            { assertEquals(guardado.password, clienteTest.password) }
        )
    }

    @Test
    fun delete() = transaction{
        SchemaUtils.create(ClienteTable)

        var guardado = repository.add(clienteTest)
        var eliminado = repository.delete(guardado)

        assertTrue(eliminado)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(ClienteTable)

        var guardado = repository.add(clienteTest)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, guardado.id) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].nombre, guardado.nombre) },
            { assertEquals(lista[0].apellido, guardado.apellido) },
            { assertEquals(lista[0].email, guardado.email) },
            { assertEquals(lista[0].password, guardado.password) }
        )
    }

    @Test
    fun deleteAll() = transaction{
        SchemaUtils.create(ClienteTable)
        repository.save(clienteTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }

}