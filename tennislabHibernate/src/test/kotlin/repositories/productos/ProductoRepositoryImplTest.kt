package repositories.productos

import entities.enums.TipoProduct
import models.pedidos.Producto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ProductoRepositoryImplTest {
    private var repository= ProductoRepositoryImpl()
    private var test = Producto(tipo = TipoProduct.COMPLEMENTO, marca = "marca", modelo="modelo", precio=12.50f, stock=5)

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun findById() {
        var add = repository.save(test)
        var find = repository.findById(add.uuid)

        assertAll(
            { assertNotNull(find) },
            { assertEquals(find?.uuid, add.uuid) },
            { assertEquals(find?.tipo, add.tipo) },
            { assertEquals(find?.marca, add.marca) },
            { assertEquals(find?.modelo, add.modelo) },
            { assertEquals(find?.precio, add.precio) },
            { assertEquals(find?.stock, add.stock) }
        )
    }

    @Test
    fun save() {
        var add = repository.save(test)
        assertAll(
            { assertEquals(add.tipo, test.tipo) },
            { assertEquals(add.marca, test.marca) },
            { assertEquals(add.modelo, test.modelo) },
            { assertEquals(add.precio, test.precio) },
            { assertEquals(add.stock, test.stock) }
        )
    }

    @Test
    fun delete() {
        var add = repository.save(test)
        var delete = repository.delete(add)

        assertTrue(delete)
    }

    @Test
    fun findAll() {
        var add = repository.save(test)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].uuid, add.uuid) },
            { assertEquals(lista[0].tipo, add.tipo) },
            { assertEquals(lista[0].marca, add.marca) },
            { assertEquals(lista[0].modelo, add.modelo) },
            { assertEquals(lista[0].precio, add.precio) },
            { assertEquals(lista[0].stock, add.stock) }
        )
    }

    @Test
    fun deleteAll() {
        repository.save(test)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}