package repositories.usuarios

import entities.usuarios.UsuarioDAO
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.usuarios.Trabajador
import mu.KotlinLogging
import mappers.fromTrabajadorDaoToTrabajador
import mappers.fromUsuarioDaoToUsuario
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación del repositorio de trabajadores.
 */
class TrabajadorRepositoryImpl(
    private var trabajadorDAO: UUIDEntityClass<TrabajadorDAO>,
    private var usuarioDAO: UUIDEntityClass<UsuarioDAO>
) : TrabajadorRepository {

    private var logger = KotlinLogging.logger {}


    /**
     * Buscar un trabajador por su id.
     * @param id id por el que buscar al trabajador.
     * @return trabajador o no dependiendo de si lo han encontrado.
     */
    override fun findById(id: UUID): Trabajador? =transaction{
        logger.debug { "buscando trabajador con uuid: $id" }
        trabajadorDAO.findById(id)?.fromTrabajadorDaoToTrabajador()
    }


    /**
     * Buscar un trabajador por su uuid.
     * @param uuid por el que buscar al trabajador.
     * @return trabajador o no dependiendo de si lo han encontrado.
     */
    override fun findByUUID(uuid: UUID): Trabajador? =transaction{
        logger.debug { "buscando trabajador con uuid: $uuid" }
        trabajadorDAO.findById(uuid)?.fromTrabajadorDaoToTrabajador()
    }


    /**
     * Salvar a un trabajador.
     * @param item trabajador a salvar.
     * @return trabajador
     */
    override fun save(item: Trabajador): Trabajador =transaction{
        logger.debug { "Save trabajador" }
        item.uuid?.let {
            trabajadorDAO.findById(it)?.let {
                update(item, it)
            }
        } ?: run {
            add(item)
        }
    }


    /**
     * Añadir un trabajador.
     * @param item trabajador a añadir.
     * @return trabajador
     */
    override fun add(item: Trabajador): Trabajador =transaction{
        logger.debug { "Add trabajador"}
        trabajadorDAO.new {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
            administrador = item.administrador
        }.fromTrabajadorDaoToTrabajador()
    }


    /**
     * Actualizar un trabajador.
     * @param item trabajador modelo actualizado.
     * @param updateItem trabajador DAO a actualizar en la base de datos.
     * @return trabajador
     */
    fun update(item: Trabajador, updateItem: TrabajadorDAO): Trabajador =transaction{
        logger.debug { "actualizando trabajador" }
        updateItem.apply {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
            administrador = item.administrador
        }.fromTrabajadorDaoToTrabajador()
    }


    /**
     * Eliminar a  un trabajador.
     * @param item trabajador a eliminar.
     * @return boolean dependiendo de si ha sido eliminado o no.
     */
    override fun delete(item: Trabajador): Boolean =transaction{
        val existe = item.uuid?.let { trabajadorDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando trabajador: $item" }
        existe.delete()
        return@transaction true
    }


    /**
     * Buscar a todos los trabajadores que hay.
     * @return lista de trabajadores.
     */
    override fun findAll(): List<Trabajador> =transaction{
        logger.debug { "Buscando todos los trabajadores"}
        trabajadorDAO.all().map { it.fromTrabajadorDaoToTrabajador()}.toList()
    }


    /**
     * Eliminar todos los trabajadores que existen.
     * @return boolean dependiendo de si ha eliminado o no trabajadores.
     */
    override fun deleteAll(): Boolean =transaction{
        logger.debug { "Eliminando todos los trabajadores"}
        var num = TrabajadorTable.deleteAll()
        return@transaction num>0
    }


}