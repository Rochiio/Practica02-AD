package repositories.pedidos

import db.HibernateManager
import db.HibernateManager.manager
import models.pedidos.Pedido
import models.pedidos.Producto
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery

class PedidoRepositoryImpl : PedidoRepository {
    private var logger = KotlinLogging.logger {}
    override fun findById(id: UUID): Pedido? {
        logger.debug { "buscando pedido por ID" }
        var res: Pedido? = null
        HibernateManager.query {
            res = manager.find(Pedido::class.java, id)
        }
        return res
    }

    override fun save(item: Pedido): Pedido {
        logger.debug { "Salvado pedido" }
        var add: Pedido? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Pedido): Boolean {
        logger.debug { "Eliminando pedido" }
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(manager.find(Pedido::class.java, item.uuid))
            eliminado = true
        }
        return eliminado
    }

    override fun findAll(): List<Pedido> {
        logger.debug { "Buscando todos los pedidos" }
        var lista = mutableListOf<Pedido>()
        HibernateManager.query {
            var query: TypedQuery<Pedido> = manager.createNamedQuery("Pedido.findAll", Pedido::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Eliminando todos los pedidos" }
        var eliminado = false
        val hb = HibernateManager
        hb.transaction {
            var query = manager.createQuery("delete from Pedido")
            query.executeUpdate()
            eliminado = true
        }
        return eliminado
    }
}