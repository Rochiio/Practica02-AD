package repositories.usuarios

import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import entities.usuarios.UsuarioDAO
import entities.usuarios.UsuarioTable
import models.usuarios.Trabajador
import models.usuarios.Usuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

/**
 * TODO creo que es por el save, hay algo mal, pero no se si es por culpa del guardado del usuario que no deberia.
 * O por el propio guardado de trabajador que intenta hacer un new o algo asi.
 */
internal class TrabajadorRepositoryImplTest {
    private var usuarioTest: Usuario = Usuario(UUID.randomUUID(),"Prueba","Test","prueba@gmail.com","123456",true)
    private lateinit var usuarioTrabajador: Usuario
    private lateinit var trabTest: Trabajador
    private var repoUsuario = UsuarioRepositoryImpl(UsuarioDAO)
    private var repository = TrabajadorRepositoryImpl(TrabajadorDAO, UsuarioDAO)

private lateinit var tDAO : TrabajadorDAO
    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(UsuarioTable, TrabajadorTable)
            repository.deleteAll()
            repoUsuario.deleteAll()

            usuarioTrabajador = repoUsuario.save(usuarioTest)
            trabTest = Trabajador(usuarioTrabajador.uuid,usuarioTrabajador,false)

        }
    }


    @Test
    fun findById() = transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)

        var guardado = repository.save(trabTest)
        var encontrado = repository.findById(guardado.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.usuario, guardado.usuario) },
            { assertEquals(encontrado?.administrador, guardado.administrador)}
        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)

        var guardado = repository.save(trabTest)
        var encontrado = repository.findByUUID(guardado.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.usuario, guardado.usuario) },
            { assertEquals(encontrado?.administrador, guardado.administrador)}
        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)

        var guardado = repository.save(trabTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.usuario, trabTest.usuario) },
            { assertEquals(guardado.administrador, trabTest.administrador) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)

        var guardado = repository.add(trabTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.usuario, trabTest.usuario) },
            { assertEquals(guardado.administrador, trabTest.administrador) }
        )
    }

    @Test
    fun delete() = transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)

        var guardado = repository.add(trabTest)
        var eliminado = repository.delete(guardado)

        assertTrue(eliminado)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)

        var guardado = repository.add(trabTest)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].usuario, guardado.usuario) },
            { assertEquals(lista[0].administrador, guardado.administrador) }
        )
    }

    @Test
    fun deleteAll() =transaction{
        SchemaUtils.create(UsuarioTable, TrabajadorTable)
        repository.save(trabTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}