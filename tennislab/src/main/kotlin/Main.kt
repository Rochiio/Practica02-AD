import com.github.ajalt.mordant.animation.progressAnimation
import com.github.ajalt.mordant.terminal.Terminal
import config.AppConfig
import controller.*
import db.DataBaseManager
import entities.EncordadorDAO
import entities.EncordadorTable
import entities.enums.Estado
import entities.enums.TipoProduct
import entities.maquinas.PersonalizadorDAO
import entities.maquinas.PersonalizadorTable
import entities.pedidos.*
import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.listados.*
import models.pedidos.Pedido
import models.pedidos.Producto
import models.usuarios.Cliente
import models.usuarios.Trabajador
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.EncordadoRepositoryImpl
import repositories.maquinas.PersonalizadoraRepositoryImpl
import repositories.pedidos.PedidoRepositoryImpl
import repositories.pedidos.TareaRepositoryImpl
import repositories.productos.ProductoRepositoryImpl
import repositories.usuarios.ClienteRepositoryImpl
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.Data
import utils.PasswordParser
import utils.ficheros.*
import view.Vista
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

val t = Terminal()
var DIR_JSON = System.getProperty("user.dir") + File.separator + "listados" + File.separator
fun main(args: Array<String>) = runBlocking {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(
            TrabajadorTable,
            EncordadorTable,
            PersonalizadorTable,
            ClienteTable,
            ProductoTable,
            PedidoTable,
            TareaTable
        )

        /*TrabajadorDAO.new {
            nombre = "Pepe"
            apellido = "apellido"
            email = "pepe@gmail.com"
            password = PasswordParser.encriptar("1234")
            disponible = true
            administrador = true
        }*/

        val t = Trabajador(
            id = null,
            uuid = null,
            nombre = "Pepe",
            apellido = "apellido",
            email = "pepe@gmail.com",
            password = PasswordParser.encriptar("1234"),
            disponible = true,
            administrador = true
        )

        val t1 = Trabajador(
            id = null,
            uuid = null,
            nombre = "Luis",
            apellido = "apellido",
            email = "luis@gmail.com",
            password = PasswordParser.encriptar("1234"),
            disponible = true,
            administrador = false
        )

        val tC = TrabajadoresController(TrabajadorRepositoryImpl(TrabajadorDAO))
        val cC = ClientesController(ClienteRepositoryImpl(ClienteDAO))
        var c1 = Cliente(
            id = null,
            uuid = null,
            nombre = "pepe",
            apellido = "lopez",
            email = "c1@gmail.com",
            password = PasswordParser.encriptar("1234"),
            pedidos = null
        )

        var c2 = Cliente(
            id = null,
            uuid = null,
            nombre = "ana",
            apellido = "lopez",
            email = "c2@gmail.com",
            password = PasswordParser.encriptar("1234"),
            pedidos = null
        )
        val cordaje = Producto(null, null, TipoProduct.CORDAJE, "cuerda", "bonita", 10F, 100)
        val cordaje2 = Producto(null, null, TipoProduct.CORDAJE, "cuerda2", "bonita2", 10F, 100)

        val productosController = ProductosController(ProductoRepositoryImpl(ProductoDAO))
        productosController.addProducto(cordaje)
        productosController.addProducto(cordaje2)

        c1 = cC.addCliente(c1)
        c2 = cC.addCliente(c2)
        println("////////////////////////////////////////")
        var a = (cC.getAllClientes())
        val pedido = Pedido(
            null,
            Estado.RECIBIDO,
            LocalDate.now().minusDays(23),
            LocalDate.now(),
            LocalDate.now(),
            100F,
            LocalDate.now(),
            c2,
            arrayListOf()
        )
        PedidosController(PedidoRepositoryImpl(PedidoDAO), TareaRepositoryImpl(TareaDAO)).addPedido(pedido)
        tC.addTrabajador(t)

        tC.addTrabajador(t1)


        println(tC.getAllTrabajadores())

        var vista = Vista(
            TrabajadoresController(TrabajadorRepositoryImpl(TrabajadorDAO)),
            MaquinasController(
                EncordadoRepositoryImpl(EncordadorDAO),
                PersonalizadoraRepositoryImpl(PersonalizadorDAO)
            ),
            ClientesController(ClienteRepositoryImpl(ClienteDAO)),
            PedidosController(PedidoRepositoryImpl(PedidoDAO), //TareaRepositoryImpl(TareaDAO)
                 ),
            TareasController(TareaRepositoryImpl(TareaDAO)),

            ProductosController(ProductoRepositoryImpl(ProductoDAO))
        )


        do {
            var num = vista.principal()
            vista.opcionesPrincipal(num)
        } while (num != 0)
    }


    makeJsonListas()
}

/**
 * Hacer los ficheros con json
 */
suspend fun makeJsonListas() = withContext(Dispatchers.IO) {
    if (!Files.isDirectory(Path.of(DIR_JSON))) {
        Files.createDirectories(Path.of(DIR_JSON))
    }

    var productos = async {
        makeListadoProductosServicios()
    }

    var asignaciones = async {
        makeListadoAsignacionesEncordadores()
    }

    var pendientes = async {
        makeListadoPedidosPendientes()
    }

    var completados = async {
        makeListadoPedidosCompletados()
    }



    productos.await()
    asignaciones.await()
    pendientes.await()
    completados.await()
}


/**
 * Crear fichero de pedidos completados, mostrando un progress bar.
 */
suspend fun makeListadoPedidosCompletados() = withContext(Dispatchers.IO) {
    val progress = t.progressAnimation {
        text("listado_pedidos_completados.json")
        percentage()
        progressBar()
        completed()
    }

    progress.start()
    progress.updateTotal(100)

    var job = async {
        FicheroJsonPedidosCompletados().writeFichero(
            DIR_JSON + "listado_pedidos_completados.json",
            ListaPedidosCompletados(Data.pedidosCompletados.toList())
        )
    }

    while (!job.isCompleted) {
        progress.advance(5)
    }

    job.await()
    progress.stop()
}


/**
 * Crear fichero de pedidos pendientes, mostrando un progress bar.
 */
suspend fun makeListadoPedidosPendientes() = withContext(Dispatchers.IO) {
    val progress = t.progressAnimation {
        text("listado_pedidos_pendientes.json")
        percentage()
        progressBar()
        completed()
    }

    progress.start()
    progress.updateTotal(100)

    var job = async {
        FicheroJsonPedidosPendientes().writeFichero(
            DIR_JSON + "listado_pedidos_pendientes.json",
            ListaPedidosPendientes(Data.pedidosPendientes.toList())
        )
    }

    while (!job.isCompleted) {
        progress.advance(5)
    }

    job.await()
    progress.stop()
}


/**
 * Crear fichero de asignaciones de encordadores, mostrando un progress bar.
 */
suspend fun makeListadoAsignacionesEncordadores() = withContext(Dispatchers.IO) {
    val progress = t.progressAnimation {
        text("listado_asignaciones_encordadores.json")
        percentage()
        progressBar()
        completed()
    }

    progress.start()
    progress.updateTotal(100)

    var job = async {
        FicheroJsonAsignaciones().writeFichero(
            DIR_JSON + "listado_asignaciones_encordadores.json",
            ListadoAsignacionesEncordadores(Data.asignaciones.toList())
        )
    }

    while (!job.isCompleted) {
        progress.advance(5)
    }

    job.await()
    progress.stop()
}


/**
 * Crear fichero de productos y servicios, mostrando un progress bar.
 */
suspend fun makeListadoProductosServicios() = withContext(Dispatchers.IO) {
    val progress = t.progressAnimation {
        text("listado_productos_servicios.json")
        percentage()
        progressBar()
        completed()
    }

    progress.start()
    progress.updateTotal(100)

    var job = async {
        FicheroJsonProductosServicios().writeFichero(
            DIR_JSON + "listado_productos_servicios.json",
            ListadoProductosServicios(Data.servicios, Data.productos)
        )
    }

    while (!job.isCompleted) {
        progress.advance(5)
    }

    job.await()
    progress.stop()
}


/**
 * Iniciar la base de datos
 */
fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}
