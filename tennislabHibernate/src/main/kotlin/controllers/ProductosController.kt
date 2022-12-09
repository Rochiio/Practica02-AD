package controllers

import exception.ProductoError
import models.pedidos.Producto
import repositories.productos.ProductoRepository
import java.util.*

/**
 * Controlador de productos
 */
class ProductosController(private var repository: ProductoRepository) {

    /**
     * Añadir un producto
     */
    @Throws(ProductoError::class)
    fun addProducto(item: Producto): Producto {
        var existe = repository.findById(item.uuid)
        existe?.let {
            throw  ProductoError("Ya existe un producto con este UUID")
        }?: run{
            repository.save(item)
            return item
        }
    }


    /**
     * Buscar un producto por su uuid.
     */
    @Throws(ProductoError::class)
    fun getProductoByUUID(uuid: UUID): Producto {
        var existe = repository.findById(uuid)
        existe?.let {
            return it
        }?: run{
            throw ProductoError("No existe el producto con este UUID")
        }
    }


    /**
     * Actualizar un producto
     */
    fun updateProducto(item: Producto): Producto {
        return repository.save(item)
    }


    /**
     * Conseguir todos los productos.
     */
    fun getAllProductos(): List<Producto> {
        return repository.findAll()
    }


    /**
     * Eliminar un producto.
     */
    @Throws(ProductoError::class)
    fun deleteProducto(item: Producto):Boolean {
        var correcto =repository.delete(item)
        if(correcto){
            println("Producto eliminado correctamente")
            return true
        }else{
            throw  ProductoError("Problemas al eliminar el producto")
        }
    }
}