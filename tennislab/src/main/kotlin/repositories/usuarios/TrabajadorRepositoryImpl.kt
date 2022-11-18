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


    override fun findById(id: UUID): Trabajador? =transaction{
        logger.debug { "buscando trabajador con uuid: $id" }
        trabajadorDAO.findById(id)?.fromTrabajadorDaoToTrabajador()
    }

    override fun findByUUID(uuid: UUID): Trabajador? =transaction{
        logger.debug { "buscando trabajador con uuid: $uuid" }
        trabajadorDAO.findById(uuid)?.fromTrabajadorDaoToTrabajador()
    }

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

    override fun add(item: Trabajador): Trabajador =transaction{
        logger.debug { "Add trabajador"}
        trabajadorDAO.new {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
            administrador = item.administrador
        }.fromTrabajadorDaoToTrabajador()
    }

    fun update(item: Trabajador, updateItem: TrabajadorDAO): Trabajador =transaction{
        logger.debug { "actualizando trabajador" }
        updateItem.apply {
            usuario = usuarioDAO.findById(item.usuario.uuid!!)!!
            administrador = item.administrador
        }.fromTrabajadorDaoToTrabajador()
    }

    override fun delete(item: Trabajador): Boolean =transaction{
        val existe = item.uuid?.let { trabajadorDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando trabajador: $item" }
        existe.delete()
        return@transaction true
    }

    override fun findAll(): List<Trabajador> =transaction{
        logger.debug { "Buscando todos los trabajadores"}
        trabajadorDAO.all().map { it.fromTrabajadorDaoToTrabajador()}.toList()
    }


    override fun deleteAll(): Boolean =transaction{
        logger.debug { "Eliminando todos los trabajadores"}
        var num = TrabajadorTable.deleteAll()
        return@transaction num>0
    }


}