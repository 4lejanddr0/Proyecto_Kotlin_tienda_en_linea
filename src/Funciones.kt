val productosDisponibles = mutableListOf(
    Producto("PS5", 500.0, 8),
    Producto("XBOX", 500.0, 10),
    Producto("PC", 800.0, 9),
    Producto("MONITOR", 255.0, 8),
    Producto("PLASMA", 500.0, 20),
    Producto("CAMARA", 150.0, 20)
)

fun mostrarProductos(productos: List<Producto>) {
    println("\nProductos disponibles:")
    println("Nombre".padEnd(15) + "Precio".padEnd(10) + "Stock")
    println("-".repeat(35))
    productos.forEach {
        println("${it.nombre.padEnd(15)} ${"$${it.precio}".padEnd(10)} ${it.cantidadDisponible}")
    }
}

fun agregarAlCarrito(productos: List<Producto>, carrito: Carrito) {
    if (productos.isEmpty()) {
        println("No hay productos disponibles, elige los que tenemos disponibles.")
        return
    }

    println("\nProductos disponibles:")
    productos.forEach { println("- ${it.nombre} (Stock: ${it.cantidadDisponible})") }

    while (true) {
        println("\nIngrese el nombre del producto a agregar al carrito (o escriba 'salir' para finalizar):")
        val nombreProducto = readLine()?.trim()

        if (nombreProducto.equals("salir", ignoreCase = true)) {
            println("Finalizando la adición de productos.")
            break
        }

        val producto = productos.find { it.nombre.equals(nombreProducto, ignoreCase = true) }

        if (producto != null) {
            print("Ingrese la cantidad que desea agregar: ")
            val cantidad = try {
                readLine()?.toInt() ?: throw NumberFormatException()
            } catch (e: NumberFormatException) {
                println("Cantidad inválida. Ingrese un número válido.")
                continue
            }

            if (cantidad <= 0) {
                println("La cantidad debe ser mayor a 0.")
                continue
            }

            if (cantidad > producto.cantidadDisponible) {
                println("No hay suficiente stock. Máximo disponible: ${producto.cantidadDisponible}")
                continue
            }

            carrito.agregarProducto(producto, cantidad)
            println("$cantidad unidades de '${producto.nombre}' agregadas al carrito.")
        } else {
            println("Producto no encontrado.")
        }
    }
}


fun eliminarDelCarrito(carrito: Carrito) {
    println("\nIngrese el nombre del producto a eliminar del carrito:")
    val nombreProducto = readLine()?.trim()?.lowercase() ?: ""
    if (nombreProducto.isEmpty()) {
        println("Nombre de producto inválido.")
        return
    }

    carrito.eliminarProducto(nombreProducto)
}
