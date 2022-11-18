package repositories.maquinas

import entities.MaquinaDAO
import entities.MaquinaTable
import entities.usuarios.UsuarioDAO
import entities.usuarios.UsuarioTable
import models.maquinas.Maquina
import models.usuarios.Usuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repositories.MaquinaTable.MaquinaRepositoryImpl
import repositories.usuarios.UsuarioRepositoryImpl
import java.time.LocalDate
import java.util.*

internal class MaquinaRepositoryImplTest {

    private var maquinaTest: Maquina = Maquina(UUID.randomUUID(),"814HYF", LocalDate.now(),true)
    private var repository = MaquinaRepositoryImpl(MaquinaDAO)


    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(MaquinaTable)
            repository.deleteAll()
        }
    }


    @Test
    fun findById() = transaction{
        SchemaUtils.create(MaquinaTable)
        var saveItem = repository.save(maquinaTest)
        var encontrado = repository.findById(saveItem.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, saveItem.uuid) },
            { assertEquals(encontrado?.modelo, saveItem.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, saveItem.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, saveItem.disponible) }
        )
    }

    @Test
    fun findByUUID()= transaction {
        SchemaUtils.create(MaquinaTable)
        var saveItem = repository.save(maquinaTest)
        var encontrado = repository.findByUUID(saveItem.uuid!!)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.uuid, saveItem.uuid) },
            { assertEquals(encontrado?.modelo, saveItem.modelo) },
            { assertEquals(encontrado?.fechaAdquisicion, saveItem.fechaAdquisicion) },
            { assertEquals(encontrado?.disponible, saveItem.disponible) }
        )
    }

    @Test
    fun save() = transaction{
        SchemaUtils.create(MaquinaTable)
        var saveItem = repository.save(maquinaTest)

        assertAll(
            { assertEquals(saveItem.modelo, maquinaTest.modelo) },
            { assertEquals(saveItem.fechaAdquisicion, maquinaTest.fechaAdquisicion) },
            { assertEquals(saveItem.disponible, maquinaTest.disponible) }
        )
    }

    @Test
    fun add() = transaction{
        SchemaUtils.create(MaquinaTable)
        var saveItem = repository.add(maquinaTest)

        assertAll(
            { assertEquals(saveItem.modelo, maquinaTest.modelo) },
            { assertEquals(saveItem.fechaAdquisicion, maquinaTest.fechaAdquisicion) },
            { assertEquals(saveItem.disponible, maquinaTest.disponible) }
        )

    }


    @Test
    fun delete() = transaction{
        SchemaUtils.create(MaquinaTable)
        var saveItem = repository.save(maquinaTest)
        var eliminado = repository.delete(saveItem)
        var find = repository.findById(saveItem.uuid!!)

        assertAll(
            { assertTrue (eliminado) },
            { assertEquals(find?.disponible, false)}
        )
    }

    @Test
    fun findAll() = transaction{
        SchemaUtils.create(MaquinaTable)
        var saveItem = repository.save(maquinaTest)
        var lista = repository.findAll()
        assertAll(
            { assertEquals(lista[0].uuid, saveItem.uuid) },
            { assertEquals(lista[0].modelo, saveItem.modelo) },
            { assertEquals(lista[0].fechaAdquisicion, saveItem.fechaAdquisicion) },
            { assertEquals(lista[0].disponible, saveItem.disponible) }
        )

    }


    @Test
    fun deleteAll() = transaction{
        SchemaUtils.create(MaquinaTable)
        repository.save(maquinaTest)
        var result = repository.deleteAll()

        assertTrue(result)
    }
}