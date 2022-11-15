import config.AppConfig
import db.DataBaseManager
import models.*

import models.enums.TipoUsuario
import models.usuarios.Trabajador


import view.Vista
import java.time.LocalDate
import java.util.UUID


fun main(args: Array<String>) {
     var vista= Vista()
    do {
        var num = vista.principal()
        vista.opcionesPrincipal(num)
    }while (num!=0)


    //initDataBase()
//    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
//
//    transaction {
//        addLogger(StdOutSqlLogger)
//        SchemaUtils.create(Usuarios, Trabajadores)
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
}


fun create(): Trabajador {
    val trabajador =Trabajador.new {
        administrador=false
        usuario = Usuario.new {
            nombre="Pepe"
            apellido="Garcia"
            email ="vsdf@gmail.com"
            password ="frgggg"
            tipo= TipoUsuario.TRABAJADOR.toString()
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

fun createEncordadora(maquina: Maquina): Encordador {
    return Encordador.new {
        automatico = true
        tensionMaxima = 10
        tensionMinima = 1
        this.maquinaID = maquina.id
    }

}

fun read(): Encordador? {
    return Encordador.all().map { it }.toList().firstOrNull()
}
fun maquinaById(m : Maquina) : Maquina?{
    return Maquina.findById(m.id)
}


fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}