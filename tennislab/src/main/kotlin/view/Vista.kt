package view

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import controller.MaquinasController
import controller.TrabajadoresController
import entities.enums.TipoMaquina
import exception.MaquinaError
import exception.TrabajadorError
import exception.log
import models.maquinas.Encordador
import models.usuarios.Trabajador
import utils.PasswordParser
import java.time.LocalDate
import java.util.*

/**
 * Vista del usuario.
 */
class Vista(
    private var trabController: TrabajadoresController,
    private var maquinaController: MaquinasController
) {
    private var terminal = Terminal()


    /**
     * Funcion principal para el inicio
     */
    fun principal():Int{
        var opcion:Int
            do{
                terminal.println(brightBlue("------ Bienvenido a tennislab🎾🎾 ------ \nelija una opción."))
                terminal.println("1- Iniciar sesión \n" +
                                        "0- Salir")
                opcion = readln().toIntOrNull()?: 2
            }while(opcion !in 0.. 1)
        return opcion
    }


    /**
     * Acciones a realizar dependiendo de la respuesta en la funcion principal
     */
    fun opcionesPrincipal(num: Int) {
        when(num){
            0 -> terminal.println(green.bg("Gracias por usar tennislab 🎾🎾"))
            1 -> loginBucle()
        }
    }


    /**
     * Bucle del login para el logeo del trabajador en su turno.
     */
    private fun loginBucle() {
        var email: String
        var correcto: Trabajador? = null

            terminal.println(brightBlue("------ Log In Usuario ------"))
                do {
                    terminal.print("Correo: ")
                    email = readln()
                }while (!email.matches(Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")))

            terminal.print("Contraseña: ")
        val password: String = readln()

            try {
                 correcto = trabController.getTrabajadorByEmailAndPassword(email,PasswordParser.encriptar(password))
            }catch (e: TrabajadorError){
                log(e)
            }

            if (correcto!=null){
                if (correcto.administrador && correcto.disponible){
                    administradorBucle()
                }else if(!correcto.administrador && correcto.disponible){
                    encordadorBucle()
                }
            }
    }


    /**
     * Bucle vista si el usuario es un encordador.
     */
    private fun encordadorBucle(){

    }

    /**
     * Bucle vista si el usuario es un administrador.
     */
    private fun administradorBucle(){
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Admin ------"))
            do {
                terminal.println(
                    "1- Trabajadores \n" +
                            "2- Maquinas \n" +
                            "3- Pedidos \n" +
                            "4- Productos \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion !in 0.. 4)
            opcionesBucleAdmin(opcion)
        }while(opcion!=0)

    }


    /**
     * Opciones del bucle del administrador
     */
    private fun opcionesBucleAdmin(opcion: Int) {
        when(opcion){
            1 ->{administradorBucleUsuarios()}
            2 ->{administradorBucleMaquinas()}
            3 ->{}
            4 ->{}
            0 ->{terminal.println(brightBlue.bg("Saliendo de la sesión"))
            }
        }
    }


    //------------------------------------------- MAQUINAS -------------------------------------------------------------

    /**
     * Bucle para elegir que máquinas queremos ver.
     */
    private fun administradorBucleMaquinas() {
        var opcion: Int
        do{
            terminal.println(brightBlue("------ Máquinas Admin ------"))
            do {
                terminal.println(
                    "1- Encordadoras \n" +
                            "2- Personalizadoras \n"+
                            "0- Salir")
                opcion= readln().toIntOrNull() ?: -1
            }while (opcion<0 || opcion>4)
            opcionesBucleAdminMaquinas(opcion)
        }while (opcion!=0)
    }


    /**
     * Opciones del bucle de administrador.
     */
    private fun opcionesBucleAdminMaquinas(opcion: Int) {
        when(opcion){
            1 -> bucleEncordadorasAdmin()
            2 -> buclePersonalizadorasAdmin()
            0 -> terminal.println(brightBlue.bg("Saliendo de la configuración de máquinas"))
        }

    }


    /**
     * Bucle personalizadoras.
     */
    private fun buclePersonalizadorasAdmin() {
        TODO("Not yet implemented")
    }


    //--------------------------------------------- ENCORDADORAS -------------------------------------------------------


    /**
     * Bucle encordadoras.
     */
    private fun bucleEncordadorasAdmin() {
        var opcion: Int
        do{
            terminal.println(brightBlue("------ Encordadoras Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Encordadora \n" +
                            "2- Actualizar Encordadora \n"+
                            "3- Listar Encordadoras \n"+
                            "4- Eliminar Encordadora \n"+
                            "0- Salir")
                opcion= readln().toIntOrNull() ?: -1
            }while (opcion<0 || opcion>4)
            opcionesBucleAdminEncordadoras(opcion)
        }while (opcion!=0)
    }

    private fun opcionesBucleAdminEncordadoras(opcion: Int) {
        when(opcion){
            1 ->{addEncordadora()}
            2 ->{actuEncordadora()}
            3 ->{getEncordadoras()}
            4 ->{eliminarEncordadora()}
            0 ->{terminal.println(brightBlue.bg("Saliendo de la configuración de encordadoras"))
            }
        }
    }


    /**
     * Eliminar una encordadora
     */
    private fun eliminarEncordadora() {
        var id: UUID? = null
        var correcto=false

        do {
            print("Dime el UUID de la encordadora a eliminar: ")
            val leer = readln()
            try {
                id =UUID.fromString(leer)
                correcto=true
            }catch (e: Exception){
                !correcto
            }

        }while(!correcto)

        try {
            val encontrado = id?.let { maquinaController.getEncordadoraByUUID(it) }
            encontrado?.let{ maquinaController.deleteEncordadora(it) }
        }catch (e: TrabajadorError){
            log(e)
        }
    }


    /**
     * Ver todas las encordadoras.
     */
    private fun getEncordadoras() {
        val lista = maquinaController.getAllEncordadoras()
        if (lista.isEmpty()){
            println("Lista vacía")
        }else{
            for (enc in lista){
                terminal.println(green(enc.toString()))
            }
        }
    }


    /**
     * Actualizar una encordadora.
     */
    private fun actuEncordadora() {
        var id: UUID? = null
        var correcto=false

        do {
            print("Dime el UUID de la encordadora a actualizar: ")
            val linea = readln()
            try {
                id = UUID.fromString(linea)
                correcto=true
            }catch (e: Exception){
                !correcto
            }

        }while(!correcto)

        try {
            val encontrado = id?.let { maquinaController.getEncordadoraByUUID(it) }
            encontrado?.let {
                var encordadora = creacionEncordadora()
                encordadora.uuid = it.uuid
                maquinaController.updateEncordadora(encordadora)
            }
        }catch (e: TrabajadorError){
            log(e)
        }

    }

    /**
     * Añadir una encordadora.
     */
    private fun addEncordadora() {
        val encordadora = creacionEncordadora()
        try {
            maquinaController.addEncordadora(encordadora)
        }catch (e: MaquinaError){
            log(e)
        }
    }


    /**
     * Creacion de una encordadora preguntando al usuario.
     */
    private fun creacionEncordadora(): Encordador {
        var marca:String
        do {
            print("Marca encordadora: ")
            marca = readln()
        }while (marca.isEmpty())
        var modelo:String
        do {
            print("Modelo encordadora: ")
            modelo = readln()
        }while (modelo.isEmpty())
        var fecha:String
        do {
            print("Fecha adquisición encordadora dd-MM-yyyy: ")
            fecha=readln()
        }while (!fecha.matches(Regex("^([0-2][0-9]|3[0-1])(-)(0[1-9]|1[0-2])\\2(\\d{4})\$")))
        var tipo:String
        do {
            print("Tipo de automaticidad AUTOMATICA/MANUAL: ")
            tipo= readln()
        }while (tipo != "AUTOMATICA" && tipo != "MANUAL")
        var tensionMin:Int
        do {
            print("Tensión mínima encordadora: ")
            tensionMin= readln().toIntOrNull()?:0
        }while (tensionMin<=0)
        var tensionMax:Int
        do {
            print("Tensión máxima encordadora: ")
            tensionMax= readln().toIntOrNull()?:0
        }while (tensionMax<=0)

        var campos = fecha.split("-")
        return Encordador(null,marca,modelo, LocalDate.of(campos[2].toInt(),campos[1].toInt(),campos[0].toInt()),true, TipoMaquina.valueOf(tipo),tensionMax,tensionMin)
    }


    //----------------------------------------- TRABAJADORES -----------------------------------------------------------


    /**
     * Bucle vista si el usuario es un administrador y selecciona los trabajadores.
     */
    private fun administradorBucleUsuarios(){
        var opcion: Int
        do{
            terminal.println(brightBlue("------ Trabajadores Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Trabajador \n" +
                        "2- Actualizar Trabajador \n"+
                        "3- Listar Trabajadores \n"+
                        "4- Eliminar trabajador \n"+
                        "0- Salir")
                opcion= readln().toIntOrNull() ?: -1
            }while (opcion<0 || opcion>4)
            opcionesBucleAdminUsuarios(opcion)
        }while (opcion!=0)
    }


    /**
     * Opciones bucle de conf de trabajadores del administrador.
     */
    private fun opcionesBucleAdminUsuarios(opcion: Int) {
        when(opcion){
            1 ->{addTrabajador()}
            2 ->{actuTrabajador()}
            3 ->{getTrabajadores()}
            4 ->{eliminarTrabajador()}
            0 ->{terminal.println(brightBlue.bg("Saliendo de la configuración de trabajadores"))
            }
        }
    }


    /**
     * Eliminar un trabajador.
     */
    private fun eliminarTrabajador() {
        var id: UUID? = null
        var correcto=false

        do {
            print("Dime el UUID del trabajador a eliminar: ")
            val leer = readln()
            try {
                id =UUID.fromString(leer)
                correcto=true
            }catch (e: Exception){
                !correcto
            }

        }while(!correcto)

        try {
            val encontrado = id?.let { trabController.getTrabajadorByUUID(it) }
            encontrado?.let{ trabController.deleteTrabajador(encontrado) }
        }catch (e: TrabajadorError){
            log(e)
        }

    }

    /**
     * Actualizar trabajador
     */
    private fun actuTrabajador() {
        var id: UUID? = null
        var correcto=false

            do {
                print("Dime el UUID del trabajador a actualizar: ")
                val linea = readln()
                try {
                    id = UUID.fromString(linea)
                    correcto=true
                }catch (e: Exception){
                    !correcto
                }

            }while(!correcto)

        try {
            val encontrado = id?.let { trabController.getTrabajadorByUUID(it) }
            encontrado?.let {
                var usuario = creacionTrabajadores()
                usuario.uuid = it.uuid
                trabController.updateTrabajador(usuario)
            }
        }catch (e: TrabajadorError){
            log(e)
        }

    }


    /**
     * Conseguir todos los trabajadores
     */
    private fun getTrabajadores() {
        val lista = trabController.getAllTrabajadores()
        if (lista.isEmpty()){
            println("Lista vacía")
        }else{
            for (trab in lista){
                terminal.println(green(trab.toString()))
            }
        }
    }


    /**
     * Crear el trabajador
     */
    private fun addTrabajador() {
       val usuario = creacionTrabajadores()
        try {
            trabController.addTrabajador(usuario)
        }catch (e: TrabajadorError){
            log(e)
        }
    }


    /**
     * Para crear trabajadores.
     */
    fun creacionTrabajadores():Trabajador{
        var nombre:String
        do {
            print("Nombre usuario: ")
            nombre = readln()
        }while (nombre.isEmpty())

        var apellido:String
        do {
            print("Apellido usuario: ")
            apellido = readln()
        }while (apellido.isEmpty())

        var email:String
        do {
            print("Correo usuario: ")
            email = readln()
        }while (!email.matches(Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")))

        var password:String
        do {
            print("Contraseña usuario: ")
            password = readln()
        }while (password.isEmpty())

        var respuesta:String
        val admin: Boolean
        do {
            print("Administrador (S/N)")
            respuesta = readln()
        }while (respuesta!="S" && respuesta!="N")
        admin = respuesta=="S"


        return Trabajador(null, null,nombre, apellido, email, PasswordParser.encriptar(password), true, admin)
    }





}