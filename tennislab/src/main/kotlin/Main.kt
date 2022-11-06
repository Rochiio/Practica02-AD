import models.Usuario
import models.Usuarios
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Usuarios)

        create()
        val usuario = read()
        println(usuario)
    }
}

fun create(): Usuario {
    val usuario = Usuario.new {
        nombre = "Nombre"
        apellido = "Apellido"
        email = "Email@email.com"
        password = "1234"
    }
    return usuario
}

fun read(): Usuario {
    val usuario = Usuario.find { Usuarios.nombre eq "Nombre" }
    return usuario.first()
}