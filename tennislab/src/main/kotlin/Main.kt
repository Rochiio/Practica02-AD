import config.AppConfig
import db.DataBaseManager
import models.*
import models.maquinas.Personalizadora
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.util.UUID


fun main(args: Array<String>) {
    //initDataBase()
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    lateinit var m : Maquina
    transaction {
        SchemaUtils.create(Maquinas, Encordadoras)
        println("TRANSACCION 1")
        m = create()

        println(m)

        createEncordadora(m)

        println("---------------------------")
        println(read())
        println(maquinaById(m))
    }


}


fun create(): Maquina {
    val encordador = Maquina.new {
        modelo = "Encordadora1"
        fechaAdquisicion = LocalDate.now()
        disponible = true
    }
    return encordador
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