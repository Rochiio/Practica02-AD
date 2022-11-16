package repositories.usuarios

import db.DataBaseManager
import entities.usuarios.TrabajadorTable
import models.Turno
import models.maquinas.Maquina
import models.usuarios.Trabajador
import models.usuarios.Usuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

import java.time.LocalDate
import java.util.*

internal class TrabajadorRepositoryImplTest {
    private var repo = TrabajadorRepositoryImpl()
    private var modeloTest = Trabajador(Usuario(UUID.randomUUID(),"Prueba","Test","prueba@prueba.com","1234",true)
        ,false, Turno(null,"10:20","14:30", Maquina("modelo", LocalDate.now(),true))
    )

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
                    SchemaUtils.create(TrabajadorTable)
//                    usuarioTest = Usuario.new {
//                    nombre = "Prueba"
//                    apellido = "test"
//                    email = "email@email.com"
//                    password = "esryu5ert454"
                }
            }



    @Test
    fun findById() =transaction{
        SchemaUtils.create(TrabajadorTable)

        var add = repo.add(modeloTest)
        var encontrado = repo.findById(add.id.value)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, add.id)},
            { assertEquals(encontrado?.usuario?.uuid, add.usuario.uuid) },
            { assertEquals(encontrado?.usuario?.nombre, add.usuario.nombre) },
            { assertEquals(encontrado?.usuario?.apellido, add.usuario.apellido) },
            { assertEquals(encontrado?.usuario?.email, add.usuario.email) },
            { assertEquals(encontrado?.usuario?.password, add.usuario.password) },
            { assertEquals(encontrado?.administrador, add.administrador) },
            { assertEquals(encontrado?.turno?.id, add.turno.id) },
            { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
            { assertEquals(encontrado?.turno?.comienzoTurno, add.turno.comienzoTurno) },
            { assertEquals(encontrado?.turno?.finTurno, add.turno.finTurno) },
            { assertEquals(encontrado?.turno?.maquina?.modelo, add.turno.maquina.modelo)},
            { assertEquals(encontrado?.turno?.maquina?.fechaAdquisicion, add.turno.maquina.fechaAdquisicion)},
            { assertEquals(encontrado?.turno?.maquina?.disponible, add.turno.maquina.disponible)},
        )
    }

    @Test
    fun findByUUID()= transaction {
        SchemaUtils.create(TrabajadorTable)

        var add = repo.add(modeloTest)
        var encontrado = repo.findByUUID(add.usuario.uuid)

        assertAll(
            { assertNotNull(encontrado) },
            { assertEquals(encontrado?.id, add.id)},
            { assertEquals(encontrado?.usuario?.uuid, add.usuario.uuid) },
            { assertEquals(encontrado?.usuario?.nombre, add.usuario.nombre) },
            { assertEquals(encontrado?.usuario?.apellido, add.usuario.apellido) },
            { assertEquals(encontrado?.usuario?.email, add.usuario.email) },
            { assertEquals(encontrado?.usuario?.password, add.usuario.password) },
            { assertEquals(encontrado?.administrador, add.administrador) },
            { assertEquals(encontrado?.turno?.id, add.turno.id) },
            { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
            { assertEquals(encontrado?.turno?.comienzoTurno, add.turno.comienzoTurno) },
            { assertEquals(encontrado?.turno?.finTurno, add.turno.finTurno) },
            { assertEquals(encontrado?.turno?.maquina?.modelo, add.turno.maquina.modelo)},
            { assertEquals(encontrado?.turno?.maquina?.fechaAdquisicion, add.turno.maquina.fechaAdquisicion)},
            { assertEquals(encontrado?.turno?.maquina?.disponible, add.turno.maquina.disponible)},
        )
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