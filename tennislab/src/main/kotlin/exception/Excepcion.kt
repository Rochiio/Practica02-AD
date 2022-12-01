package exception

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal

/**
 * Excepciones personalizadas
 */
sealed interface Excepcion
class ClienteError(val item: String) : Excepcion, Exception()
class TrabajadorError(val item: String) : Excepcion, Exception()
class MaquinaError(val item: String) : Excepcion, Exception()
class PedidoError(val item: String) : Excepcion, Exception()
class TareaError(val item: String) : Excepcion, Exception()


fun log(e: Excepcion) {
    var t = Terminal()

    when (e) {
        is TrabajadorError -> t.println(red("Error con trabajadores -> ${e.item}"))
        is ClienteError -> t.println(red("Error con clientes -> ${e.item}"))
        is MaquinaError -> t.println(red("Error con máquinas -> ${e.item}"))
        is PedidoError -> t.println(red("Error con pedidos -> ${e.item}"))
        is TareaError -> t.println(red("Error con tareas -> ${e.item}"))
    }
}