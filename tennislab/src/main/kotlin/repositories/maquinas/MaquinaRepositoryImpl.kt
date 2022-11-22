package repositories.maquinas

import entities.maquinas.MaquinaDAO
import entities.maquinas.MaquinaTable
import mappers.fromMaquinaDaoToMaquina
import models.maquinas.Maquina
import mu.KotlinLogging
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * Implementación repositorio de maquinas.
 */
class MaquinaRepositoryImpl(
    private var maquinaDAO: UUIDEntityClass<MaquinaDAO>
) : MaquinaRepository {

    private var logger = KotlinLogging.logger {}


    /**
     * Buscar una maquina por su id.
     * @param id de la maquina a buscar.
     * @return maquina dependiendo de si es encontrada.
     */
    override fun findById(id: UUID): Maquina? = transaction {
        logger.debug { "buscando maquina con uuid: $id" }
        maquinaDAO.findById(id)?.fromMaquinaDaoToMaquina()
    }


    /**
     * Buscar una maquina por su uuid.
     * @param uuid de la maquina a buscar.
     * @return maquina dependiendo de si existe.
     */
    override fun findByUUID(uuid: UUID): Maquina? = transaction {
        logger.debug { "buscando maquina con uuid: $uuid" }
        maquinaDAO.findById(uuid)?.fromMaquinaDaoToMaquina()
    }


    /**
     * Salvar una maquina.
     * @param item maquina a salvar.
     * @return maquina
     */
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


    /**
     * Añadir una maquina.
     * @param item maquina a añdir.
     * @return maquina
     */
    override fun add(item: Maquina): Maquina = transaction {
        logger.debug { "añadiendo maquina: $item" }
        maquinaDAO.new {
            marca = item.marca
            modelo = item.modelo
            fechaAdquisicion = item.fechaAdquisicion
            numeroSerie = item.numeroSerie
            disponible = item.disponible
        }.fromMaquinaDaoToMaquina()
    }


    /**
     * Actualizar una maquina.
     * @param item maquina modelo actualizado.
     * @param updateItem maquina DAO a actualizar.
     * @return maquina
     */
    private fun update(item: Maquina, updateItem: MaquinaDAO): Maquina =transaction{
        logger.debug { "actualizando maquina" }
        updateItem.apply {
            marca = item.marca
            modelo = item.modelo
            fechaAdquisicion = item.fechaAdquisicion
            numeroSerie = item.numeroSerie
            disponible = item.disponible
        }.fromMaquinaDaoToMaquina()
    }


    /**
     * Eliminar una maquina.
     * @param item maquina a eliminar.
     * @return boolean si se ha eliminado o no.
     */
    override fun delete(item: Maquina): Boolean = transaction{
        logger.debug { "eliminando maquina" }
        val existe = maquinaDAO.findById(item.uuid!!)?: return@transaction false
        existe.disponible = false
        return@transaction true
    }


    /**
     * Buscar todas las maquinas que hay.
     * @return lista de maquinas.
     */
    override fun findAll(): List<Maquina> = transaction{
        logger.debug { "buscando todas las maquinas" }
        maquinaDAO.all().map { it.fromMaquinaDaoToMaquina()}
    }


    /**
     * Eliminar todas las maquinas que existen.
     * @return boolean dependiendo de si se ha eliminado.
     */
    override fun deleteAll(): Boolean =transaction{
        logger.debug { "Eliminando todas las máquinas"}
        var delete = MaquinaTable.deleteAll()
        return@transaction delete>0
    }

}