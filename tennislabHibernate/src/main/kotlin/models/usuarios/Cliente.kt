package models.usuarios

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/**
 * Modelo de cliente
 */
@Entity
@Table(name = "clientes")
@NamedQuery(name = "Cliente.finAll", query = "select c from Cliente c")
data class Cliente(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID(),
    var nombre:String,
    var apellido:String,
    var email:String,
    var password:String,
) {
}