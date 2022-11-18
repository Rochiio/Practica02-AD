package repositories.usuarios

import entities.usuarios.UsuarioDAO
import entities.usuarios.TrabajadorTable
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.usuarios.Usuario

import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.util.*

internal class TrabajadorRepositoryImplTestUnt {

    @MockK
    private lateinit var usuarioDAO: UUIDEntityClass<UsuarioDAO>

    @InjectMockKs
    lateinit var repo: TrabajadorRepositoryImpl

    private var usuarioTest = Usuario(UUID.randomUUID(), "Prueba", "Test", "prueba@prueba.com", "1234", true)
//
//    private var modeloTest = Trabajador(
//        uuid = UUID.randomUUID(),
//        usuario = usuarioTest,
//        administrador = true,
//        turno = Turno(UUID.randomUUID(), "13:00", "18:00", Maquina(UUID.randomUUID(), "modelo1", LocalDate.now(), true))
//    )

    private lateinit var daoItem: UsuarioDAO

    init {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(TrabajadorTable)
        }
    }


    @Test
    fun findById() = transaction {
        SchemaUtils.create(TrabajadorTable)
    }


    /* @Test
    fun findById() = transaction {
        every { usuarioDAO.findById(modeloTest.uuid) } returns daoItem

        val res = repo.findById(modeloTest.uuid)
        assert(res == modeloTest)
>>>>>>> 6bf9ce7639f84cffb5cd70e636c5af14c143252a

        var add = repo.add(modeloTest)
        var encontrado = repo.findById(add.uuid.value)

        assertAll(
            { assertNotNull(encontrado) },
<<<<<<< HEAD
            { assertEquals(encontrado?.uuid, add.uuid)},
            { assertEquals(encontrado?.usuario?.uuid, add.usuario.uuid) },
=======
            { assertEquals(encontrado?.uuid, add.uuid) },
            { assertEquals(encontrado?.usuario?.uuid, add.usuario.uuid) },
>>>>>>> 6bf9ce7639f84cffb5cd70e636c5af14c143252a
            { assertEquals(encontrado?.usuario?.nombre, add.usuario.nombre) },
            { assertEquals(encontrado?.usuario?.apellido, add.usuario.apellido) },
            { assertEquals(encontrado?.usuario?.email, add.usuario.email) },
            { assertEquals(encontrado?.usuario?.password, add.usuario.password) },
            { assertEquals(encontrado?.administrador, add.administrador) },
            { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
<<<<<<< HEAD
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
            { assertEquals(encontrado?.uuid, add.uuid)},
            { assertEquals(encontrado?.usuario?.uuid, add.usuario.uuid) },
            { assertEquals(encontrado?.usuario?.nombre, add.usuario.nombre) },
            { assertEquals(encontrado?.usuario?.apellido, add.usuario.apellido) },
            { assertEquals(encontrado?.usuario?.email, add.usuario.email) },
            { assertEquals(encontrado?.usuario?.password, add.usuario.password) },
            { assertEquals(encontrado?.administrador, add.administrador) },
            { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
            { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
            { assertEquals(encontrado?.turno?.comienzoTurno, add.turno.comienzoTurno) },
            { assertEquals(encontrado?.turno?.finTurno, add.turno.finTurno) },
            { assertEquals(encontrado?.turno?.maquina?.modelo, add.turno.maquina.modelo)},
            { assertEquals(encontrado?.turno?.maquina?.fechaAdquisicion, add.turno.maquina.fechaAdquisicion)},
            { assertEquals(encontrado?.turno?.maquina?.disponible, add.turno.maquina.disponible)},
        )
=======
            { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
            { assertEquals(encontrado?.turno?.comienzoTurno, add.turno.comienzoTurno) },
            { assertEquals(encontrado?.turno?.finTurno, add.turno.finTurno) },
            { assertEquals(encontrado?.turno?.maquina?.modelo, add.turno.maquina.modelo) },
            { assertEquals(encontrado?.turno?.maquina?.fechaAdquisicion, add.turno.maquina.fechaAdquisicion) },
            { assertEquals(encontrado?.turno?.maquina?.disponible, add.turno.maquina.disponible) },
        )
    }*/

    @Test
    fun findByUUID() {

//        transaction {
//            SchemaUtils.create(TrabajadorTable, UsuarioTable)
//            UsuarioDAO.new { usuarioTest }
//        }
//        transaction {
//            println("///////////////////////////////////////")
//            println(UsuarioDAO.findById(usuarioTest.uuid))
//            println("///////////////////////////////////////")
//        }
//        transaction {
//            var add = repo.add(modeloTest)
//
//
//            println(add)
        /*assertAll(
                { assertNotNull(encontrado) },
                { assertEquals(encontrado?.uuid, add.uuid) },
                { assertEquals(encontrado?.usuario?.uuid, add.usuario.uuid) },
                { assertEquals(encontrado?.usuario?.nombre, add.usuario.nombre) },
                { assertEquals(encontrado?.usuario?.apellido, add.usuario.apellido) },
                { assertEquals(encontrado?.usuario?.email, add.usuario.email) },
                { assertEquals(encontrado?.usuario?.password, add.usuario.password) },
                { assertEquals(encontrado?.administrador, add.administrador) },
                { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
                { assertEquals(encontrado?.turno?.uuid, add.turno.uuid) },
                { assertEquals(encontrado?.turno?.comienzoTurno, add.turno.comienzoTurno) },
                { assertEquals(encontrado?.turno?.finTurno, add.turno.finTurno) },
                { assertEquals(encontrado?.turno?.maquina?.modelo, add.turno.maquina.modelo) },
                { assertEquals(encontrado?.turno?.maquina?.fechaAdquisicion, add.turno.maquina.fechaAdquisicion) },
                { assertEquals(encontrado?.turno?.maquina?.disponible, add.turno.maquina.disponible) },
            )*/

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
