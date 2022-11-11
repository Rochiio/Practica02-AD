import com.diogonunes.jcolor.Ansi.colorize
import com.diogonunes.jcolor.Attribute
import config.AppConfig
import db.DataBaseManager
import models.*
import models.enums.tipoUsuario
import models.usuarios.Trabajador
import models.usuarios.Trabajadores
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger

import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate


fun main(args: Array<String>) {
    //initDataBase()
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Usuarios, Trabajadores)

        var x = create()
        println(colorize(x.toString(), Attribute.RED_TEXT()))

        var usuarios =Usuario.all()
        for (usuario in usuarios){
            println(colorize(usuario.toString(), Attribute.RED_TEXT()))
        }

        var turnos =Turno.all()
        for (turno in turnos){
            println(colorize(turno.toString(), Attribute.RED_TEXT()))
        }

        var maquinas = Maquina.all()
        for (maquina in maquinas){
            println(colorize(maquina.toString(), Attribute.RED_TEXT()))
        }


    }
}


fun create(): Trabajador {
    val trabajador =Trabajador.new {
        administrador=false
        usuario = Usuario.new {
            nombre="Pepe"
            apellido="Garcia"
            email ="vsdf@gmail.com"
            password ="frgggg"
            tipo= tipoUsuario.TRABAJADOR.toString()
        }
        turno = Turno.new {
            comienzoTurno="12:00"
            finTurno="15:30"
            maquina = Maquina.new {
                modelo="LPG2015"
                fechaAdquisicion=LocalDate.now()
                disponible=false

            }
        }
    }

    return trabajador
}

fun read(): Encordador? {
    val encordador = Encordador.find {Encordadores.automatico eq true}
    return encordador.firstOrNull()
}


fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}