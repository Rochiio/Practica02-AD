import config.AppConfig
import controller.MaquinasController
import controller.TrabajadoresController
import db.DataBaseManager
import entities.EncordadorDAO
import entities.EncordadorTable
import entities.maquinas.PersonalizadorDAO
import entities.usuarios.TrabajadorDAO
import entities.usuarios.TrabajadorTable
import models.maquinas.Encordador
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import repositories.maquinas.EncordadoRepositoryImpl
import repositories.maquinas.PersonalizadoraRepositoryImpl
import repositories.usuarios.TrabajadorRepositoryImpl
import utils.PasswordParser
import view.Vista


fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction{
        SchemaUtils.create(TrabajadorTable, EncordadorTable)

        TrabajadorDAO.new {
            nombre = "Pepe"
            apellido="apellido"
            email = "pepe@gmail.com"
            password= PasswordParser.encriptar("1234")
            disponible = true
            administrador = true
        }

        var vista= Vista(TrabajadoresController(TrabajadorRepositoryImpl(TrabajadorDAO)),
            MaquinasController(EncordadoRepositoryImpl(EncordadorDAO), PersonalizadoraRepositoryImpl(PersonalizadorDAO)))

        do {
            var num = vista.principal()
            vista.opcionesPrincipal(num)
        }while (num!=0)
    }



}


fun initDataBase() {
    val appConfig = AppConfig.fromPropertiesFile("src/main/resources/config.properties")
    println("Configuración: $appConfig")

    // Iniciamos la base de datos con la configuracion que hemos leido
    DataBaseManager.init(appConfig)

}