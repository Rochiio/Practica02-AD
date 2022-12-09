package view.view

import ListaProductos
import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import controller.*
import controllers.ClientesController
import controllers.MaquinasController
import controllers.ProductosController
import controllers.TrabajadoresController
import entities.enums.Estado
import entities.enums.TipoMaquina
import entities.enums.TipoProduct
import entities.enums.TipoTarea
import exception.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.maquinas.Encordador
import models.maquinas.Personalizador
import models.pedidos.*
import models.usuarios.Cliente
import models.usuarios.Trabajador
import utils.Data
import utils.PasswordParser
import view.models.pedidos.TareaAdquisicion
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

/**
 * Vista del usuario.
 */
class Vista(
    private var trabController: TrabajadoresController,
    private var maquinaController: MaquinasController,
    private var clienteController: ClientesController,
    private var pedidosController: PedidosController,
    private var tareasController: TareasController,
    private var productoController: ProductosController
) {
    private var terminal = Terminal(width = 150)
    private var trabajadorLoggeado: Trabajador? = null
    private var clienteLoggeado: Cliente? = null


    /**
     * Funcion principal para el inicio
     */
    fun principal(): Int {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Bienvenido a tennislab🎾🎾 ------ \nelija una opción."))
            terminal.println(
                "1 - Iniciar sesión (Trabajador) \n" +
                        "2 - Iniciar sesión (Cliente)\n" +
                        "0 - Salir"
            )
            opcion = readln().toIntOrNull() ?: 3
        } while (opcion !in 0..2)
        return opcion
    }


    /**
     * Acciones a realizar dependiendo de la respuesta en la funcion principal
     */
    fun opcionesPrincipal(num: Int) {
        when (num) {
            0 -> terminal.println(green.bg("Gracias por usar tennislab 🎾🎾"))
            1 -> loginBucle()
            2 -> loginClienteBucle()
        }
    }

    private fun loginClienteBucle() {
        var email: String
        var correcto: Cliente? = null

        terminal.println(brightBlue("------ Log In Cliente ------"))
        do {
            terminal.print("Correo: ")
            email = readln()
        } while (!email.matches(Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")))

        terminal.print("Contraseña: ")
        val password: String = readln()

        try {
            correcto = clienteController.getClienteByEmailAndPassword(email, password)
        } catch (e: ClienteError) {
            log(e)
        }

        if (correcto != null) {
            clienteLoggeado = correcto
            clienteBucle()
        }
    }

    private fun clienteBucle() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Cliente ------"))
            do {
                terminal.println(
                    "1- Realizar pedido \n" +
                            "2- Comprobar pedidos \n" +
                            "3- Cancelar pedido \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion !in 0..3)
            opcionesClienteBucle(opcion)
        } while (opcion != 0)


    }

    private fun opcionesClienteBucle(opcion: Int) {
        when (opcion) {
            1 -> {
                realizarPedidoBucle()
            }

            2 -> {
                comprobarPedidosBucle()
            }

            3 -> {
                cancelarPedidoBucle()
            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la sesión"))
            }
        }
    }

    private fun cancelarPedidoBucle() {
        terminal.println("Introduce el pedido que quieres eliminar (selecciona con el indice): ")
        val lista = pedidosController.getPedidos().filter { it.cliente == clienteLoggeado }
        getPedidos(lista)
        var indice = readln().toIntOrNull() ?: -1
        if (indice < 0 || indice > lista.size) {
            terminal.println(red("INDICE DE PEDIDO INCORRECTO"))
        } else {
            pedidosController.deletePedido(lista[indice])
        }

    }

    private fun comprobarPedidosBucle() {
        val lista = pedidosController.getPedidos().filter { it.cliente == clienteLoggeado }
        getPedidos(lista)
    }

    private fun realizarPedidoBucle() {
        var opcion: Int
        var pedido: Pedido? = null
        terminal.println(brightBlue("------ Realización de pedido ------"))

        do {
            terminal.println(brightBlue("------ Tipo de tarea ------"))
            do {
                terminal.println(
                    "1- Encordado \n" +
                            "2- Personalizado \n" +
                            "3- Adquisicion \n" +
                            "4- Confirmar pedido\n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleTarea(opcion)
        } while (opcion != 0 && opcion != 4)
    }


    private fun opcionesBucleTarea(opcion: Int) {
        when (opcion) {
            1 -> {
                val tarea = creacionTareaEncordado()
                tareasController.addTarea(tarea)
                Data.tareasCreadas.add(tarea)
            }

            2 -> {
                val tarea = creacionTareaPersonalizado()
                tareasController.addTarea(tarea)
                Data.tareasCreadas.add(tarea)
            }

            3 -> {
                val tarea = createTareaAdquisicion()
                tareasController.addTarea(tarea)
                Data.tareasCreadas.add(tarea)
            }

            4 -> {
                val pedido = creacionPedido(Data.tareasCreadas)
                pedido?.let {
                    Data.pedidosPendientes.add(pedido)
                    pedidosController.addPedido(pedido)
                    terminal.println("Pedido creado")
                }

                getPedidos(pedidosController.getPedidos())

            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la creacion de tareas"))
            }
        }
    }

    private fun creacionPedido(tareas: MutableList<Tarea>): Pedido? {
        var pedido: Pedido? = null
        var precio = 0F
        tareas.forEach { precio += it.precio }
        println(precio)
        pedido = Pedido(

            estado = Estado.RECIBIDO.toString(),
            fechaEntrada = LocalDate.now(),
            fechaSalida = LocalDate.parse("2022-12-31"),
            fechaFinal = LocalDate.parse("2022-12-31").plusDays(14),
            precioTotal = precio,
            topeEntrega = LocalDate.now().plusDays(30),
            cliente = clienteLoggeado,
            tareas = tareas as ArrayList<Tarea>
        )
        Data.tareasCreadas.clear()

        return pedido
    }


    fun creacionTareaEncordado(): Tarea {
        var tarea: Tarea? = null

        terminal.println("Indica la tensión vertical: ")
        val tV = readln().toIntOrNull() ?: -1

        terminal.println("Indica la tensión horizontal: ")
        val tH = readln().toIntOrNull() ?: -1


        var id = -1

        do {
            terminal.println("Indica el cordaje vertical (escribe el indice): ")
            getCordajes()
            terminal.println("ID: ")
            id = readln().toIntOrNull() ?: 0
        } while (id !in (0 until productoController.getAllProductos().size))
        val cV = productoController.getAllProductos()[id]

        do {
            terminal.println("Indica el cordaje horizontal (escribe el indice): ")
            getCordajes()
            terminal.println("ID: ")
            id = readln().toIntOrNull() ?: 0
        } while (id !in (0 until productoController.getAllProductos().size))
        val cH = productoController.getAllProductos()[id]

        terminal.println("Numero de nudos que quieres (2 o 4): ")
        val nudos = readln().toIntOrNull() ?: 2
        val json: Json = Json
        val descripcion = json.encodeToString(TareaEncordado(tH, tV, cV, cH, nudos))

        val precio = cH.precio + cV.precio + 15L
        tarea = Tarea(
            idTrabajador = null,
            idMaquina = null,
            idPedido = null,
            descripcion = descripcion,
            precio = precio.toLong(),
            tipoTarea = TipoTarea.ENCORDADO,
            disponible = true
        )
        tarea.let {
            tareasController.addTarea(tarea)
        }
        return tarea
    }

    fun creacionTareaPersonalizado(): Tarea {
        var tarea: Tarea? = null

        terminal.println("Indica el nuevo peso de la raqueta: ")
        val peso = readln().toIntOrNull() ?: -1

        terminal.println("Indica el nuevo balance de la raqueta: ")
        val balance = readln().toFloatOrNull() ?: -1F

        terminal.println("Indica la nueva rigidez de la raqueta: ")
        var rigidez = readln().toIntOrNull() ?: -1
        val json: Json = Json
        val descripcion = json.encodeToString(TareaPersonalizado(peso, balance, rigidez))

        val precio = 10L
        tarea = Tarea(
            idTrabajador = null,
            idMaquina = null,
            idPedido = null,
            descripcion = descripcion,
            precio = precio.toLong(),
            tipoTarea = TipoTarea.PERSONALIZADO,
            disponible = true
        )
        tarea.let {
            tareasController.addTarea(tarea)
        }
        return tarea
    }


    private fun createTareaAdquisicion(): Tarea {
        var tarea: Tarea? = null
        val productos = mutableListOf<Producto>()
        var repeat: Boolean
        do {
            repeat = false
            terminal.println("Selecciona el producto que vas a comprar (marca el indice):")
            var index: Int
            getProductos()
            index = readln().toIntOrNull() ?: -1
            while (index == -1) {
                terminal.println(red("INDICE INCORRECTO. VUELVE A INTENTARLO"))
                getProductos()
                index = readln().toIntOrNull() ?: -1
            }
            terminal.println("Añadiendo producto al carrito")
            productos.add(productoController.getAllProductos()[index])
            terminal.println("¿Quieres añadir otro producto? (S/N)")
            var opt = readln()
            if (opt.equals("s", ignoreCase = true))
                repeat = true
        } while (repeat)
        var precio = 0F
        productos.forEach { precio += it.precio }
        val json: Json = Json
        val descripcion = json.encodeToString(TareaAdquisicion(ListaProductos(productos), precio))

        tarea = Tarea(
            idTrabajador = null,
            idMaquina = null,
            idPedido = null,
            descripcion = descripcion,
            precio = precio.toLong(),
            tipoTarea = TipoTarea.ADQUISICION,
            disponible = true
        )
        return tarea
    }

    private fun getPedidos(lista: List<Pedido>) {
        //val lista = pedidosController.getPedidos()
        var indice = 0
        if (lista.isEmpty()) {
            println("Lista vacía")
        } else {

            terminal.println(table {

                align = TextAlign.CENTER



                header {
                    style(blue, bold = true)
                    row(
                        "INDICE",
                        "ID",
                        "FECHA ENTRADA",
                        "FECHA SALIDA",
                        "FECHA FINAL",
                        "PRECIO",
                        "TOPE ENTREGA",
                        "CLIENTE"
                    )
                }
                for (pedido in lista) {
                    body {
                        rowStyles(cyan, brightCyan)
                        row(
                            indice,
                            pedido.uuid,
                            pedido.fechaEntrada,
                            pedido.fechaSalida,
                            pedido.fechaFinal,
                            pedido.precioTotal,
                            pedido.topeEntrega,
                            pedido.cliente?.uuid
                        )
                    }

                    indice++
                }
            })

        }
    }

    /**
     * Bucle del login para el logeo del trabajador en su turno.
     */
    private fun loginBucle() {
        var email: String
        var correcto: Trabajador? = null

        terminal.println(brightBlue("------ Log In Usuario ------"))
        do {
            terminal.print("Correo: ")
            email = readln()
        } while (!email.matches(Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")))

        terminal.print("Contraseña: ")
        val password: String = readln()

        try {
            correcto = trabController.getTrabajadorByEmailAndPassword(email, PasswordParser.encriptar(password))
        } catch (e: TrabajadorError) {
            log(e)
        }

        if (correcto != null) {
            trabajadorLoggeado = correcto
            if (correcto.administrador && correcto.disponible) {
                administradorBucle()
            } else if (!correcto.administrador && correcto.disponible) {
                encordadorBucle()
            }
        }
    }


    /**
     * Bucle vista si el usuario es un encordador.
     */
    private fun encordadorBucle() {

    }

    /**
     * Bucle vista si el usuario es un administrador.
     */
    private fun administradorBucle() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Admin ------"))
            do {
                terminal.println(
                    "1- Trabajadores \n" +
                            "2- Clientes \n" +
                            "3- Maquinas \n" +
                            "4- Pedidos \n" +
                            "5- Productos \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion !in 0..5)
            opcionesBucleAdmin(opcion)
        } while (opcion != 0)

    }


    /**
     * Opciones del bucle del administrador
     */
    private fun opcionesBucleAdmin(opcion: Int) {
        when (opcion) {
            1 -> {
                administradorBucleUsuarios()
            }

            2 -> {
                administradorBucleClientes()
            }

            3 -> {
                administradorBucleMaquinas()
            }

            4 -> {}
            5 -> {
                administradorBucleProductos()
            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la sesión"))
            }
        }
    }

//------------------------------------------- PRODUCTOS ------------------------------------------------------------

    /**
     * Bucle de administrador config productos.
     */
    private fun administradorBucleProductos() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Productos Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Producto \n" +
                            "2- Actualizar Producto \n" +
                            "3- Listar Producto \n" +
                            "4- Eliminar Producto \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminProductos(opcion)
        } while (opcion != 0)
    }

    /**
     * Opciones del bucle del administrador con los productos.
     */
    private fun opcionesBucleAdminProductos(opcion: Int) {
        when (opcion) {
            1 -> {
                addProducto()
            }

            2 -> {
                actuProducto()
            }

            3 -> {
                getProductos()
            }

            4 -> {
                eliminarProducto()
            }

            0 -> {
                terminal.println(brightBlue.bg("Saliendo de la configuración de productos🛒🛒"))
            }
        }
    }


    /**
     * Eliminar un producto.
     */
    private fun eliminarProducto() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID del producto a eliminar: ")
            val leer = readln()
            try {
                id = UUID.fromString(leer)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { productoController.getProductoByUUID(it) }
            encontrado?.let { productoController.deleteProducto(encontrado) }
        } catch (e: ProductoError) {
            log(e)
        }

    }


    /**
     * Actualizar producto
     */
    private fun actuProducto() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID del prodcuto a actualizar: ")
            val linea = readln()
            try {
                id = UUID.fromString(linea)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { productoController.getProductoByUUID(it) }
            encontrado?.let {
                val producto = creacionProductos()
                producto.uuid = it.uuid
                productoController.updateProducto(producto)
            }
        } catch (e: ProductoError) {
            log(e)
        }

    }


    /**
     * Conseguir todos los productos
     */
    private fun getProductos() {
        val lista = productoController.getAllProductos()
        mostrarTablaProductos(lista)
    }

    private fun getCordajes() {
        val lista = productoController.getAllProductos().filter { it.tipo == TipoProduct.CORDAJE }
        mostrarTablaProductos(lista)
    }

    private fun mostrarTablaProductos(lista: List<Producto>) {
        var index = 0
        if (lista.isEmpty()) {
            println("Lista vacía")
        } else {
            terminal.println(table {

                align = TextAlign.CENTER



                header {
                    style(blue, bold = true)
                    row("INDEX", "ID", "TIPO", "MARCA", "MODELO", "PRECIO", "STOCK")
                }
                for (prod in lista) {
                    body {
                        rowStyles(cyan, brightCyan)

                        row(
                            index, prod.uuid, prod.tipo, prod.marca, prod.modelo, prod.precio, prod.stock
                        )
                    }
                    index++
                }
            })
        }
    }


    /**
     * Crear el producto
     */
    private fun addProducto() {
        val producto = creacionProductos()
        try {
            productoController.addProducto(producto)
        } catch (e: ProductoError) {
            log(e)
        }
    }


    /**
     * Para crear productos.
     */
    fun creacionProductos(): Producto {
        val listaTipos = TipoProduct.values().map { it.toString() }.toList()

        var tipo: String
        do {
            println("Dime el tipo de producto: RAQUETA, CORDAJE, COMPLEMENTO")
            tipo = readln()
        } while (!listaTipos.contains(tipo))

        println("Dime el marca de producto: ")
        val marca: String = readln()
        println("Dime el modelo del producto: ")
        val modelo: String = readln()

        var precio: Float
        do {
            println("Dime el precio del producto: ")
            precio = readln().toFloatOrNull() ?: -1.0f
        } while (precio < 0.0f)

        var stock: Int
        do {
            println("Dime el stock del producto: ")
            stock = readln().toIntOrNull() ?: -1
        } while (stock <= 0)

        return Producto(
            tipo = TipoProduct.valueOf(tipo),
            marca = marca,
            modelo = modelo,
            precio = precio,
            stock = stock
        )
    }


//-------------------------------------------- CLIENTES ------------------------------------------------------------


    /**
     * Bucle de administrador config clientes.
     */
    private fun administradorBucleClientes() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Clientes Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Cliente \n" +
                            "2- Actualizar Cliente \n" +
                            "3- Listar Cliente \n" +
                            "4- Eliminar Cliente \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminClientes(opcion)
        } while (opcion != 0)
    }


    /**
     * Opciones del bucle del administrador con los clientes.
     */
    private fun opcionesBucleAdminClientes(opcion: Int) {
        when (opcion) {
            1 -> {
                addCliente()
            }

            2 -> {
                actuCliente()
            }

            3 -> {
                getClientes()
            }

            4 -> {
                eliminarCliente()
            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la configuración de clientes👩🏻👨🏻"))
            }
        }
    }


    /**
     * Eliminar un cliente.
     */
    private fun eliminarCliente() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID del cliente a eliminar: ")
            val leer = readln()
            try {
                id = UUID.fromString(leer)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { clienteController.getClienteByUUID(it) }
            encontrado?.let { clienteController.deleteCliente(encontrado) }
        } catch (e: ClienteError) {
            log(e)
        }

    }


    /**
     * Actualizar cliente
     */
    private fun actuCliente() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID del cliente a actualizar: ")
            val linea = readln()
            try {
                id = UUID.fromString(linea)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { clienteController.getClienteByUUID(it) }
            encontrado?.let {
                val usuario = creacionClientes()
                usuario.uuid = it.uuid
                clienteController.updateCliente(usuario)
            }
        } catch (e: ClienteError) {
            log(e)
        }

    }


    /**
     * Conseguir todos los clientes
     */
    private fun getClientes() {
        val lista = clienteController.getAllClientes()
        if (lista.isEmpty()) {
            println("Lista vacía")
        } else {

            terminal.println(table {

                align = TextAlign.CENTER



                header {
                    style(blue, bold = true)
                    row("ID", "NOMBRE", "APELLIDO", "EMAIL")
                }
                for (cli in lista) {
                    body {
                        rowStyles(cyan, brightCyan)
                        row(
                            cli.uuid, cli.nombre, cli.apellido, cli.email
                        )
                    }
                }
            })

        }
    }


    /**
     * Crear el cliente
     */
    private fun addCliente() {
        val usuario = creacionClientes()
        try {
            clienteController.addCliente(usuario)
        } catch (e: ClienteError) {
            log(e)
        }
    }


    /**
     * Para crear clientes.
     */
    private fun creacionClientes(): Cliente {
        var nombre: String
        do {
            print("Nombre usuario: ")
            nombre = readln()
        } while (nombre.isEmpty())

        var apellido: String
        do {
            print("Apellido usuario: ")
            apellido = readln()
        } while (apellido.isEmpty())

        var email: String
        do {
            print("Correo usuario: ")
            email = readln()
        } while (!email.matches(Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")))

        var password: String
        do {
            print("Contraseña usuario: ")
            password = readln()
        } while (password.isEmpty())


//se crea una lista de pedidos vacía

        return Cliente(
            nombre = nombre,
            apellido = apellido,
            email = email,
            password = PasswordParser.encriptar(password),
            pedido = mutableListOf()
        )
    }


//------------------------------------------- MAQUINAS -------------------------------------------------------------

    /**
     * Bucle para elegir que máquinas queremos ver.
     */
    private fun administradorBucleMaquinas() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Máquinas Admin ------"))
            do {
                terminal.println(
                    "1- Encordadoras \n" +
                            "2- Personalizadoras \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminMaquinas(opcion)
        } while (opcion != 0)
    }


    /**
     * Opciones del bucle de administrador.
     */
    private fun opcionesBucleAdminMaquinas(opcion: Int) {
        when (opcion) {
            1 -> bucleEncordadorasAdmin()
            2 -> buclePersonalizadorasAdmin()
            0 -> terminal.println(blue.bg("Saliendo de la configuración de máquinas"))
        }

    }


//-------------------------------------- PERSONALIZADORAS ----------------------------------------------------------


    /**
     * Bucle personalizadoras.
     */
    private fun buclePersonalizadorasAdmin() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Personalizadoras Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Personalizadora \n" +
                            "2- Actualizar Personalizadora \n" +
                            "3- Listar Personalizadoras \n" +
                            "4- Eliminar Personalizadora \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminPersonalizadoras(opcion)
        } while (opcion != 0)
    }

    private fun opcionesBucleAdminPersonalizadoras(opcion: Int) {
        when (opcion) {
            1 -> {
                addPersonalizadora()
            }

            2 -> {
                actuPersonalizadora()
            }

            3 -> {
                getPersonalizadoras()
            }

            4 -> {
                eliminarPersonalizadora()
            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la configuración de personalizadoras🎾🎾"))
            }
        }
    }


    /**
     * Eliminar una personalizadora
     */
    private fun eliminarPersonalizadora() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID de la personalizadora a eliminar: ")
            val leer = readln()
            try {
                id = UUID.fromString(leer)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { maquinaController.getPersonalizadoraByUUID(it) }
            encontrado?.let { maquinaController.deletePersonalizadora(it) }
        } catch (e: MaquinaError) {
            log(e)
        }
    }


    /**
     * Ver todas las personalizadoras.
     */
    private fun getPersonalizadoras() {
        val lista = maquinaController.getAllPersonalizadoras()
        if (lista.isEmpty()) {
            println("Lista vacía")
        } else {
            terminal.println(table {

                align = TextAlign.CENTER



                header {
                    style(blue, bold = true)
                    row("ID", "MODELO", "MARCA", "FECHA ADQUISICION", "MANIOBRABILIDAD", "BALANCE", "RIGIDEZ")
                }
                for (pers in lista) {
                    body {
                        rowStyles(cyan, brightCyan)

                        row(
                            pers.uuid,
                            pers.modelo,
                            pers.marca,
                            pers.fechaAdquisicion,
                            pers.maniobrabilidad,
                            pers.balance,
                            pers.rigidez
                        )
                    }
                }
            })
        }
    }


    /**
     * Actualizar una personalizadora.
     */
    private fun actuPersonalizadora() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID de la personalizadora a actualizar: ")
            val linea = readln()
            try {
                id = UUID.fromString(linea)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { maquinaController.getPersonalizadoraByUUID(it) }
            encontrado?.let {
                val personalizadora = creacionPersonalizadora()
                personalizadora.uuid = it.uuid
                maquinaController.updatePersonalizadora(personalizadora)
            }
        } catch (e: MaquinaError) {
            log(e)
        }

    }

    /**
     * Añadir una personalizadora.
     */
    private fun addPersonalizadora() {
        val personalizadora = creacionPersonalizadora()
        try {
            maquinaController.addPersonalizadora(personalizadora)
        } catch (e: MaquinaError) {
            log(e)
        }
    }


    /**
     * Creacion de una personalizadora preguntando al usuario.
     */
    private fun creacionPersonalizadora(): Personalizador {
        var marca: String
        do {
            print("Marca personalizadora: ")
            marca = readln()
        } while (marca.isEmpty())
        var modelo: String
        do {
            print("Modelo personalizadora: ")
            modelo = readln()
        } while (modelo.isEmpty())
        var fecha: String
        do {
            print("Fecha adquisición personalizadora dd-MM-yyyy: ")
            fecha = readln()
        } while (!fecha.matches(Regex("^([0-2][0-9]|3[0-1])(-)(0[1-9]|1[0-2])\\2(\\d{4})\$")))
        var maniobrabilidad: String
        do {
            print("Maniobrabilidad personalizadora SI/NO: ")
            maniobrabilidad = readln()
        } while (maniobrabilidad != "SI" && maniobrabilidad != "NO")
        var balance: String
        do {
            print("Balance personalizadora SI/NO: ")
            balance = readln()
        } while (balance != "SI" && balance != "NO")
        var rigidez: String
        do {
            print("Rigidez personalizadora SI/NO: ")
            rigidez = readln()
        } while (rigidez != "SI" && rigidez != "NO")


        val campos = fecha.split("-")
        return Personalizador(
            UUID.randomUUID(), marca, modelo, LocalDate.of(campos[2].toInt(), campos[1].toInt(), campos[0].toInt()),
            true, maniobrabilidad == "SI", balance == "SI", rigidez == "SI"
        )
    }


//--------------------------------------------- ENCORDADORAS -------------------------------------------------------


    /**
     * Bucle encordadoras.
     */
    private fun bucleEncordadorasAdmin() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Encordadoras Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Encordadora \n" +
                            "2- Actualizar Encordadora \n" +
                            "3- Listar Encordadoras \n" +
                            "4- Eliminar Encordadora \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminEncordadoras(opcion)
        } while (opcion != 0)
    }

    private fun opcionesBucleAdminEncordadoras(opcion: Int) {
        when (opcion) {
            1 -> {
                addEncordadora()
            }

            2 -> {
                actuEncordadora()
            }

            3 -> {
                getEncordadoras()
            }

            4 -> {
                eliminarEncordadora()
            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la configuración de encordadoras🎾🎾"))
            }
        }
    }


    /**
     * Eliminar una encordadora
     */
    private fun eliminarEncordadora() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID de la encordadora a eliminar: ")
            val leer = readln()
            try {
                id = UUID.fromString(leer)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { maquinaController.getEncordadoraByUUID(it) }
            encontrado?.let { maquinaController.deleteEncordadora(it) }
        } catch (e: MaquinaError) {
            log(e)
        }
    }


    /**
     * Ver todas las encordadoras.
     */
    private fun getEncordadoras() {
        val lista = maquinaController.getAllEncordadoras()
        if (lista.isEmpty()) {
            println("Lista vacía")
        } else {
            terminal.println(table {

                align = TextAlign.CENTER



                header {
                    style(blue, bold = true)
                    row("ID", "MODELO", "MARCA", "FECHA ADQUISICION", "AUTOMATICA", "TENSION MAXIMA", "TENSION MINIMA")
                }
                for (trab in lista) {
                    body {
                        rowStyles(cyan, brightCyan)

                        row(
                            trab.uuid,
                            trab.modelo,
                            trab.marca,
                            trab.fechaAdquisicion,
                            if (trab.automatico == TipoMaquina.AUTOMATICA) "✅" else "❌",
                            trab.tensionMaxima,
                            trab.tensionMinima,

                            )
                    }
                }
            })
        }
    }


    /**
     * Actualizar una encordadora.
     */
    private fun actuEncordadora() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID de la encordadora a actualizar: ")
            val linea = readln()
            try {
                id = UUID.fromString(linea)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { maquinaController.getEncordadoraByUUID(it) }
            encontrado?.let {
                val encordadora = creacionEncordadora()
                encordadora.uuid = it.uuid
                maquinaController.updateEncordadora(encordadora)
            }
        } catch (e: MaquinaError) {
            log(e)
        }

    }

    /**
     * Añadir una encordadora.
     */
    private fun addEncordadora() {
        val encordadora = creacionEncordadora()
        try {
            maquinaController.addEncordadora(encordadora)
        } catch (e: MaquinaError) {
            log(e)
        }
    }


    /**
     * Creacion de una encordadora preguntando al usuario.
     */
    private fun creacionEncordadora(): Encordador {
        var marca: String
        do {
            print("Marca encordadora: ")
            marca = readln()
        } while (marca.isEmpty())
        var modelo: String
        do {
            print("Modelo encordadora: ")
            modelo = readln()
        } while (modelo.isEmpty())
        var fecha: String
        do {
            print("Fecha adquisición encordadora dd-MM-yyyy: ")
            fecha = readln()
        } while (!fecha.matches(Regex("^([0-2][0-9]|3[0-1])(-)(0[1-9]|1[0-2])\\2(\\d{4})\$")))
        var tipo: String
        do {
            print("Tipo de automaticidad AUTOMATICA/MANUAL: ")
            tipo = readln()
        } while (tipo != "AUTOMATICA" && tipo != "MANUAL")
        var tensionMin: Int
        do {
            print("Tensión mínima encordadora: ")
            tensionMin = readln().toIntOrNull() ?: 0
        } while (tensionMin <= 0)
        var tensionMax: Int
        do {
            print("Tensión máxima encordadora: ")
            tensionMax = readln().toIntOrNull() ?: 0
        } while (tensionMax <= 0)

        val campos = fecha.split("-")
        return Encordador(
            UUID.randomUUID(),
            marca,
            modelo,
            LocalDate.of(campos[2].toInt(), campos[1].toInt(), campos[0].toInt()),
            true,
            TipoMaquina.valueOf(tipo),
            tensionMax,
            tensionMin
        )
    }


//----------------------------------------- TRABAJADORES -----------------------------------------------------------


    /**
     * Bucle vista si el usuario es un administrador y selecciona los trabajadores.
     */
    private fun administradorBucleUsuarios() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Trabajadores Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Trabajador \n" +
                            "2- Actualizar Trabajador \n" +
                            "3- Listar Trabajadores \n" +
                            "4- Eliminar trabajador \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminUsuarios(opcion)
        } while (opcion != 0)
    }


    /**
     * Opciones bucle de conf de trabajadores del administrador.
     */
    private fun opcionesBucleAdminUsuarios(opcion: Int) {
        when (opcion) {
            1 -> {
                addTrabajador()
            }

            2 -> {
                actuTrabajador()
            }

            3 -> {
                getTrabajadores()
            }

            4 -> {
                eliminarTrabajador()
            }

            0 -> {
                terminal.println(blue.bg("Saliendo de la configuración de trabajadores👩🏻👨🏻"))
            }
        }
    }


    /**
     * Eliminar un trabajador.
     */
    private fun eliminarTrabajador() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID del trabajador a eliminar: ")
            val leer = readln()
            try {
                id = UUID.fromString(leer)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { trabController.getTrabajadorByUUID(it) }
            encontrado?.let { trabController.deleteTrabajador(encontrado) }
        } catch (e: TrabajadorError) {
            log(e)
        }

    }

    /**
     * Actualizar trabajador
     */
    private fun actuTrabajador() {
        var id: UUID? = null
        var correcto = false

        do {
            print("Dime el UUID del trabajador a actualizar: ")
            val linea = readln()
            try {
                id = UUID.fromString(linea)
                correcto = true
            } catch (e: Exception) {
                !correcto
            }

        } while (!correcto)

        try {
            val encontrado = id?.let { trabController.getTrabajadorByUUID(it) }
            encontrado?.let {
                val usuario = creacionTrabajadores()
                usuario.uuid = it.uuid
                trabController.updateTrabajador(usuario)
            }
        } catch (e: TrabajadorError) {
            log(e)
        }

    }


    /**
     * Conseguir todos los trabajadores
     */
    private fun getTrabajadores() {
        val lista = trabController.getAllTrabajadores()
        if (lista.isEmpty()) {
            println("Lista vacía")
        } else {


            terminal.println(table {

                align = TextAlign.CENTER



                header {
                    style(blue, bold = true)
                    row("ID", "NOMBRE", "APELLIDO", "EMAIL", "ADMINISTRADOR")
                }
                for (trab in lista) {
                    body {
                        rowStyles(cyan, brightCyan)

                        row(
                            trab.uuid, trab.nombre, trab.apellido, trab.email,
                            if (trab.administrador) "✅" else "❌"
                        )
                    }
                }
            })
        }
    }


    /**
     * Crear el trabajador
     */
    private fun addTrabajador() {
        val usuario = creacionTrabajadores()
        try {
            trabController.addTrabajador(usuario)
        } catch (e: TrabajadorError) {
            log(e)
        }
    }


    /**
     * Para crear trabajadores.
     */
    fun creacionTrabajadores(): Trabajador {
        var nombre: String
        do {
            print("Nombre usuario: ")
            nombre = readln()
        } while (nombre.isEmpty())

        var apellido: String
        do {
            print("Apellido usuario: ")
            apellido = readln()
        } while (apellido.isEmpty())

        var email: String
        do {
            print("Correo usuario: ")
            email = readln()
        } while (!email.matches(Regex("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")))

        var password: String
        do {
            print("Contraseña usuario: ")
            password = readln()
        } while (password.isEmpty())

        var respuesta: String
        val admin: Boolean
        do {
            print("Administrador (S/N)")
            respuesta = readln()
        } while (respuesta != "S" && respuesta != "N")
        admin = respuesta == "S"


        return Trabajador(
            UUID.randomUUID(), nombre, apellido, email, PasswordParser.encriptar(password), true, admin,
            mutableListOf()
        )
    }


//---------------------------------PEDIDOS------------------------------------------------------

    private fun administradorBuclePedidos() {
        var opcion: Int
        do {
            terminal.println(brightBlue("------ Pedidos Admin ------"))
            do {
                terminal.println(
                    "1- Añadir Pedido \n" +
                            "2- Actualizar Pedido \n" +
                            "3- Listar Cliente \n" +
                            "4- Eliminar Cliente \n" +
                            "0- Salir"
                )
                opcion = readln().toIntOrNull() ?: -1
            } while (opcion < 0 || opcion > 4)
            opcionesBucleAdminClientes(opcion)
        } while (opcion != 0)
    }
}