package repositories.productos

import entities.enums.TipoProduct
import entities.pedidos.ProductoDAO
import entities.pedidos.ProductoTable
import entities.usuarios.TrabajadorTable
import models.pedidos.Producto
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class ProductoRepositoryImplTest {
    private var test = Producto(null, null, TipoProduct.COMPLEMENTO, "MarcaX", "ModeloY", 15.5f, 10)
    private var repository = ProductoRepositoryImpl(ProductoDAO)

    @BeforeEach
    fun setUp() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction{
            SchemaUtils.create(ProductoTable)
            repository.deleteAll()
        }
    }

    @Test
    fun findById() = transaction{
        SchemaUtils.create(ProductoTable)
        var add = repository.save(test)
        var find = repository.findById(add.id!!)

        assertAll(
            { assertNotNull(find) },
            { assertEquals(find?.id, add.id) },
            { assertEquals(find?.uuid, add.uuid) },
            { assertEquals(find?.tipo, add.tipo) },
            { assertEquals(find?.marca, add.marca) },
            { assertEquals(find?.modelo, add.modelo) },
            { assertEquals(find?.precio, add.precio) },
            { assertEquals(find?.stock, add.stock) }
        )
    }

    @Test
    fun findByUUID() = transaction{
        SchemaUtils.create(ProductoTable)
        var add = repository.save(test)
        var find = repository.findByUUID(add.uuid!!)

        assertAll(
            { assertNotNull(find) },
            { assertEquals(find?.id, add.id) },
            { assertEquals(find?.uuid, add.uuid) },
            { assertEquals(find?.tipo, add.tipo) },
            { assertEquals(find?.marca, add.marca) },
            { assertEquals(find?.modelo, add.modelo) },
            { assertEquals(find?.precio, add.precio) },
            { assertEquals(find?.stock, add.stock) }
        )
    }

    @Test
    fun save() =transaction{
        SchemaUtils.create(ProductoTable)
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
    fun add() =transaction{
        SchemaUtils.create(ProductoTable)
        var add = repository.add(test)
        assertAll(
            { assertEquals(add.tipo, test.tipo) },
            { assertEquals(add.marca, test.marca) },
            { assertEquals(add.modelo, test.modelo) },
            { assertEquals(add.precio, test.precio) },
            { assertEquals(add.stock, test.stock) }
        )
    }

    @Test
    fun delete() =transaction{
        SchemaUtils.create(ProductoTable)
        var add = repository.save(test)
        var delete = repository.delete(add)

        assertTrue(delete)
    }

    @Test
    fun findAll() =transaction{
        SchemaUtils.create(ProductoTable)
        var add = repository.save(test)
        var lista = repository.findAll()

        assertAll(
            { assertTrue(lista.isNotEmpty()) },
            { assertEquals(lista[0].id, add.id) },
            { assertEquals(lista[0].uuid, add.uuid) },
            { assertEquals(lista[0].tipo, add.tipo) },
            { assertEquals(lista[0].marca, add.marca) },
            { assertEquals(lista[0].modelo, add.modelo) },
            { assertEquals(lista[0].precio, add.precio) },
            { assertEquals(lista[0].stock, add.stock) }
        )
    }

    @Test
    fun deleteAll() =transaction{
        SchemaUtils.create(ProductoTable)
        repository.save(test)
        var delete = repository.deleteAll()

        assertTrue(delete)
    }
}