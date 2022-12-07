package models.pedidos

import models.usuarios.Cliente
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type

import java.time.LocalDate
import java.util.UUID
import javax.persistence.*


@Entity
@Table(name = "pedidos")
@NamedQuery(name = "Pedido.findAll", query = "select p from Pedido p")
data class Pedido(
    @Id @GeneratedValue
    @GenericGenerator(
        name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid") @Type(type = "uuid-char")
    val uuid: UUID = UUID.randomUUID(),
    var estado: String,
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    var cliente: Cliente?,
    var fechaEntrada: LocalDate,
    var fechaSalida: LocalDate,
    var fechaFinal: LocalDate,
    var precioTotal: Float,
    var topeEntrega: LocalDate,

    @OneToMany(mappedBy = "idPedido", fetch = FetchType.EAGER)
    var tareas: MutableList<Tarea>
)