package models.pedidos

import kotlinx.serialization.Serializable


//TODO en el enunciado pone lo siguiente -> Listado de productos y servicios que ofrecemos en JSON.
@Serializable
data class ListaProductos(
    val lista: List<Producto>
) {
}