package entities.enums


//enum class TipoUsuario {
//    ADMINISTRADOR,TRABAJADOR,CLIENTE
//}

/**
 * Tipos de máquinas que hay.
 */
enum class TipoMaquina{
    AUTOMATICA, MANUAL
}


/**
 * Estado en el que puede estar un pedido.
 */
enum class Estado{
    RECIBIDO, EN_PROCESO, TERMINADO
}

/**
 * Tipos de tarea a realizar.
 */
enum class TipoTarea{
    ENCORDADO, PERSONALIZADO, ADQUISICION
}


/**
 * Tipos de productos en venta.
 */
enum class TipoProduct{
    RAQUETA, CORDAJE, COMPLEMENTO
}
