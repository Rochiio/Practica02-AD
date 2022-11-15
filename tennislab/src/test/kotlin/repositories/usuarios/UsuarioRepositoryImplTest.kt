package repositories.usuarios

import models.Usuario
import models.Usuarios
import models.enums.tipoUsuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UsuarioRepositoryImplTest {
    private var repo = UsuarioRepositoryImpl()
    private lateinit var usuarioTest:Usuario

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
                    SchemaUtils.create(Usuarios)
                    usuarioTest = Usuario.new {
                    nombre = "Prueba"
                    apellido = "test"
                    email = "email@email.com"
                    password = "esryu5ert454"
                    tipo = tipoUsuario.TRABAJADOR.toString()
                    disponible = true
                }
            }
        }


    @Test
    fun findById()= transaction {
        SchemaUtils.create(Usuarios)

        var add = repo.add(usuarioTest)
        var encontrado = repo.findById(add.id.value)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, add.id)},
            { assertEquals(encontrado?.uuid, add.uuid) },
            { assertEquals(encontrado?.nombre, add.nombre) },
            { assertEquals(encontrado?.apellido, add.apellido) },
            { assertEquals(encontrado?.email, add.email) },
            { assertEquals(encontrado?.password, add.password) },
            { assertEquals(encontrado?.tipo, add.tipo) },
            { assertEquals(encontrado?.disponible, add.disponible) }
        )
    }

    @Test
    fun findByUUID()= transaction {
        SchemaUtils.create(Usuarios)

        var add = repo.add(usuarioTest)
        var encontrado = repo.findByUUID(add.uuid)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, add.id)},
            { assertEquals(encontrado?.uuid, add.uuid) },
            { assertEquals(encontrado?.nombre, add.nombre) },
            { assertEquals(encontrado?.apellido, add.apellido) },
            { assertEquals(encontrado?.email, add.email) },
            { assertEquals(encontrado?.password, add.password) },
            { assertEquals(encontrado?.tipo, add.tipo) },
            { assertEquals(encontrado?.disponible, add.disponible) }
        )
    }

    @Test
    fun add()= transaction {
        SchemaUtils.create(Usuarios)

        var add = repo.add(usuarioTest)

        assertAll(
            { assertNotNull(add) },
            { assertEquals(add.nombre, usuarioTest.nombre) },
            { assertEquals(add.apellido, usuarioTest.apellido) },
            { assertEquals(add.email, usuarioTest.email) },
            { assertEquals(add.password, usuarioTest.password) },
            { assertEquals(add.tipo, usuarioTest.tipo) },
            { assertEquals(add.disponible, usuarioTest.disponible) }
        )
    }

    @Test
    fun update()= transaction {
        SchemaUtils.create(Usuarios)

        var add = repo.add(usuarioTest)
        var actualizar = Usuario.new {
            nombre = "Actualizado"
            apellido = "test"
            email = "actu@email.com"
            password = "esryu5ert454"
            tipo = tipoUsuario.TRABAJADOR.toString()
            disponible = true
        }
        var consulta = repo.update(add,actualizar)

        assertAll(
            { assertEquals(consulta.id, add.id)},
            { assertEquals(consulta.uuid, add.uuid) },
            { assertEquals(consulta.nombre, actualizar.nombre) },
            { assertEquals(consulta.apellido, actualizar.apellido) },
            { assertEquals(consulta.email, actualizar.email) },
            { assertEquals(consulta.password, actualizar.password) },
            { assertEquals(consulta.tipo, actualizar.tipo) },
            { assertEquals(consulta.disponible, actualizar.disponible) }
        )
    }


    @Test
    fun delete()= transaction {
        SchemaUtils.create(Usuarios)

        var add = repo.add(usuarioTest)
        var eliminado = repo.delete(add)

        assertTrue(eliminado)
    }

    @Test
    fun findAll()= transaction {
        SchemaUtils.create(Usuarios)

        var add = repo.add(usuarioTest)
        var lista = repo.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, add.id)},
            { assertEquals(lista[0].uuid, add.uuid) },
            { assertEquals(lista[0].nombre, add.nombre) },
            { assertEquals(lista[0].apellido, add.apellido) },
            { assertEquals(lista[0].email, add.email) },
            { assertEquals(lista[0].password, add.password) },
            { assertEquals(lista[0].tipo, add.tipo) },
            { assertEquals(lista[0].disponible, add.disponible) }
        )
    }
}