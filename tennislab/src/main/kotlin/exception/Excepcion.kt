package exception

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal

/**
 * Excepciones personalizadas
 */
sealed interface Excepcion
class UserError(val item: String) : Excepcion
class MaquinaError(val item: String) : Excepcion
class PedidoError(val item: String) : Excepcion

fun log(e: Excepcion){
    var t = Terminal()

    when (e) {
        is UserError -> t.println(red("Error con el usuario ${e.item}"))
        is MaquinaError -> t.println(red("Error con la máquina ${e.item}"))
        is PedidoError -> t.println(red("Error con el pedido ${e.item}"))
    }
}