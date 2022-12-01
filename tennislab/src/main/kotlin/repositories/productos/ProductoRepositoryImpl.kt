package repositories.productos

import entities.pedidos.ProductoDAO
import entities.pedidos.ProductoTable
import mappers.fromProductoDaoToProducto
import models.pedidos.Producto
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ProductoRepositoryImpl(
    private var productoDAO: IntEntityClass<ProductoDAO>
): ProductoRepository{
    private var logger = KotlinLogging.logger {}

    override fun findById(id: Int): Producto? =transaction{
        logger.debug { "Buscando producto por id"}
        productoDAO.findById(id)?.fromProductoDaoToProducto()
    }

    override fun findByUUID(uuid: UUID): Producto? = transaction{
        logger.debug { "Buscando producto por uuid" }
        productoDAO.find { ProductoTable.uuid eq uuid}.firstOrNull()?.fromProductoDaoToProducto()
    }

    override fun save(item: Producto): Producto = transaction{
        logger.debug { "Save producto" }
        item.id?.let {
            productoDAO.findById(it)?.let { update ->
                update(item, update)
            }
        } ?: run {
            add(item)
        }
    }

    override fun add(item: Producto): Producto =transaction{
        logger.debug { "Añadiendo producto" }
        productoDAO.new {
            tipo = item.tipo
            marca= item.marca
            modelo = item.modelo
            precio= item.precio
            stock= item.stock
        }.fromProductoDaoToProducto()
    }


    fun update(item: Producto, updateItem: ProductoDAO): Producto = transaction{
        logger.debug { "actualizando producto" }
       updateItem.apply {
           tipo = item.tipo
           marca= item.marca
           modelo = item.modelo
           precio= item.precio
           stock= item.stock
       }.fromProductoDaoToProducto()
    }

    override fun delete(item: Producto): Boolean = transaction{
        val existe = item.id?.let { productoDAO.findById(it) } ?: return@transaction false
        logger.debug { "eliminando producto: $item" }
        existe.delete()
        return@transaction true
    }

    override fun findAll(): List<Producto> =transaction{
        logger.debug { "Buscando todos los productos"}
        productoDAO.all().map { it.fromProductoDaoToProducto()}.toList()
    }

    override fun deleteAll(): Boolean = transaction{
        logger.debug { "Eliminando todos los productos"}
        var num = ProductoTable.deleteAll()
        return@transaction num>0
    }

}