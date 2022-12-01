package controller

import entities.enums.TipoProduct
import exception.ClienteError
import exception.ProductoError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.pedidos.Producto
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.productos.ProductoRepositoryImpl
import java.util.*

@ExtendWith(MockKExtension::class)
class ProductosControllerTest {
    @MockK
    private lateinit var repository: ProductoRepositoryImpl

    @InjectMockKs
    private lateinit var controller: ProductosController

    private var test = Producto(1, UUID.randomUUID(), TipoProduct.COMPLEMENTO, "MarcaX", "ModeloY", 15.5f, 10)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun addProducto() {
        every { repository.findByUUID(test.uuid!!) } returns null
        every { repository.save(test) } returns test
        every { repository.add(test) } returns test

        var add = controller.addProducto(test)

        assertAll(
            { assertEquals(add.id, test.id) },
            { assertEquals(add.uuid, test.uuid) },
            { assertEquals(add.tipo, test.tipo) },
            { assertEquals(add.marca, test.marca) },
            { assertEquals(add.modelo, test.modelo) },
            { assertEquals(add.precio, test.precio) },
            { assertEquals(add.stock, test.stock) }
        )

        verify(exactly=1){repository.save(test)}
        verify(exactly=1){repository.findByUUID(test.uuid!!)}
    }

    @Test
    fun addProductoYaExiste(){
        every { repository.findByUUID(test.uuid!!) } returns test
        var exception = assertThrows(ProductoError::class.java){
            controller.addProducto(test)
        }

        assertEquals("Ya existe un producto con este UUID", exception.item)

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }

    @Test
    fun getProductoByUUID() {
        every { repository.findByUUID(test.uuid!!) } returns test
        var find = controller.getProductoByUUID(test.uuid!!)

        assertAll(
            { assertEquals(find.id, test.id) },
            { assertEquals(find.uuid, test.uuid) },
            { assertEquals(find.tipo, test.tipo) },
            { assertEquals(find.marca, test.marca) },
            { assertEquals(find.modelo, test.modelo) },
            { assertEquals(find.precio, test.precio) },
            { assertEquals(find.stock, test.stock) }
        )

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }

    @Test
    fun getProductoByUUIDNoExiste(){
        every { repository.findByUUID(test.uuid!!) } returns null
        var exception = assertThrows(ProductoError::class.java){
            controller.getProductoByUUID(test.uuid!!)
        }

        assertEquals("No existe el producto con este UUID", exception.item)

        verify(exactly=1){ repository.findByUUID(test.uuid!!) }
    }

    @Test
    fun getAllProductos() {
        every { repository.findAll() } returns listOf(test)
        var lista = controller.getAllProductos()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, test.id) },
            { assertEquals(lista[0].uuid, test.uuid) },
            { assertEquals(lista[0].tipo, test.tipo) },
            { assertEquals(lista[0].marca, test.marca) },
            { assertEquals(lista[0].modelo, test.modelo) },
            { assertEquals(lista[0].precio, test.precio) },
            { assertEquals(lista[0].stock, test.stock) }
        )
    }

    @Test
    fun deleteProducto() {
        every{ repository.delete(test) } returns true
        var eliminado = controller.deleteProducto(test)
        assertTrue(eliminado)
        verify(exactly=1){repository.delete(test)}
    }

    @Test
    fun deleteProductoNoExiste(){
        every { repository.delete(test) } returns false
        var exception = assertThrows(ProductoError::class.java){
            controller.deleteProducto(test)
        }

        assertEquals("Problemas al eliminar el producto", exception.item)

        verify(exactly=1){repository.delete(test) }
    }
}