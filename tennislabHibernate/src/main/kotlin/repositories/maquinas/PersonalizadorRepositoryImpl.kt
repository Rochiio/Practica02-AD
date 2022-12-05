package repositories.maquinas

import db.HibernateManager
import db.HibernateManager.manager
import models.maquinas.Personalizador
import models.pedidos.Producto
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

/**
 * Implementación repositorio de personalizadores.
 */
class PersonalizadorRepositoryImpl: PersonalizadorRepository {
    private var logger = KotlinLogging.logger {}

    override fun findById(id: UUID): Personalizador? {
        logger.debug { "Buscando personalizador por id" }
        var encontrado: Personalizador? = null
        HibernateManager.query {
            encontrado = manager.find(Personalizador::class.java, id)
        }
        return encontrado
    }

    override fun save(item: Personalizador): Personalizador {
        logger.debug { "Salvando personalizador" }
        var add:Personalizador? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Personalizador): Boolean {
        logger.debug { "Eliminando personalizador" }
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(manager.find(Personalizador::class.java, item.uuid))
            eliminado = true
        }
        return eliminado
    }

    override fun findAll(): List<Personalizador> {
        logger.debug { "Buscando todos los personalizadores" }
        var lista = mutableListOf<Personalizador>()
        HibernateManager.query {
            var query: TypedQuery<Personalizador> = manager.createNamedQuery("Personalizador.findAll", Personalizador::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Eliminando todos los personalizadores" }
        var eliminado = false
        HibernateManager.transaction{
            var query = manager.createQuery("delete from Personalizador ")
            query.executeUpdate()
            eliminado = true
        }
        return eliminado
    }
}