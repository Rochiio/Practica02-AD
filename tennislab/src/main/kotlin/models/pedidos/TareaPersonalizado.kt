package models.pedidos

import kotlinx.serialization.Serializable

@Serializable
data class TareaPersonalizado(
    var pero : Int,
    var balance : Float,
    var rigidez : Int
)
