import kotlinx.serialization.Serializable
import models.pedidos.Producto

@Serializable
data class ListaProductos(
    val lista: List<Producto>
) {
}