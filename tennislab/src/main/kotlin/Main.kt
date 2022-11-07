import models.Usuario
import models.Usuarios
import models.enums.tipoUsuario
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Usuarios)

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