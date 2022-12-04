package repositories.productos

import db.HibernateManager
import db.HibernateManager.manager
import models.pedidos.Producto
import models.usuarios.Cliente
import mu.KotlinLogging
import java.util.*
import javax.persistence.TypedQuery
import kotlin.math.log10

/**
 * Implementación repositorio de productos.
 */
class ProductoRepositoryImpl: ProductoRepository {
    private var logger = KotlinLogging.logger {}

    override fun findById(id: UUID): Producto? {
        logger.debug { "Buscando producto por id"}
        var encontrado:Producto? = null
        HibernateManager.query {
            encontrado = manager.find(Producto::class.java, id)
        }
        return encontrado
    }

    override fun save(item: Producto): Producto {
        logger.debug { "Salvado producto" }
        var add: Producto? = null
        HibernateManager.transaction {
            add = manager.merge(item)
        }
        return add!!
    }

    override fun delete(item: Producto): Boolean {
        logger.debug { "Eliminando producto"}
        var eliminado = false
        HibernateManager.transaction {
            manager.remove(item)
            eliminado= true
        }
        return eliminado
    }

    override fun findAll(): List<Producto> {
        logger.debug { "Buscando todos los productos" }
        var lista = mutableListOf<Producto>()
        HibernateManager.query {
            var query: TypedQuery<Producto> = manager.createNamedQuery("Producto.findAll", Producto::class.java)
            lista = query.resultList
        }
        return lista
    }

    override fun deleteAll(): Boolean {
        logger.debug { "Eliminando todos los productos" }
        var eliminado = false
        HibernateManager.transaction {
            var query: TypedQuery<Producto> = manager.createNamedQuery("Producto.deleteAll", Producto::class.java)
            query.firstResult
            eliminado = true
        }
        return eliminado
    }
}