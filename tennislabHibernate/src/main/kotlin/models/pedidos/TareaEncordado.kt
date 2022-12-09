package models.pedidos

import kotlinx.serialization.Serializable

@Serializable
data class TareaEncordado(
    var tensionHorizontal : Int,
    var tensionVertical : Int,
    var cordajeVertical : Producto,
    var cordajeHorizontal : Producto,
    var nNudos : Int
)
