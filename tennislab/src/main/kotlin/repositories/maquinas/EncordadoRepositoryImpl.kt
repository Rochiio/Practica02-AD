package repositories.maquinas

import entities.EncordadorDAO
import entities.EncordadorTable
import entities.enums.TipoMaquina
import exception.log
import mappers.fromEncordadorDaoToEncordador
import models.maquinas.Encordador
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación repositorio de maquinas de encordar.
 */

class EncordadoRepositoryImpl(private var encordadorDAO: IntEntityClass<EncordadorDAO>) : EncordadoRepository {
    private var logger = KotlinLogging.logger {}
    override fun findById(id: Int): Encordador? {
        logger.debug { "buscando encordador por id" }
        return encordadorDAO.findById(id)?.fromEncordadorDaoToEncordador()
    }

    override fun findByUUID(uuid: UUID): Encordador? = transaction {
        logger.debug { "buscando encordador por uuid" }
        encordadorDAO.find { EncordadorTable.uuid eq uuid }.firstOrNull()?.fromEncordadorDaoToEncordador()
    }

    override fun save(item: Encordador): Encordador = transaction{
        logger.debug { "Save trabajador" }
        var result = encordadorDAO.find { EncordadorTable.uuid eq item.uuid!! }.firstOrNull()
        result?.let {
            update(item, result)
        }?: run{
            add(item)
        }
    }

    override fun add(item: Encordador): Encordador = transaction {
        logger.debug { "añadiendo encordador" }
        encordadorDAO.new {
            modelo = item.modelo
            marca = item.marca
            fechaAdquisicion = item.fechaAdquisicion
            disponible = item.disponible
            automatico = item.automatico == TipoMaquina.AUTOMATICA
            tensionMaxima = item.tensionMaxima
            tensionMinima = item.tensionMinima
        }.fromEncordadorDaoToEncordador()
    }

    fun update(item: Encordador, updateItem: EncordadorDAO): Encordador = transaction {
        logger.debug { "acualizando encordador" }
        updateItem.apply {
            modelo = item.modelo
            marca = item.marca
            fechaAdquisicion = item.fechaAdquisicion
            disponible = item.disponible
            automatico = item.automatico == TipoMaquina.AUTOMATICA
            tensionMaxima = item.tensionMaxima
            tensionMinima = item.tensionMinima
        }.fromEncordadorDaoToEncordador()
    }

    override fun delete(item: Encordador): Boolean = transaction {
        val existe = item.uuid?.let {
            encordadorDAO.find { EncordadorTable.uuid eq item.uuid!! }
        } ?: return@transaction false
        logger.debug { "eliminando encordador" }
        existe.first().delete()
        return@transaction true
    }

    override fun findAll(): List<Encordador> = transaction {
        logger.debug { "recuperando todos los encordadores" }
        encordadorDAO.all().map { it.fromEncordadorDaoToEncordador() }
    }

    override fun deleteAll(): Boolean = transaction {
        logger.debug { "eliminando todos los encordadores" }
        var num = EncordadorTable.deleteAll()
        return@transaction num > 0
    }
}