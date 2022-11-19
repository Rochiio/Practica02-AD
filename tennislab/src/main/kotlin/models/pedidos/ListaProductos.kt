package models.pedidos

import kotlinx.serialization.Serializable

/**
 * Listado de productos y servicios ofrecidos, Serializable para pasar a JSON.
 */

//TODO en el enunciado pone lo siguiente -> Listado de productos y servicios que ofrecemos en JSON.
@Serializable
data class ListaProductos(
    val lista: List<Producto>
) {
}