package repositories.usuarios

import db.HibernateManager
import db.HibernateManager.manager
import models.usuarios.Cliente
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

/**
 * Implementación repositorio de clientes.
 */
class ClienteRepositoryImpl: ClienteRepository {
    private var logger = KotlinLogging.logger {}
    override fun findByEmail(email: String): Cliente? {
        logger.debug { "Buscando cliente por su email"}
        var encontrado: Cliente? = null
        HibernateManager.query {
            var query: TypedQuery<Cliente> = manager.createNamedQuery("Cliente.findByEmail", Cliente::class.java)
            query.setParameter("email",email)
            encontrado = query.singleResult
        }
        return encontrado
    }

    override fun findById(id: UUID): Cliente? {
        logger.debug { "Buscando cliente por el id" }
        var encontrado: Cliente? = null
        HibernateManager.query {
            encontrado = manager.find(Cliente::class.java, id)
        }
        return encontrado
    }

    override fun save(item: Cliente): Cliente {
        logger.debug { "Salvando cliente" }
        var add: Cliente? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Cliente): Boolean {
        logger.debug { "Eliminando cliente"}
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(item)
            eliminado = true
        }
        return eliminado
    }

    override fun findAll(): List<Cliente> {
        logger.debug { "Buscando todos los clientes" }
        var lista = mutableListOf<Cliente>()
        HibernateManager.query {
            var query: TypedQuery<Cliente> = manager.createNamedQuery("Cliente.findAll", Cliente::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Eliminando todos los clientes" }
        var eliminado = false
        HibernateManager.transaction{
            var query: TypedQuery<Cliente> = manager.createNamedQuery("Cliente.deleteAll", Cliente::class.java)
            query.firstResult
            eliminado = true
        }
        return eliminado
    }
}