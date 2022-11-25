package repositories.maquinas

import entities.EncordadorDAO
import entities.EncordadorTable
import entities.enums.TipoMaquina
import entities.usuarios.TrabajadorTable
import models.maquinas.Encordador
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import java.util.UUID

internal class EncordadoRepositoryImplTest {
    private var encordadoTest: Encordador = Encordador(
        UUID.randomUUID(), "MarcaPrueba", "ModeloPrueba",
        LocalDate.now(),
        true, TipoMaquina.AUTOMATICA, 1, 1
    )

    private var repository = EncordadoRepositoryImpl(EncordadorDAO)

    @BeforeEach
    fun setUp(){
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(EncordadorTable)
            repository.deleteAll()
        }
    }

    @Test
    fun findById() = transaction{
        SchemaUtils.create(EncordadorTable)

        var guardado = repository.save(encordadoTest)
        var encontrado = repository.findById(1)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.marca, guardado.marca) },
            { assertEquals(encontrado?.modelo, guardado.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertEquals(encontrado?.automatico, guardado.automatico) },
            { assertEquals(encontrado?.tensionMaxima, guardado.tensionMaxima) },
            { assertEquals(encontrado?.tensionMinima, guardado.tensionMinima) }

        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(EncordadorTable)

        var guardado = repository.save(encordadoTest)
        var encontrado = repository.findByUUID(encordadoTest.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.marca, guardado.marca) },
            { assertEquals(encontrado?.modelo, guardado.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertEquals(encontrado?.automatico, guardado.automatico) },
            { assertEquals(encontrado?.tensionMaxima, guardado.tensionMaxima) },
            { assertEquals(encontrado?.tensionMinima, guardado.tensionMinima) }

        )
    }

    @Test
    fun save() = transaction{
    SchemaUtils.create(EncordadorTable)

        var guardado = repository.save(encordadoTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertEquals(guardado.uuid, encordadoTest.uuid) },
            { assertEquals(guardado.marca, encordadoTest.marca) },
            { assertEquals(guardado.modelo, encordadoTest.modelo) },
            { assertEquals(guardado.fechaAdquisicion, encordadoTest.fechaAdquisicion) },
            { assertEquals(guardado.disponible, encordadoTest.disponible) },
            { assertEquals(guardado.automatico, encordadoTest.automatico) },
            { assertEquals(guardado.tensionMaxima, encordadoTest.tensionMaxima) },
            { assertEquals(guardado.tensionMinima, encordadoTest.tensionMinima) }

        )
    }

    @Test
    fun add() = transaction {
        SchemaUtils.create(EncordadorTable)

        val guardado = repository.add(encordadoTest)

        assertAll(
            { assertNotNull(guardado) },
            { assertEquals(guardado.uuid, guardado.uuid) },
            { assertEquals(guardado.marca, guardado.marca) },
            { assertEquals(guardado.modelo, guardado.modelo) },
            { assertEquals(guardado.fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(guardado.disponible, guardado.disponible) },
            { assertEquals(guardado.automatico, guardado.automatico) },
            { assertEquals(guardado.tensionMaxima, guardado.tensionMaxima) },
            { assertEquals(guardado.tensionMinima, guardado.tensionMinima) }

        )
    }

   /* @Test
    fun update(){
        TODO("HACER ESTE TEST >:")
        *//*var updateItem = Encordador(
            UUID.randomUUID(), "MarcaUpdate", "ModeloUpdate",
            LocalDate.now(),
            false, TipoMaquina.MANUAL, 2, 3
        )

        var result = repository.update(encordadoTest, updateItem)*//*
    }*/

    @Test
    fun delete() = transaction {
        SchemaUtils.create(EncordadorTable)
        var guardado = repository.save(encordadoTest)
        var result = repository.delete(guardado)
        assertTrue(result)
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(EncordadorTable)
        var result = repository.findAll()
        assertTrue(result.isEmpty())

        var guardado = repository.save(encordadoTest)
        result = repository.findAll()
        assertEquals(result.size, 1)

    }

    @Test
    fun deleteAll() = transaction{
        SchemaUtils.create(EncordadorTable)

        var guardado = repository.save(encordadoTest)
        var result = repository.deleteAll()
        assertTrue(result)
    }
}