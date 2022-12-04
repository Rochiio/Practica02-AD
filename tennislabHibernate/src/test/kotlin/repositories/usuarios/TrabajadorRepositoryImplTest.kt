package repositories.usuarios

import models.usuarios.Trabajador
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TrabajadorRepositoryImplTest {
    private var repo = TrabajadorRepositoryImpl()
    private var test = Trabajador(nombre="Prueba", apellido = "Test", email = "pepe@gmailo", password = "1234",
        disponible = true, administrador = false)

    @BeforeEach
    fun setUp() {
        repo.deleteAll()
    }

    @Test
    fun findByEmail() {
        var guardado = repo.save(test)
        var encontrado = repo.findByEmail(guardado.email)

        assertAll(
            { assertNotNull(encontrado) },
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
    fun findById() {
        var guardado = repo.save(test)
        var encontrado = repo.findById(guardado.uuid)
        assertAll(
            { assertNotNull(encontrado) },
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
    fun save() {
        var guardado = repo.save(test)

        assertAll(
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.nombre, test.nombre) },
            { assertEquals(guardado.apellido, test.apellido) },
            { assertEquals(guardado.email, test.email) },
            { assertEquals(guardado.password, test.password) },
            { assertEquals(guardado.disponible, test.disponible) },
            { assertEquals(guardado.administrador, test.administrador) }
        )
    }

    @Test
    fun delete() {
        var guardado = repo.save(test)
        var eliminado = repo.delete(guardado)
        var encontrado = repo.findById(guardado.uuid)

        assertTrue(eliminado)
        assertFalse(encontrado?.disponible!!)
    }

    @Test
    fun findAll() {
        var guardado = repo.save(test)
        var lista = repo.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
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
    fun deleteAll() {
        repo.save(test)
        var delete = repo.deleteAll()

        assertTrue(delete)
    }
}