package repositories.maquinas


import entities.enums.TipoMaquina
import entities.maquinas.PersonalizadorDAO
import entities.maquinas.PersonalizadorTable
import models.maquinas.Personalizadora
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import java.util.*

internal class PersonalizadoraRepositoryImplTest {

    private var personalizadoraTest: Personalizadora = Personalizadora(
        UUID.randomUUID(), "MarcaPrueba", "ModeloPrueba",
        LocalDate.now(),
        true, true, true, true
    )

    private var repository = PersonalizadoraRepositoryImpl(PersonalizadorDAO)

    @BeforeEach
    fun setUp(){
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(PersonalizadorTable)
            repository.deleteAll()
        }
    }

    @Test
    fun findById() = transaction{
        SchemaUtils.create(PersonalizadorTable)

        var guardado = repository.save(personalizadoraTest)
        var encontrado = repository.findById(1)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.marca, guardado.marca) },
            { assertEquals(encontrado?.modelo, guardado.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertTrue(encontrado?.maniobrabilidad!!) },
            { assertTrue(encontrado?.balance !!) },
            { assertTrue(encontrado?.rigidez!!) }

        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(PersonalizadorTable)

        var guardado = repository.save(personalizadoraTest)
        var encontrado = repository.findByUUID(personalizadoraTest.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.marca, guardado.marca) },
            { assertEquals(encontrado?.modelo, guardado.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertTrue(encontrado?.maniobrabilidad!!) },
            { assertTrue(encontrado?.balance !!) },
            { assertTrue(encontrado?.rigidez!!) }

        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(PersonalizadorTable)

        var guardado = repository.save(personalizadoraTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertEquals(guardado?.uuid, personalizadoraTest.uuid) },
            { assertEquals(guardado?.marca, personalizadoraTest.marca) },
            { assertEquals(guardado?.modelo, personalizadoraTest.modelo) },
            { assertEquals(guardado?.fechaAdquisicion, personalizadoraTest.fechaAdquisicion) },
            { assertEquals(guardado?.disponible, personalizadoraTest.disponible) },
            { assertTrue(guardado?.maniobrabilidad!!) },
            { assertTrue(guardado?.balance !!) },
            { assertTrue(guardado?.rigidez!!) }

        )
    }

    @Test
    fun add() = transaction {
        SchemaUtils.create(PersonalizadorTable)

        val guardado = repository.add(personalizadoraTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertEquals(guardado?.uuid, personalizadoraTest.uuid) },
            { assertEquals(guardado?.marca, personalizadoraTest.marca) },
            { assertEquals(guardado?.modelo, personalizadoraTest.modelo) },
            { assertEquals(guardado?.fechaAdquisicion, personalizadoraTest.fechaAdquisicion) },
            { assertEquals(guardado?.disponible, personalizadoraTest.disponible) },
            { assertTrue(guardado?.maniobrabilidad!!) },
            { assertTrue(guardado?.balance !!) },
            { assertTrue(guardado?.rigidez!!) }

        )
    }

    /* @Test
     fun update(){
         TODO("HACER ESTE TEST >:")
         *//*var updateItem = Personalizadora(
            UUID.randomUUID(), "MarcaUpdate", "ModeloUpdate",
            LocalDate.now(),
            false, TipoMaquina.MANUAL, 2, 3
        )

        var result = repository.update(encordadoTest, updateItem)*//*
    }*/

    @Test
    fun delete() = transaction {
        SchemaUtils.create(PersonalizadorTable)
        var guardado = repository.save(personalizadoraTest)
        var result = repository.delete(guardado)
        assertTrue(result)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(PersonalizadorTable)
        var result = repository.findAll()
        assertTrue(result.isEmpty())

        var guardado = repository.save(personalizadoraTest)
        result = repository.findAll()
        assertEquals(result.size, 1)

    }

    @Test
    fun deleteAll() = transaction{
        SchemaUtils.create(PersonalizadorTable)

        var guardado = repository.save(personalizadoraTest)
        var result = repository.deleteAll()
        assertTrue(result)
    }
}