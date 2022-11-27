package repositories.maquinas


import entities.EncordadorTable
import entities.enums.TipoMaquina
import entities.maquinas.PersonalizadorDAO
import entities.maquinas.PersonalizadorTable
import mappers.fromPersonalizadorDaoToPersonalizadora
import models.maquinas.Personalizadora
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PersonalizadoraRepositoryImpl(private var personalizadorDAO: IntEntityClass<PersonalizadorDAO>) : PersonalizadoraRepository {
    private var logger = KotlinLogging.logger {}

    override fun findById(id: Int): Personalizadora? {
        logger.debug { "buscando Personalizadora por id" }
        return personalizadorDAO.findById(id)?.fromPersonalizadorDaoToPersonalizadora()
    }

    override fun findByUUID(uuid: UUID): Personalizadora? = transaction {
        logger.debug { "buscando Personalizadora por uuid" }
        PersonalizadorDAO.find { PersonalizadorTable.uuid eq uuid }.firstOrNull()
            ?.fromPersonalizadorDaoToPersonalizadora()
    }

    override fun save(item: Personalizadora): Personalizadora = transaction {
        logger.debug { "Save trabajador" }
        var result = personalizadorDAO.find { EncordadorTable.uuid eq item.uuid!! }.firstOrNull()
        result?.let {
            update(item, result)
        }?: run{
            add(item)
        }
    }

    override fun add(item: Personalizadora): Personalizadora = transaction {
        logger.debug { "añadiendo Personalizadora" }
        personalizadorDAO.new {
            uuid = item.uuid!!
            modelo = item.modelo
            marca = item.marca
            fechaAdquisicion = item.fechaAdquisicion
            disponible = item.disponible
            maniobrabilidad = item.maniobrabilidad
            balance = item.balance
            rigidez = item.rigidez
        }.fromPersonalizadorDaoToPersonalizadora()
    }

    fun update(item: Personalizadora, updateItem: PersonalizadorDAO): Personalizadora = transaction {
        logger.debug { "acualizando Personalizadora" }
        updateItem.apply {
            uuid = item.uuid!!
            modelo = item.modelo
            marca = item.marca
            fechaAdquisicion = item.fechaAdquisicion
            disponible = item.disponible
            maniobrabilidad = item.maniobrabilidad
            balance = item.balance
            rigidez = item.rigidez
        }.fromPersonalizadorDaoToPersonalizadora()
    }

    override fun delete(item: Personalizadora): Boolean = transaction {
        val existe = item.uuid?.let {
            personalizadorDAO.find { PersonalizadorTable.uuid eq item.uuid!! }
        } ?: return@transaction false
        logger.debug { "eliminando Personalizadora" }
        existe.first().delete()
        return@transaction true
    }

    override fun findAll(): List<Personalizadora> = transaction {
        logger.debug { "recuperando todos los Personalizadoraes" }
        personalizadorDAO.all().map { it.fromPersonalizadorDaoToPersonalizadora() }
    }

    override fun deleteAll(): Boolean = transaction {
        logger.debug { "eliminando todos los Personalizadoraes" }
        var num = PersonalizadorTable.deleteAll()
        return@transaction num > 0
    }
}