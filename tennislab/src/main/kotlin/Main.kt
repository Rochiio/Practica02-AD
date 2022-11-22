import config.AppConfig
import controller.TrabajadoresController
import db.DataBaseManager
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.PasswordParser
import view.Vista


fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction{
        SchemaUtils.create(TrabajadorTable)

        TrabajadorDAO.new {
            nombre = "Pepe"
            apellido="apellido"
            email = "pepe@gmail.com"
            password= PasswordParser.encriptar("1234")
            disponible = true
            administrador = true
        }

        var vista= Vista(TrabajadoresController(TrabajadorRepositoryImpl(TrabajadorDAO)))

        do {
            var num = vista.principal()
            vista.opcionesPrincipal(num)
        }while (num!=0)
    }




//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
//    var repo = UsuarioRepositoryImpl(UsuarioDAO)
//    transaction {
//        SchemaUtils.create(UsuarioTable, TrabajadorTable)
//        var usuer = Usuario(null,"Pepe","Pele","dfjhihfg","4544",true)
//        val guardada = repo.save(usuer)
//
//        var salida =TrabajadorDAO.new {
//            usuario=UsuarioDAO.findById(guardada.uuid!!)!!
//            administrador= false
//        }


    }

//    initDataBase()
//    val repo = TrabajadorRepositoryImpl(TrabajadorDAO, UsuarioDAO)
//    var maqui = Maquina(UUID.randomUUID(), "modelo1", LocalDate.now(), true)
//    var turno = Turno(UUID.randomUUID(), "10", "13", maqui)
//    var usuario = Usuario(UUID.randomUUID(), "moah", "asidah", "emal", "pass", true)
//    var trabajador = Trabajador(usuario.uuid, usuario, true, turno)
//
//    transaction {
//
//        var a = UsuarioDAO.new(usuario.uuid) {
//            iID = 1
//            nombre = usuario.nombre
//            apellido = usuario.apellido
//            email = usuario.email
//            password = usuario.password
//            disponible = usuario.disponible
//        }
//        var m = MaquinaDAO.new(maqui.uuid) {
//            modelo = maqui.modelo
//            fechaAdquisicion = maqui.fechaAdquisicion
//            disponible = maqui.disponible
//        }
//
//        var t = TurnoDAO.new(turno.uuid) {
//            iID = 1
//            comienzoTurno = turno.comienzoTurno
//            finTurno = turno.comienzoTurno
//            maquina = null
//            pedidos = null
//        }
//        SchemaUtils.create(TrabajadorTable, UsuarioTable, MaquinaTable)
//        println(repo.save(trabajador))
//
//    }


//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
//    transaction {
//        SchemaUtils.create(Tareas)
//        val tarea = crearTarea()
//        println(tarea)
//    }




    //initDataBase()
//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
//
//    transaction {
//        addLogger(StdOutSqlLogger)
//        SchemaUtils.create(UsuarioTable, Trabajadores)
//
//        var x = create()
//        println(colorize(x.toString(), Attribute.RED_TEXT()))
//
//        var usuarios =Usuario.all()
//        for (usuario in usuarios){
//            println(colorize(usuario.toString(), Attribute.RED_TEXT()))
//        }
//
//        var turnos =Turno.all()
//        for (turno in turnos){
//            println(colorize(turno.toString(), Attribute.RED_TEXT()))
//        }
//
//        var maquinas = Maquina.all()
//        for (maquina in maquinas){
//            println(colorize(maquina.toString(), Attribute.RED_TEXT()))
//        }
//
//
//    }



//fun create(): Trabajador {
//    val trabajador =Trabajador.new {
//        administrador=false
//        usuario = Usuario.new {
//            nombre="Pepe"
//            apellido="Garcia"
//            email ="vsdf@gmail.com"
//            password ="frgggg"
//            tipo= TipoUsuario.TRABAJADOR.toString()
//        }
//        turno = Turno.new {
//            comienzoTurno="12:00"
//            finTurno="15:30"
//            maquina = Maquina.new {
//                modelo="LPG2015"
//                fechaAdquisicion=LocalDate.now()
//                disponible=false
//
//            }
//        }
//    }
//
//    return trabajador
//}
//fun crearTarea() : TareaDAO{
//    return TareaDAO.new {
//        precio = 15
//        raqueta = "Raqueta1"
//        tipoTarea = TipoTarea.ENCORDADO
//    }
//}
//fun createEncordadora(maquina: MaquinaDAO): EncordadorDAO {
//    return EncordadorDAO.new {
//        automatico = true
//        tensionMaxima = 10
//        tensionMinima = 1
//        this.maquinaID = maquina.uuid
//    }
//
//}
//
//fun read(): Encordador? {
//    return Encordador.all().map { it }.toList().firstOrNull()
//}
//fun maquinaById(m : Maquina) : Maquina?{
//    return Maquina.findById(m.uuid)
//}


fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}