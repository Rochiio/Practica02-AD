import config.AppConfig
import db.DataBaseManager
import models.*
import models.enums.tipoUsuario
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
        SchemaUtils.create(Maquinas, Encordadores)

        create()
        val encordar = read()
        encordar?.let {
            println(it)
        }?: run{
            println("NO EXISTE")
        }

    }
}


fun create(): Encordador {
    val encordador = Encordador.new {
        automatico=true
        tensionMaxima=20
        tensionMinima=16
        maquina = Maquina.new {
            modelo = "RPG"
            fechaAdquisicion = LocalDate.now()
            disponible = false
        }
    }
    return encordador
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