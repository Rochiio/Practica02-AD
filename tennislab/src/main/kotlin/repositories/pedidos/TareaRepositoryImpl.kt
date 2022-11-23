package repositories.pedidos

import entities.EncordadorTable
import entities.pedidos.TareaDAO
import entities.pedidos.TareaTable
import mappers.fromTareaDaoToTarea
import models.pedidos.Tarea
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class TareaRepositoryImpl(private var tareaDao: IntEntityClass<TareaDAO>) : TareaRepository {
    private var logger = KotlinLogging.logger {}
    override fun findById(id: Int): Tarea? = transaction {
        logger.debug { "buscando tarea por id" }
        tareaDao.findById(id)?.fromTareaDaoToTarea()
    }

    override fun findByUUID(uuid: UUID): Tarea? = transaction {
        logger.debug { "buscando tarea por uuid" }
        tareaDao.find { TareaTable.uuid eq uuid }.first().fromTareaDaoToTarea()
    }

    override fun save(item: Tarea): Tarea = transaction {
        logger.debug { "Save trabajador" }
        var result = tareaDao.find { TareaTable.uuid eq item.uuid!! }.firstOrNull()
        result?.let {
            update(item, result)
        } ?: run {
            add(item)
        }
    }

    private fun update(item: Tarea, updateItem: TareaDAO): Tarea = transaction {
        logger.debug { "actualizando item" }
        updateItem.apply {
            uuid = item.uuid!!
            precio = item.precio
            raqueta = item.raqueta
            tipoTarea = item.tipoTarea
        }.fromTareaDaoToTarea()
    }

    override fun add(item: Tarea): Tarea = transaction {
        logger.debug { "actualizando item" }
        tareaDao.new {
            uuid = item.uuid!!
            precio = item.precio
            raqueta = item.raqueta
            tipoTarea = item.tipoTarea
        }.fromTareaDaoToTarea()
    }

    override fun delete(item: Tarea): Boolean = transaction {
        val existe = item.uuid?.let {
            tareaDao.find { TareaTable.uuid eq item.uuid!! }
        } ?: return@transaction false
        logger.debug { "eliminando tarea" }
        existe.first().delete()
        return@transaction true
    }

    override fun findAll(): List<Tarea> = transaction {
        tareaDao.all().map { it.fromTareaDaoToTarea() }
    }

    override fun deleteAll(): Boolean = transaction {
        logger.debug { "eliminando todos las tareas" }
        var num = TareaTable.deleteAll()
        return@transaction num > 0
    }
}