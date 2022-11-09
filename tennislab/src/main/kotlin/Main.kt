import config.AppConfig
import db.DataBaseManager
import models.Usuario
import models.Usuarios
import models.enums.tipoUsuario

import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>) {
    initDataBase()
   // Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
//        addLogger(StdOutSqlLogger)
//        SchemaUtils.create(Usuarios)

        create()
        val usuario = read()
        usuario?.let {
            println(it)
        }?: run{
            println("NO EXISTE")
        }

    }
}


fun create(): Usuario {
    val usuario = Usuario.new {
        nombre = "Pepe"
        apellido = "Apellido"
        email = "Email@email.com"
        password = "1234"
        tipo = tipoUsuario.ADMINISTRADOR.toString()
        disponible = true
    }
    return usuario
}

fun read(): Usuario? {
    val usuario = Usuario.find { Usuarios.nombre eq "Pepe" }
    return usuario.firstOrNull()
}


fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}