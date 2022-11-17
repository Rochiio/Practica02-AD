package repositories.usuarios

import entities.TurnoDAO
import entities.usuarios.UsuarioDAO
import entities.usuarios.TrabajadorDAO
import models.usuarios.Trabajador
import mu.KotlinLogging
import mappers.fromTrabajadorDaoToTrabajador
import org.jetbrains.exposed.dao.UUIDEntityClass
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
    override fun findById(id: UUID): Trabajador? {
        logger.debug { "buscando trabajador con id: $id" }
        return trabajadorDAO.findById(id)?.fromTrabajadorDaoToTrabajador()
    }

    override fun findByUUID(uuid: UUID): Trabajador? {
        logger.debug { "buscando trabajador con id: $uuid" }
        return trabajadorDAO.findById(uuid)?.fromTrabajadorDaoToTrabajador()
    }

    override fun save(item: Trabajador): Trabajador {
        println(trabajadorDAO.findById(item.id))
        trabajadorDAO.findById(item.id)?.let {
            update(item, it)
        } ?: run {
            add(item)
        }
        return item
    }

    override fun add(item: Trabajador): Trabajador {
        logger.debug { "añadiendo trabajador: $item" }
        return trabajadorDAO.new(item.id) {
            administrador = item.administrador
            usuario = item.usuario.uuid?.let { usuarioDAO.findById(it) }!!
            turno = TurnoDAO.findById(item.turno.uuid)!!
        }.fromTrabajadorDaoToTrabajador()
    }

    fun update(item: Trabajador, updateItem: TrabajadorDAO): Trabajador {
        logger.debug { "actualizando trabajador" }
        return updateItem.apply {
            usuario = item.usuario.uuid?.let { usuarioDAO.findById(it) }!!
            administrador = item.administrador
            turno = TurnoDAO.findById(item.turno.uuid)!!
        }.fromTrabajadorDaoToTrabajador()

    }

    override fun delete(item: Trabajador): Boolean = transaction {
        val existe = trabajadorDAO.findById(item.id) ?: return@transaction false
        logger.debug { "eliminando trabajador: $item" }
        existe.delete()
        true
    }

    override fun findAll(): List<Trabajador> {
        return trabajadorDAO.all().map { it.fromTrabajadorDaoToTrabajador() }
    }

    override fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }


}