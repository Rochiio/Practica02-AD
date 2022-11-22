package view

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import controller.TrabajadoresController
import exception.TrabajadorError
import exception.log
import models.usuarios.Trabajador
import utils.PasswordParser

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
            }while(opcion in 1.. 0)
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
        var password: String = readln()

            try {
                 correcto = trabController.getTrabajadorByEmailAndPassword(email,PasswordParser.encriptar(password))
            }catch (e: TrabajadorError){
                log(e)
            }

            if (correcto!=null){

            }else{
                principal()
            }
    }


}