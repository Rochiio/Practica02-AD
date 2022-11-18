package repositories.MaquinaTable

import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.MaquinaRepository
import java.util.*

//class MaquinaRepositoryImpl : MaquinaRepository {
//
//    private var logger = KotlinLogging.logger {}
//    override fun findById(uuid: Int): Maquina? = transaction {
//        logger.debug { "buscando maquina con uuid: $uuid" }
//        Maquina.findById(uuid)
//    }
//
//    override fun findByUUID(uuid: UUID): Maquina? = transaction {
//        logger.debug { "buscando maquina con uuid: $uuid" }
//        Maquina.find { Maquinas.uuid eq uuid }.firstOrNull()
//    }
//
//    override fun add(item: Maquina): Maquina? = transaction {
//        logger.debug { "añadiendo maquina: $item" }
//        val existe = Maquina.find { Maquinas.uuid eq item.uuid }.firstOrNull()
//        existe?.let {
//            update(item, existe)
//        } ?: run {
//            Maquina.new {
//                uuid = item.uuid
//                modelo = item.modelo
//                fechaAdquisicion = item.fechaAdquisicion
//            }
//        }
//
//    }
//
//    override fun update(item: Maquina, updateItem: Maquina): Maquina = transaction {
//        logger.debug { "actualizando maquina" }
//        item.apply {
//            uuid = updateItem.uuid
//            modelo = updateItem.modelo
//            fechaAdquisicion = updateItem.fechaAdquisicion
//        }
//    }
//
//    override fun delete(item: Maquina): Boolean = transaction{
//        logger.debug { "eliminando maquina" }
//        val existe = Maquina.find{Maquinas.uuid eq item.uuid}.firstOrNull() ?: return@transaction false
//        existe.disponible = false
//        return@transaction true
//    }
//
//    override fun findAll(): List<Maquina> = transaction{
//        logger.debug { "buscando todas las maquinas" }
//        return@transaction Maquina.all().map { it }.toList()
//    }

//}