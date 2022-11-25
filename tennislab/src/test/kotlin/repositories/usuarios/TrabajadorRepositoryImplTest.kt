package repositories.usuarios

import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.usuarios.Trabajador
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


internal class TrabajadorRepositoryImplTest {
    private var trabTest: Trabajador = Trabajador(
        null, "Prueba", "Test", "prueba@gmail.com", "12345", disponible = true,
        administrador = false
    )
    private var repository = TrabajadorRepositoryImpl(TrabajadorDAO)


    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(TrabajadorTable)
            repository.deleteAll()
        }
    }


    @Test
    fun findByEmail() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.save(trabTest)
        var encontrado = repository.findByEmail(guardado.email)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, guardado.id) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertEquals(encontrado?.administrador, guardado.administrador) }
        )
    }

    @Test
    fun findById() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.save(trabTest)
        var encontrado = repository.findById(guardado.id!!)
        println(encontrado)
        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, guardado.id) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertEquals(encontrado?.administrador, guardado.administrador) }
        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.save(trabTest)
        var encontrado = repository.findByUUID(guardado.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, guardado.id) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertEquals(encontrado?.administrador, guardado.administrador) }
        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.save(trabTest)

        assertAll(
            { assertNotNull(guardado.id) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.nombre, trabTest.nombre) },
            { assertEquals(guardado.apellido, trabTest.apellido) },
            { assertEquals(guardado.email, trabTest.email) },
            { assertEquals(guardado.password, trabTest.password) },
            { assertEquals(guardado.disponible, trabTest.disponible) },
            { assertEquals(guardado.administrador, trabTest.administrador) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.add(trabTest)

        assertAll(
            { assertNotNull(guardado.id) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.nombre, trabTest.nombre) },
            { assertEquals(guardado.apellido, trabTest.apellido) },
            { assertEquals(guardado.email, trabTest.email) },
            { assertEquals(guardado.password, trabTest.password) },
            { assertEquals(guardado.disponible, trabTest.disponible) },
            { assertEquals(guardado.administrador, trabTest.administrador) }
        )
    }

    @Test
    fun delete() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.add(trabTest)
        var eliminado = repository.delete(guardado)
        var encontrado = repository.findByUUID(guardado.uuid!!)

        assertTrue(eliminado)
        assertFalse(encontrado?.disponible!!)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(TrabajadorTable)

        var guardado = repository.add(trabTest)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, guardado.id) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].nombre, guardado.nombre) },
            { assertEquals(lista[0].apellido, guardado.apellido) },
            { assertEquals(lista[0].email, guardado.email) },
            { assertEquals(lista[0].password, guardado.password) },
            { assertEquals(lista[0].disponible, guardado.disponible) },
            { assertEquals(lista[0].administrador, guardado.administrador) }
        )
    }

    @Test
    fun deleteAll() =transaction{
        SchemaUtils.create(TrabajadorTable)
        repository.save(trabTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}