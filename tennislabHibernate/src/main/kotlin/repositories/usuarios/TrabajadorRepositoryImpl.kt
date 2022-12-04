package repositories.usuarios

import db.HibernateManager
import db.HibernateManager.manager
import models.usuarios.Cliente
import models.usuarios.Trabajador
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

/**
 * Implementacion repositorio de trabajadores
 */
class TrabajadorRepositoryImpl: TrabajadorRepository {
    private var logger = KotlinLogging.logger {}
    override fun findByEmail(email: String): Trabajador? {
        logger.debug { "Buscando trabajador por su email" }
        var encontrado:Trabajador? = null
        HibernateManager.query {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.findByEmail",  Trabajador::class.java)
            query.setParameter("email", email)
            encontrado = query.singleResult
        }
        return encontrado
    }

    override fun findById(id: UUID): Trabajador? {
        logger.debug { "Buscando trabajador por el id" }
        var encontrado: Trabajador? = null
        HibernateManager.query {
            encontrado = manager.find(Trabajador::class.java, id)
        }
        return encontrado
    }

    override fun save(item: Trabajador): Trabajador {
        logger.debug { "Salvando trabajador" }
        var add: Trabajador? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Trabajador): Boolean {
        logger.debug { "Eliminando trabajador"}
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(item)
            eliminado = true
        }
        return eliminado
    }


    override fun findAll(): List<Trabajador> {
        logger.debug { "Buscando todos los trabajadores" }
        var lista = mutableListOf<Trabajador>()
        HibernateManager.query {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.findAll", Trabajador::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        var eliminado = false
        HibernateManager.transaction {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.deleteAll", Trabajador::class.java)
            eliminado = true
        }
        return eliminado
    }
}