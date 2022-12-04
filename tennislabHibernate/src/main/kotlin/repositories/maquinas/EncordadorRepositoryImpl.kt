package repositories.maquinas

import db.HibernateManager
import db.HibernateManager.manager
import models.maquinas.Encordador
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

/**
 * Implementación repositorio de encordadores.
 */
class EncordadorRepositoryImpl: EncordadorRepository {
    private var logger = KotlinLogging.logger {}

    override fun findById(id: UUID): Encordador? {
        logger.debug { "Buscando encordador por id" }
        var encontrado: Encordador? = null
        HibernateManager.query {
            encontrado = manager.find(Encordador::class.java, id)
        }
        return encontrado
    }

    override fun save(item: Encordador): Encordador {
        logger.debug { "Salvando encordador" }
        var add: Encordador? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Encordador): Boolean {
        logger.debug { "Eliminando encordador" }
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(item)
            eliminado = true
        }
        return eliminado
    }

    override fun findAll(): List<Encordador> {
        logger.debug { "Buscando todos los encordadores" }
        var lista = mutableListOf<Encordador>()
        HibernateManager.query{
            var query: TypedQuery<Encordador> = manager.createNamedQuery("Encordador.findAll", Encordador::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Eliminando todos los encordadores" }
        var eliminado = false
        HibernateManager.transaction{
            var query: TypedQuery<Encordador> = manager.createNamedQuery("Encordador.deleteAll", Encordador::class.java)
            query.firstResult
            eliminado = true
        }
        return eliminado
    }
}