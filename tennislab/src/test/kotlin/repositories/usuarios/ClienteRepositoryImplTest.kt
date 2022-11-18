package repositories.usuarios

import entities.usuarios.*
import models.usuarios.Cliente
import models.usuarios.Trabajador
import models.usuarios.Usuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class ClienteRepositoryImplTest {

    private var usuarioTest: Usuario = Usuario(UUID.randomUUID(),"Prueba","Test","prueba@gmail.com","123456",true)
    private lateinit var usuarioCliente: Usuario
    private lateinit var clienteTest: Cliente
    private var repoUsuario = UsuarioRepositoryImpl(UsuarioDAO)
    private var repository = ClienteRepositoryImpl(ClienteDAO,UsuarioDAO)


    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(UsuarioTable, ClienteTable)
            repository.deleteAll()
            repoUsuario.deleteAll()

            usuarioCliente = repoUsuario.save(usuarioTest)
            clienteTest = Cliente(UUID.randomUUID(),usuarioCliente)
        }
    }


    @Test
    fun findById() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)

        var guardado = repository.save(clienteTest)
        var encontrado = repository.findById(guardado.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.usuario, guardado.usuario) },
        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)

        var guardado = repository.save(clienteTest)
        var encontrado = repository.findByUUID(guardado.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.usuario, guardado.usuario) }
        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)

        var guardado = repository.save(clienteTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.usuario, clienteTest.usuario) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)

        var guardado = repository.add(clienteTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertNotNull(guardado.uuid) },
            { assertEquals(guardado.usuario, clienteTest.usuario) }
        )
    }

    @Test
    fun delete() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)

        var guardado = repository.add(clienteTest)
        var eliminado = repository.delete(guardado)

        assertTrue(eliminado)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)

        var guardado = repository.add(clienteTest)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].usuario, guardado.usuario) }
        )
    }

    @Test
    fun deleteAll() = transaction{
        SchemaUtils.create(UsuarioTable, ClienteTable)
        repository.save(clienteTest)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }

}