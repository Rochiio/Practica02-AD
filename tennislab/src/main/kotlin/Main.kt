import config.AppConfig
import controller.ClientesController
import controller.MaquinasController
import controller.ProductosController
import controller.TrabajadoresController
import db.DataBaseManager
import entities.EncordadorDAO
import entities.EncordadorTable
import entities.maquinas.PersonalizadorDAO
import entities.maquinas.PersonalizadorTable
import entities.pedidos.ProductoDAO
import entities.pedidos.ProductoTable
import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import models.usuarios.Trabajador
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.EncordadoRepositoryImpl
import repositories.maquinas.PersonalizadoraRepositoryImpl
import repositories.productos.ProductoRepositoryImpl
import repositories.usuarios.ClienteRepositoryImpl
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.PasswordParser
import view.Vista


fun main(args: Array<String>) = runBlocking {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    println(Trabajador::class.members)
    transaction {
        SchemaUtils.create(TrabajadorTable, EncordadorTable, PersonalizadorTable, ClienteTable, ProductoTable)

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
 * Hacer los ficheros con json.
 */
suspend fun makeJsonListas() {
    withContext(Dispatchers.IO) {

    }
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