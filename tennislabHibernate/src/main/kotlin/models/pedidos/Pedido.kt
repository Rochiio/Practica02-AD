package models.pedidos

import models.usuarios.Cliente
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import javax.persistence.Table

import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedQuery
import javax.persistence.OneToMany


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
    @JoinColumn(name = "cliente_id", nullable = false)
    var cliente: Cliente,
    var fechaEntrada: LocalDate,
    var fechaSalida: LocalDate,
    var fechaFinal: LocalDate,
    var precioTotal: Float,
    var topeEntrega: LocalDate,

    @OneToMany(mappedBy = "pedido")
    var tareas: MutableList<Tarea>
)