import config.AppConfig
import controller.*
import db.DataBaseManager
import entities.EncordadorDAO
import entities.EncordadorTable
import entities.enums.Estado
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
import mappers.fromPedidoDaoToPedido
import mappers.fromTareaDaoToTarea
import mappers.fromTrabajadorDaoToTrabajador
import models.pedidos.Pedido
import models.usuarios.Trabajador
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.EncordadoRepositoryImpl
import repositories.maquinas.PersonalizadoraRepositoryImpl
import repositories.pedidos.PedidoRepositoryImpl
import repositories.pedidos.TareaRepositoryImpl
import repositories.usuarios.ClienteRepositoryImpl
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.PasswordParser
import view.Vista
import java.time.LocalDate
import java.util.UUID


fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(TrabajadorTable, MaquinaTable, PedidoTable, TareaTable)
        val pedidoRepositoryImpl = PedidoRepositoryImpl(PedidoDAO)

        var tr = TrabajadorDAO.new {
            nombre = "Pepe"
            apellido = "apellido"
            email = "pepe@gmail.com"
            password = PasswordParser.encriptar("1234")
            disponible = true
            administrador = true
        }
        val pedidoController = PedidosController(PedidoRepositoryImpl(PedidoDAO), TareaRepositoryImpl(TareaDAO))
        val trabajadorController = TrabajadoresController(TrabajadorRepositoryImpl(TrabajadorDAO))
        val tareaController = TareasController(TareaRepositoryImpl(TareaDAO))
        println(trabajadorController.getAllTrabajadores())

        trabajadorController.addTrabajador(tr.fromTrabajadorDaoToTrabajador())
        var pedido = PedidoDAO.new {
            uuid = UUID.randomUUID()
            estado = Estado.RECIBIDO.toString()
            fechaEntrada = LocalDate.now()
            fechaSalida = LocalDate.now()
            fechaFinal = LocalDate.now()
            precioTotal = 10.0F
            topeEntrega = LocalDate.now()
        }
        // pedidoRepositoryImpl.save(pedido)
        pedidoController.addPedido(pedido.fromPedidoDaoToPedido())
        val res = pedidoController.getPedidos()
        println(res)
        //pedidoController.deletePedido(pedido.fromPedidoDaoToPedido())
        println(pedidoController.getPedidos())

        var t1 = TareaDAO.new {
            idTrabajador = tr
            idMaquina = null
            idPedido = null
            precio = 10
            tipoTarea = TipoTarea.ENCORDADO
            descripcion = "desc1"
            idPedido = pedido
            disponible = true
        }
        var t2 = TareaDAO.new {
            idTrabajador = tr
            idMaquina = null
            idPedido = null
            precio = 10
            tipoTarea = TipoTarea.ENCORDADO
            descripcion = "desc1"
            disponible = true
        }

        println(t1)
        println(pedido)
        tareaController.addTarea(t1.fromTareaDaoToTarea())
        println(tareaController.addPedidoId(t1.fromTareaDaoToTarea(), pedido.fromPedidoDaoToPedido()))
        println(t1)

        /*println("Tareas -> \n ${tareaController.getTareas()}")
           pedidoController.addTareaToPedido(pedido.fromPedidoDaoToPedido(), t1.fromTareaDaoToTarea())

            println(pedidoController.getPedidos())*/
        /*

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
            */

    }


    fun initDataBase() {
        val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
        println("Configuración: $appConfig")

        // Iniciamos la base de datos con la configuracion que hemos leido
        DataBaseManager.init(appConfig)

    }
}