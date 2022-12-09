package repositories.maquinas

import models.maquinas.Personalizador
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class PersonalizadorRepositoryImplTest {
    private var repository = PersonalizadorRepositoryImpl()
    private var test = Personalizador(modelo="modelo", marca = "marca", fechaAdquisicion = LocalDate.now(), disponible = true,
        maniobrabilidad = true, balance = false, rigidez = true)

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun findById() {
        var guardado = repository.save(test)
        var encontrado = repository.findById(guardado.uuid)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, guardado.uuid) },
            { assertEquals(encontrado?.marca, guardado.marca) },
            { assertEquals(encontrado?.modelo, guardado.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, guardado.disponible) },
            { assertEquals(encontrado?.maniobrabilidad, guardado.maniobrabilidad) },
            { assertEquals(encontrado?.balance, guardado.balance) },
            { assertEquals(encontrado?.rigidez, guardado.rigidez) }
        )
    }

    @Test
    fun save() {
        var guardado = repository.save(test)
        assertAll(
            { assertEquals(guardado.marca, test.marca) },
            { assertEquals(guardado.modelo, test.modelo) },
            { assertEquals(guardado.fechaAdquisicion, test.fechaAdquisicion) },
            { assertEquals(guardado.disponible, test.disponible) },
            { assertEquals(guardado.maniobrabilidad, test.maniobrabilidad) },
            { assertEquals(guardado.balance, test.balance) },
            { assertEquals(guardado.rigidez, test.rigidez) }
        )
    }

    @Test
    fun delete() {
        var guardado = repository.save(test)
        var result = repository.delete(guardado)
        assertTrue(result)
    }

    @Test
    fun findAll() {
        var guardado = repository.save(test)
        var lista = repository.findAll()
        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, guardado.uuid) },
            { assertEquals(lista[0].marca, test.marca) },
            { assertEquals(lista[0].modelo, test.modelo) },
            { assertEquals(lista[0].fechaAdquisicion, test.fechaAdquisicion) },
            { assertEquals(lista[0].disponible, test.disponible) },
            { assertEquals(lista[0].maniobrabilidad, test.maniobrabilidad) },
            { assertEquals(lista[0].balance, test.balance) },
            { assertEquals(lista[0].rigidez, test.rigidez) }
        )
    }

    @Test
    fun deleteAll() {
        var guardado = repository.save(test)
        var result = repository.deleteAll()
        assertTrue(result)
    }
}