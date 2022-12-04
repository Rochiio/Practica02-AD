package models.pedidos

import kotlinx.serialization.Serializable


@Serializable
data class TareaAdquisicion(
    var productos: ListaProductos,
    var precio: Float
)
