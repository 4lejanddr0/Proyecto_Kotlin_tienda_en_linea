fun main() {
    val carrito = Carrito()
    carrito.onProductoAgregado = { producto, cantidad ->
        println("🛒 ¡Has agregado $cantidad de ${producto.nombre} al carrito!")
    }

    while (true) {
        println("\nBienvenido a tienda Accesories en línea. Elige una opción:")
        println("1. Ver los productos disponibles")
        println("2. Agregar producto al carrito de compras")
        println("3. Eliminar producto del carrito")
        println("4. Ver carrito de tu compra")
        println("5. Pagar")
        println("6. Salir")
        print("Opción: ")

        when (readLine()?.trim()) {
            "1" -> mostrarProductos(productosDisponibles)
            "2" -> agregarAlCarrito(productosDisponibles, carrito)
            "3" -> eliminarDelCarrito(carrito)
            "4" -> carrito.mostrarCarrito()
            "5" -> {
                carrito.generarFactura()
                // return ---> Se elimina return para que el usuario pueda seguir comprando al finalizar una
            }
            "6" -> return
            else -> println("Opción no válida. Intenta de nuevo por favor.")
        }
    }
}
