package repositories.usuarios

import entities.TrabajadorTableUnica
import entities.TrabajadorUnicoDAO
import models.usuarios.Trabajador
import models.usuarios.TrabajadorUnico
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.UUID

internal class TrabajadorUnicoRepositoryImplTest {
    private lateinit var trabajadorTest: TrabajadorUnicoDAO
    private lateinit var trabajador: TrabajadorUnico
    private var repo = TrabajadorUnicoRepositoryImpl(TrabajadorUnicoDAO)

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

        trabajador = TrabajadorUnico(
            nombre = "pepe",
            apellido = "papa",
            email = "email",
            password = "1234",
            disponible = true,
            administrador = true,
            uuid = UUID.randomUUID()
        )
        transaction {
            SchemaUtils.create(TrabajadorTableUnica)

            trabajadorTest = TrabajadorUnicoDAO.new {
                uuid = trabajador.uuid!!
                nombre = trabajador.nombre
                apellido = trabajador.apellido
                email = trabajador.email
                password = trabajador.password
                disponible = trabajador.disponible
                administrador = trabajador.administrador
            }
        }
    }

    @Test
    fun findById() {
    }

    @Test
    fun findByUUID() = transaction {
        SchemaUtils.create(TrabajadorTableUnica)
        var save = repo.save(trabajadorTest)
        var result = repo.findByUUID(trabajador.uuid!!)
        println("ENCONTRADO TRABAJADOR: $result")
        assertNotNull(result)
        assertEquals(result?.uuid, trabajadorTest.uuid)
    }

    @Test
    fun save() = transaction {
        SchemaUtils.create(TrabajadorTableUnica)
        var result = repo.save(trabajadorTest)
        assertNotNull(result)
        assertEquals(result.uuid, trabajadorTest.uuid)
    }
}