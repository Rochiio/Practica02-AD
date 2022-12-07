package repositories.pedidos

import db.HibernateManager
import models.pedidos.Tarea
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

class TareaRepositoryImpl : TareaRepository {
    private var logger = KotlinLogging.logger {}
    override fun findById(id: UUID): Tarea? {
        logger.debug { "buscando tarea por ID" }
        var res: Tarea? = null
        HibernateManager.query {
            res = HibernateManager.manager.find(Tarea::class.java, id)
        }
        return res
    }

    override fun save(item: Tarea): Tarea {
        logger.debug { "Salvado Tarea" }
        var add: Tarea? = null
        HibernateManager.transaction {
            add = HibernateManager.manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Tarea): Boolean {
        logger.debug { "Eliminando Tarea" }
        var eliminado = false
        HibernateManager.transaction {
            HibernateManager.manager.remove(HibernateManager.manager.find(Tarea::class.java, item.uuid))
            eliminado = true
        }
        return eliminado
    }

    override fun findAll(): List<Tarea> {
        logger.debug { "Buscando todos los Tareas" }
        var lista = mutableListOf<Tarea>()
        HibernateManager.query {
            var query: TypedQuery<Tarea> = HibernateManager.manager.createNamedQuery("Tarea.findAll", Tarea::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Eliminando todos los Tareas" }
        var eliminado = false
        HibernateManager.transaction {
            var query = HibernateManager.manager.createQuery("delete from Tarea")
            query.executeUpdate()
            eliminado = true
        }
        return eliminado
    }
}