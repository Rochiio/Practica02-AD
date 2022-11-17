package repositories.usuarios

import entities.UsuarioDAO
import entities.UsuarioTable
import models.usuarios.Usuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class UsuarioRepositoryImplTest {
    private var usuarioTest: Usuario = Usuario(UUID.randomUUID(),"Prueba","Test","prueba@gmail.com","123456",true)
    private var repository = UsuarioRepositoryImpl(UsuarioDAO)


    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(UsuarioTable)
            repository.deleteAll()
        }
    }


    @Test
    fun findById() = transaction{
        SchemaUtils.create(UsuarioTable)
        var saveItem = repository.save(usuarioTest)
        var encontrado = repository.findById(saveItem.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, saveItem.uuid) },
            { assertEquals(encontrado?.nombre, saveItem.nombre) },
            { assertEquals(encontrado?.apellido, saveItem.apellido) },
            { assertEquals(encontrado?.email, saveItem.email) },
            { assertEquals(encontrado?.password, saveItem.password) }
        )
    }

    @Test
    fun findByUUID()= transaction {
        SchemaUtils.create(UsuarioTable)
        var saveItem = repository.save(usuarioTest)
        var encontrado = repository.findByUUID(saveItem.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, saveItem.uuid) },
            { assertEquals(encontrado?.nombre, saveItem.nombre) },
            { assertEquals(encontrado?.apellido, saveItem.apellido) },
            { assertEquals(encontrado?.email, saveItem.email) },
            { assertEquals(encontrado?.password, saveItem.password) }
        )
    }

    @Test
    fun save() =transaction{
        SchemaUtils.create(UsuarioTable)
        var saveItem = repository.save(usuarioTest)

        assertAll(
            { assertEquals(saveItem.nombre, usuarioTest.nombre) },
            { assertEquals(saveItem.apellido, usuarioTest.apellido) },
            { assertEquals(saveItem.email, usuarioTest.email) },
            { assertEquals(saveItem.password, usuarioTest.password) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(UsuarioTable)
        var saveItem = repository.add(usuarioTest)

        assertAll(
            { assertEquals(saveItem.nombre, usuarioTest.nombre) },
            { assertEquals(saveItem.apellido, usuarioTest.apellido) },
            { assertEquals(saveItem.email, usuarioTest.email) },
            { assertEquals(saveItem.password, usuarioTest.password) }
        )

    }


    @Test
    fun delete() =transaction{
        SchemaUtils.create(UsuarioTable)
        var saveItem = repository.save(usuarioTest)
        var eliminado = repository.delete(saveItem)
        var find = repository.findById(saveItem.uuid!!)

        assertAll(
            { assertTrue (eliminado) },
            { assertNull(find) }
        )
    }

    @Test
    fun findAll() =transaction{
        SchemaUtils.create(UsuarioTable)
        var saveItem = repository.save(usuarioTest)
        var lista = repository.findAll()
        assertAll(
            { assertEquals(lista[0].uuid, saveItem.uuid) },
            { assertEquals(lista[0].nombre, saveItem.nombre) },
            { assertEquals(lista[0].apellido, saveItem.apellido) },
            { assertEquals(lista[0].email, saveItem.email) },
            { assertEquals(lista[0].password, saveItem.password) }
        )


    }


    @Test
    fun deleteAll() =transaction{
        SchemaUtils.create(UsuarioTable)
        repository.save(usuarioTest)
        var result = repository.deleteAll()

        assertTrue(result)
    }
}