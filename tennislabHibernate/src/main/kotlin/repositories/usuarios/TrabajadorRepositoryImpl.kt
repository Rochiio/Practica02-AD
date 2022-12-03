package repositories.usuarios

import db.HibernateManager
import db.HibernateManager.manager
import models.usuarios.Trabajador
import java.util.*
import javax.persistence.TypedQuery

/**
 * Implementacion repositorio de trabajadores
 */
class TrabajadorRepositoryImpl: TrabajadorRepository {
    override fun findById(id: UUID): Trabajador? {
        var encontrado: Trabajador? = null
        HibernateManager.query {
            encontrado = manager.find(Trabajador::class.java, id)
        }
        return encontrado
    }

    override fun save(item: Trabajador): Trabajador {
        HibernateManager.transaction {
            manager.merge(item)
        }
        return item
    }

    override fun delete(item: Trabajador): Boolean {
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(item)
            eliminado = true
        }
        return eliminado
    }

    /**
     * TODO da problema el name, investigar
     */
    override fun findAll(): List<Trabajador> {
        var lista = mutableListOf<Trabajador>()
        HibernateManager.query {
            var query: TypedQuery<Trabajador> = manager.createNamedQuery("Trabajador.findAll", Trabajador::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }
}