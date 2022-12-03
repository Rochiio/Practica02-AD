package models.pedidos

import entities.enums.TipoProduct
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/**
 * Modelo de productos
 */
@Entity
@Table(name = "productos")
@NamedQuery(name = "Producto.findAll", query = "select p from Producto p")
data class Producto(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID(),
    var tipo: TipoProduct,
    var marca: String,
    var modelo: String,
    var precio: Float,
    var stock: Int
) {
}