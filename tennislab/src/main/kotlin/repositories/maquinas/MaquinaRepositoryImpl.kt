package repositories.MaquinaTable

import entities.MaquinaDAO
import entities.MaquinaTable
import entities.usuarios.UsuarioDAO
import mappers.fromMaquinaDaoToMaquina
import mappers.fromUsuarioDaoToUsuario
import models.maquinas.Maquina
import models.usuarios.Usuario
import mu.KotlinLogging
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.MaquinaRepository
import java.util.*

class MaquinaRepositoryImpl(
    private var maquinaDAO: UUIDEntityClass<MaquinaDAO>
) : MaquinaRepository {

    private var logger = KotlinLogging.logger {}
    override fun findById(id: UUID): Maquina? = transaction {
        logger.debug { "buscando maquina con uuid: $id" }
        maquinaDAO.findById(id)?.fromMaquinaDaoToMaquina()
    }

    override fun findByUUID(uuid: UUID): Maquina? = transaction {
        logger.debug { "buscando maquina con uuid: $uuid" }
        maquinaDAO.findById(uuid)?.fromMaquinaDaoToMaquina()
    }

    override fun save(item: Maquina): Maquina =transaction{
        logger.debug { "Save maquina" }
        item.uuid?.let {
            maquinaDAO.findById(it)?.let { update ->
                update(item, update)
            }
        } ?: run {
            add(item)
        }
    }

    override fun add(item: Maquina): Maquina = transaction {
        logger.debug { "añadiendo maquina: $item" }
        maquinaDAO.new {
            modelo = item.modelo
            fechaAdquisicion = item.fechaAdquisicion
            disponible = item.disponible
        }.fromMaquinaDaoToMaquina()
    }


    private fun update(item: Maquina, updateItem: MaquinaDAO): Maquina =transaction{
        logger.debug { "actualizando maquina" }
        updateItem.apply {
            modelo = item.modelo
            fechaAdquisicion = item.fechaAdquisicion
            disponible = item.disponible
        }.fromMaquinaDaoToMaquina()
    }

    override fun delete(item: Maquina): Boolean = transaction{
        logger.debug { "eliminando maquina" }
        val existe = maquinaDAO.findById(item.uuid!!)?: return@transaction false
        existe.disponible = false
        return@transaction true
    }

    override fun findAll(): List<Maquina> = transaction{
        logger.debug { "buscando todas las maquinas" }
        maquinaDAO.all().map { it.fromMaquinaDaoToMaquina()}
    }

    override fun deleteAll(): Boolean =transaction{
        logger.debug { "Eliminando todas las máquinas"}
        var delete = MaquinaTable.deleteAll()
        return@transaction delete>0
    }

}