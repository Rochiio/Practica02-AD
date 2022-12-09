package models.usuarios

import kotlinx.serialization.Serializable
import models.pedidos.Pedido
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.UUIDSerializer
import java.util.*
import javax.persistence.*

/**
 * Modelo de cliente
 */
@Serializable
@Entity
@Table(name = "clientes")
@NamedQueries(
    NamedQuery(name ="Cliente.findAll", query = "select c from Cliente c"),
    NamedQuery(name ="Cliente.findByEmail", query="select c from Cliente c where c.email = :email")
)data class Cliente(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID = UUID.randomUUID(),
    var nombre:String,
    var apellido:String,
    var email:String,
    var password:String,
    @OneToMany(mappedBy = "cliente")
    var pedido : MutableList<Pedido>
) {
}