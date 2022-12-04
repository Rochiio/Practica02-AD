package repositories.maquinas

import models.maquinas.Encordador
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class EncordadorRepositoryImplTest {
    private var repository = EncordadorRepositoryImpl()
    private var test = Encordador(modelo="modelo", marca = "marca", fechaAdquisicion = LocalDate.now(), disponible = true,
        automatico = false, tensionMaxima = 10, tensionMinima = 5)

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
            { assertEquals(encontrado?.automatico, guardado.automatico) },
            { assertEquals(encontrado?.tensionMaxima, guardado.tensionMaxima) },
            { assertEquals(encontrado?.tensionMinima, guardado.tensionMinima) }

        )
    }

    @Test
    fun save() {
        var guardado = repository.save(test)

        assertAll(
            { assertNotNull(guardado) },
            { assertEquals(guardado.marca, test.marca) },
            { assertEquals(guardado.modelo, test.modelo) },
            { assertEquals(guardado.fechaAdquisicion, test.fechaAdquisicion) },
            { assertEquals(guardado.disponible, test.disponible) },
            { assertEquals(guardado.automatico, test.automatico) },
            { assertEquals(guardado.tensionMaxima, test.tensionMaxima) },
            { assertEquals(guardado.tensionMinima, test.tensionMinima) }

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
            { assertEquals(lista[0].uuid, guardado.uuid)},
            { assertEquals(lista[0].marca, guardado.marca) },
            { assertEquals(lista[0].modelo, guardado.modelo) },
            { assertEquals(lista[0].fechaAdquisicion, guardado.fechaAdquisicion) },
            { assertEquals(lista[0].disponible, guardado.disponible) },
            { assertEquals(lista[0].automatico, guardado.automatico) },
            { assertEquals(lista[0].tensionMaxima, guardado.tensionMaxima) },
            { assertEquals(lista[0].tensionMinima, guardado.tensionMinima) }

        )
    }

    @Test
    fun deleteAll() {
        repository.save(test)
        var result = repository.deleteAll()
        assertTrue(result)
    }
}