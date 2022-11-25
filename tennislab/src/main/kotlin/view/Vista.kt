package view

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import controller.TrabajadoresController
import exception.TrabajadorError
import exception.log
import models.usuarios.Trabajador
import utils.PasswordParser
import java.util.*

/**
 * Vista del usuario.
 */
class Vista(
    private var trabController: TrabajadoresController
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
            2 ->{}
            3 ->{}
            4 ->{}
            0 ->{terminal.println(brightBlue.bg("Saliendo de la sesión"))
            }
        }
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
            val usuario = creacionTrabajadores()
            usuario.id = encontrado?.id
            trabController.updateTrabajador(usuario)
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


        return Trabajador(null, null, nombre, apellido, email, PasswordParser.encriptar(password), true, admin)
    }





}