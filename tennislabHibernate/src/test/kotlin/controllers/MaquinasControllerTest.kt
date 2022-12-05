package controllers

import entities.enums.TipoMaquina
import exception.MaquinaError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import models.maquinas.Encordador
import models.maquinas.Personalizador
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repositories.maquinas.EncordadorRepositoryImpl
import repositories.maquinas.PersonalizadorRepositoryImpl
import java.time.LocalDate
import java.util.*

internal class MaquinasControllerTest {
    @MockK
    private lateinit var encordado: EncordadorRepositoryImpl
    @MockK
    private lateinit var personalizado: PersonalizadorRepositoryImpl
    @InjectMockKs
    private lateinit var controller: MaquinasController

    private var encordadora = Encordador(UUID.fromString("27c1af75-3bd1-4a71-be4c-a498ab5e7d85"),"marca","test",
        LocalDate.now(),true, TipoMaquina.AUTOMATICA,10,3)
    private var personalizadora = Personalizador(UUID.fromString("27c1af75-3bd1-4a71-be4c-a498ab5e7d85"),"marca","modelo",
    LocalDate.now(), disponible = true, maniobrabilidad = true, balance = false, rigidez = false)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addEncordadora() {
        every { encordado.findById(encordadora.uuid) } returns null
        every { encordado.save(encordadora) } returns encordadora

        var add = controller.addEncordadora(encordadora)

        assertAll(
            { assertEquals(add.uuid, encordadora.uuid) },
            { assertEquals(add.marca, encordadora.marca) },
            { assertEquals(add.modelo, encordadora.modelo) },
            { assertEquals(add.fechaAdquisicion, encordadora.fechaAdquisicion) },
            { assertEquals(add.disponible, encordadora.disponible) },
            { assertEquals(add.automatico, encordadora.automatico) },
            { assertEquals(add.tensionMaxima, encordadora.tensionMaxima) },
            { assertEquals(add.tensionMinima, encordadora.tensionMinima) }
        )

        verify(exactly=1){ encordado.save(encordadora)}
        verify(exactly=1){ encordado.findById(encordadora.uuid) }
    }

    @Test
    fun addEncordadoraErrorYaExiste(){
        every { encordado.findById(encordadora.uuid)} returns encordadora
        var exception = assertThrows(MaquinaError::class.java){
            controller.addEncordadora(encordadora)
        }

        assertEquals("Ya existe una encordadora con este UUID", exception.item)

        verify(exactly=1){encordado.findById(encordadora.uuid)}
    }

    @Test
    fun getEncordadoraByUUID() {
        every { encordado.findById(encordadora.uuid)} returns encordadora

        var find = controller.getEncordadoraByUUID(encordadora.uuid)

        assertAll(
            { assertEquals(find.uuid, encordadora.uuid) },
            { assertEquals(find.marca, encordadora.marca) },
            { assertEquals(find.modelo, encordadora.modelo) },
            { assertEquals(find.fechaAdquisicion, encordadora.fechaAdquisicion) },
            { assertEquals(find.disponible, encordadora.disponible) },
            { assertEquals(find.automatico, encordadora.automatico) },
            { assertEquals(find.tensionMaxima, encordadora.tensionMaxima) },
            { assertEquals(find.tensionMinima, encordadora.tensionMinima) }
        )

        verify(exactly=1){ encordado.findById(encordadora.uuid) }
    }

    @Test
    fun getEncordadoraByUUIDErrorNoExiste(){
        every {encordado.findById(encordadora.uuid)} returns null
        var exception = assertThrows(MaquinaError::class.java){
            controller.getEncordadoraByUUID(encordadora.uuid)
        }

        assertEquals("No existe la encordadora con este UUID", exception.item)

        verify(exactly=1){encordado.findById(encordadora.uuid)}
    }


    @Test
    fun getAllEncordadoras() {
        every { encordado.findAll() } returns listOf(encordadora)

        var lista = controller.getAllEncordadoras()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, encordadora.uuid) },
            { assertEquals(lista[0].marca, encordadora.marca) },
            { assertEquals(lista[0].modelo, encordadora.modelo) },
            { assertEquals(lista[0].fechaAdquisicion, encordadora.fechaAdquisicion) },
            { assertEquals(lista[0].disponible, encordadora.disponible) },
            { assertEquals(lista[0].automatico, encordadora.automatico) },
            { assertEquals(lista[0].tensionMaxima, encordadora.tensionMaxima) },
            { assertEquals(lista[0].tensionMinima, encordadora.tensionMinima) }
        )

        verify(exactly=1){ encordado.findAll() }
    }

    @Test
    fun deleteEncordadora() {
        every { encordado.delete(encordadora) } returns true

        var eliminado = controller.deleteEncordadora(encordadora)

        assertTrue(eliminado)

        verify(exactly=1){ encordado.delete(encordadora) }
    }

    @Test
    fun deleteEncordadoraErrorNoEliminado() {
        every { encordado.delete(encordadora) } returns false
        var exception = assertThrows(MaquinaError::class.java){
            controller.deleteEncordadora(encordadora)
        }

        assertEquals("Problemas al eliminar la encordadora",exception.item)

        verify(exactly=1){encordado.delete(encordadora)}
    }

    @Test
    fun addPersonalizadora() {
        every { personalizado.findById(personalizadora.uuid) } returns null
        every { personalizado.save(personalizadora) } returns personalizadora

        var add = controller.addPersonalizadora(personalizadora)

        assertAll(
            { assertEquals(add.uuid, personalizadora.uuid) },
            { assertEquals(add.marca, personalizadora.marca) },
            { assertEquals(add.modelo, personalizadora.modelo) },
            { assertEquals(add.fechaAdquisicion, personalizadora.fechaAdquisicion) },
            { assertEquals(add.disponible, personalizadora.disponible) },
            { assertEquals(add.maniobrabilidad, personalizadora.maniobrabilidad) },
            { assertEquals(add.balance, personalizadora.balance) } ,
            { assertEquals(add.rigidez, personalizadora.rigidez) }
        )

        verify(exactly=1){ personalizado.save(personalizadora)}
        verify(exactly=1){ personalizado.findById(personalizadora.uuid) }
    }

    @Test
    fun addPersonalizadoraErrorYaExiste(){
        every { personalizado.findById(personalizadora.uuid) } returns personalizadora
        var exception = assertThrows(MaquinaError::class.java){
            controller.addPersonalizadora(personalizadora)
        }

        assertEquals("Ya existe una personalizadora con este UUID", exception.item)

        verify(exactly=1){personalizado.findById(personalizadora.uuid)}
    }

    @Test
    fun getPersonalizadoraByUUID() {
        every { personalizado.findById(personalizadora.uuid)} returns personalizadora

        var find = controller.getPersonalizadoraByUUID(personalizadora.uuid)

        assertAll(
            { assertEquals(find.uuid, personalizadora.uuid) },
            { assertEquals(find.marca, personalizadora.marca) },
            { assertEquals(find.modelo, personalizadora.modelo) },
            { assertEquals(find.fechaAdquisicion, personalizadora.fechaAdquisicion) },
            { assertEquals(find.disponible, personalizadora.disponible) },
            { assertEquals(find.maniobrabilidad, personalizadora.maniobrabilidad) },
            { assertEquals(find.balance, personalizadora.balance) } ,
            { assertEquals(find.rigidez, personalizadora.rigidez) }
        )

        verify(exactly=1){ personalizado.findById(personalizadora.uuid) }
    }

    @Test
    fun getPersonalizadoraByUUIDErrorNoExiste(){
        every{personalizado.findById(personalizadora.uuid)} returns null
        var exception = assertThrows(MaquinaError::class.java){
            controller.getPersonalizadoraByUUID(personalizadora.uuid)
        }
        assertEquals("No existe la personalizadora con este UUID",exception.item)
        verify(exactly=1){personalizado.findById(personalizadora.uuid)}
    }


    @Test
    fun getAllPersonalizadoras() {
        every { personalizado.findAll() } returns listOf(personalizadora)

        var lista= controller.getAllPersonalizadoras()

        assertAll(
            { assertEquals(lista[0].uuid, personalizadora.uuid) },
            { assertEquals(lista[0].marca, personalizadora.marca) },
            { assertEquals(lista[0].modelo, personalizadora.modelo) },
            { assertEquals(lista[0].fechaAdquisicion, personalizadora.fechaAdquisicion) },
            { assertEquals(lista[0].disponible, personalizadora.disponible) },
            { assertEquals(lista[0].maniobrabilidad, personalizadora.maniobrabilidad) },
            { assertEquals(lista[0].balance, personalizadora.balance) } ,
            { assertEquals(lista[0].rigidez, personalizadora.rigidez) }
        )

        verify(exactly=1){ personalizado.findAll() }
    }

    @Test
    fun deletePersonalizadora() {
        every { personalizado.delete(personalizadora) } returns true

        var eliminado = controller.deletePersonalizadora(personalizadora)

        assertTrue(eliminado)

        verify(exactly=1){ personalizado.delete(personalizadora) }
    }

    @Test
    fun deletePersonalizadoraErrorNoEliminado(){
        every { personalizado.delete(personalizadora) } returns false
        var exception = assertThrows(MaquinaError::class.java){
            controller.deletePersonalizadora(personalizadora)
        }

        assertEquals("Problemas al eliminar la personalizadora",exception.item)
        verify(exactly=1){personalizado.delete(personalizadora)}
    }
}