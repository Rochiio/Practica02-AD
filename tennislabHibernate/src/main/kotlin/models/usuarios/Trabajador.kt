package models.usuarios

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "trabajadores")
@NamedQuery(name ="Trabajado.findAll", query = "select t from Trabajador t")
data class Trabajador(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid:UUID = UUID.randomUUID(),
    var nombre:String,
    var apellido:String,
    var email:String,
    var password:String,
    var disponible:Boolean,
    var administrador: Boolean
) {
}