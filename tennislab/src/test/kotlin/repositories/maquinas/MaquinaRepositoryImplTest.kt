package repositories.maquinas

import models.Maquina
import models.Maquinas
import org.h2.engine.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.javatime.dateLiteral
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import java.util.*

internal class MaquinaRepositoryImplTest {

    private lateinit var maquinaTest: Maquina
    private var repository = MaquinaRepositoryImpl()

    @BeforeEach
    fun setUp() {
        org.jetbrains.exposed.sql.Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(Maquinas)
            maquinaTest = Maquina.new {
                uuid = UUID.fromString("b74b4c18-9108-4102-83f0-8019f6861ba3")
                modelo = "MaquinaPrueba"
                fechaAdquisicion = LocalDate.parse("2000-01-01")
                disponible = true
            }
        }
    }

    @Test
    fun findById() = transaction {

        SchemaUtils.create(Maquinas)

        var add = repository.add(maquinaTest)
        var found = add?.id?.let { repository.findById(it.value) }

        assertNotNull(found)
    }

    @Test
    fun findByUUID() {
    }

    @Test
    fun add() {
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun findAll() {
    }
}