package models.pedidos

import kotlinx.serialization.Serializable
import models.usuarios.Cliente
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import utils.serializer.LocalDateSerializer
import utils.serializer.UUIDSerializer

import java.time.LocalDate
import java.util.UUID
import javax.persistence.*

@Serializable
@Entity
@Table(name = "pedidos")
@NamedQuery(name = "Pedido.findAll", query = "select p from Pedido p")
data class Pedido(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid") @Type(type = "uuid-char")
    @Serializable(with = UUIDSerializer::class)
    val uuid: UUID = UUID.randomUUID(),
    var estado: String,
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    var cliente: Cliente?,
    @Serializable(with = LocalDateSerializer::class)

    var fechaEntrada: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    var fechaSalida: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    var fechaFinal: LocalDate,
    var precioTotal: Float,
    @Serializable(with = LocalDateSerializer::class)
    var topeEntrega: LocalDate,

    @OneToMany(mappedBy = "idPedido", fetch = FetchType.EAGER)
    var tareas: MutableList<Tarea>
)