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
import java.util.UUID


fun main(args: Array<String>) {
    //initDataBase()
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Maquinas, Encordadores)
        val maquina = createMaquina()
        create(maquina)
        val encordar = read()
        encordar?.let {
            println(it)
        }?: run{
            println("NO EXISTE")
        }

    }
}


fun create(maquina: Maquina): Encordador {
    val encordador = Encordador.new {
        automatico=true
        tensionMaxima=20
        tensionMinima=16
        uuid = maquina.uuid
    }
    return encordador
}

fun createMaquina(): Maquina{
    val maquina = Maquina.new {
        uuid = UUID.randomUUID()
        modelo = "Maquinita"
        disponible = true
        fechaAdquisicion = LocalDate.now()
    }

    return maquina
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