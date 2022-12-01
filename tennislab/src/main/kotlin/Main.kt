import config.AppConfig
import controller.ClientesController
import controller.MaquinasController
import controller.TrabajadoresController
import db.DataBaseManager
import entities.EncordadorDAO
import entities.EncordadorTable
import entities.enums.TipoTarea
import entities.maquinas.MaquinaTable
import entities.maquinas.PersonalizadorDAO
import entities.maquinas.PersonalizadorTable
import entities.pedidos.PedidoDAO
import entities.pedidos.PedidoTable
import entities.pedidos.TareaDAO
import entities.pedidos.TareaTable
import entities.usuarios.ClienteDAO
import entities.usuarios.ClienteTable
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.EncordadoRepositoryImpl
import repositories.maquinas.PersonalizadoraRepositoryImpl
import repositories.usuarios.ClienteRepositoryImpl
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.PasswordParser
import view.Vista
import java.time.LocalDate


fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(TrabajadorTable, MaquinaTable, PedidoTable, TareaTable)

        var tr = TrabajadorDAO.new {
            nombre = "Pepe"
            apellido = "apellido"
            email = "pepe@gmail.com"
            password = PasswordParser.encriptar("1234")
            disponible = true
            administrador = true
        }
        var pedido = PedidoDAO.new {
            estado = "fin"
            fechaEntrada = LocalDate.now()
            fechaSalida = LocalDate.now()
            fechaFinal = LocalDate.now()
            precioTotal = 10.0F
            topeEntrega = LocalDate.now()
        }
        var t1 = TareaDAO.new {
            idTrabajador = tr
            idMaquina = null
            idPedido = pedido
            precio = 10
            tipoTarea = TipoTarea.ENCORDADO
            descripcion = "desc1"
            disponible = true
        }
        var t2 = TareaDAO.new {
            idTrabajador = tr
            idMaquina = null
            idPedido = pedido
            precio = 10
            tipoTarea = TipoTarea.ENCORDADO
            descripcion = "desc2"
            disponible = true
        }
        println("----------------------")
        println(pedido)
        pedido.tareas.forEach { println(it) }
        println("----------------------")


        var vista = Vista(
            TrabajadoresController(TrabajadorRepositoryImpl(TrabajadorDAO)),
            MaquinasController(
                EncordadoRepositoryImpl(EncordadorDAO),
                PersonalizadoraRepositoryImpl(PersonalizadorDAO)
            ),
            ClientesController(ClienteRepositoryImpl(ClienteDAO))
        )

        do {
            var num = vista.principal()
            vista.opcionesPrincipal(num)
        } while (num != 0)
    }


}


fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}