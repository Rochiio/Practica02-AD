package utils.ficheros


interface FicherosJson<T>{
    suspend fun writeFichero(path: String, item: T)
}