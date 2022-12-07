package repositories.usuarios

import models.usuarios.Cliente
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ClienteRepositoryImplTest {
    private var repository = ClienteRepositoryImpl()
    private var test = Cliente(nombre="Prueba", apellido = "Test", email = "pepe@gmailo", password = "1234", pedido = mutableListOf())

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun findByEmail() {
        var guardado = repository.save(test)
        var encontrado = repository.findByEmail(guardado.email)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) }
        )
    }

    @Test
    fun findById() {
        var guardado = repository.save(test)
        var encontrado = repository.findById(guardado.uuid)

        assertAll(
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.nombre, guardado.nombre) },
            { assertEquals(encontrado?.apellido, guardado.apellido) },
            { assertEquals(encontrado?.email, guardado.email) },
            { assertEquals(encontrado?.password, guardado.password) }
        )
    }

    @Test
    fun save() {
        var guardado = repository.save(test)

        assertAll(
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.nombre, test.nombre) },
            { assertEquals(guardado.apellido, test.apellido) },
            { assertEquals(guardado.email, test.email) },
            { assertEquals(guardado.password, test.password) }
        )
    }

    @Test
    fun delete() {
        var guardado = repository.save(test)
        var eliminado = repository.delete(guardado)

        assertTrue(eliminado)
    }

    @Test
    fun findAll() {
        var guardado = repository.save(test)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].nombre, guardado.nombre) },
            { assertEquals(lista[0].apellido, guardado.apellido) },
            { assertEquals(lista[0].email, guardado.email) },
            { assertEquals(lista[0].password, guardado.password) }
        )
    }

    @Test
    fun deleteAll() {
        repository.save(test)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}