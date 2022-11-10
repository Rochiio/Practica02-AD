package repositories.maquinas

import models.*
import mu.KotlinLogging
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.wrapAsExpression
import java.time.LocalDate
import java.util.*

/**
 * Implementación repositorio de maquinas de encordar.
 */

class EncordadoRepositoryImpl: EncordadoRepository {
    private var logger = KotlinLogging.logger {}

    override fun findById(id: Int): Encordador? =transaction{
        logger.debug { "Buscando máquina de encordar por id" }
        Encordador.findById(id)
    }

    override fun findByUUID(uuid: UUID): Encordador?  =transaction{
        logger.debug { "Buscando máquina de encordar por UUID de la maquina"}
        Encordador.find { Encordadores.maquina eq (Maquinas.uuid eq uuid) }.firstOrNull()
    }

    override fun add(item: Encordador): Encordador = transaction{
        logger.debug { "Añadiendo maquina de encordar" }
        val existe = Encordador.find { Encordadores.maquina eq (Maquinas.uuid eq item.maquina.uuid) }.firstOrNull()
        existe?.let {
            update(existe, item)
        } ?: run {
            Encordador.new {
                automatico= item.automatico
                tensionMaxima= item.tensionMaxima
                tensionMinima= item.tensionMinima
                maquina = item.maquina
            }
        }
    }

    override fun update(item: Encordador, updateItem: Encordador): Encordador =transaction{
        logger.debug { "Actualizando maquina de encordar" }
        item.apply{
                automatico= updateItem.automatico
                tensionMaxima= updateItem.tensionMaxima
                tensionMinima= updateItem.tensionMinima
                maquina = updateItem.maquina
        }
    }

    override fun delete(item: Encordador): Boolean = transaction{
        logger.debug { "Borrando maquina de encordado" }
        val existe = Encordador.find { Encordadores.maquina eq (Maquinas.uuid eq item.maquina.uuid) }.firstOrNull() ?: return@transaction false
        existe.delete()
        true
    }

    override fun findAll(): List<Encordador> = transaction{
        logger.debug { "Buscando todas las maquinas de encordar" }
        return@transaction Encordador.all().map { it }.toList()
    }
}