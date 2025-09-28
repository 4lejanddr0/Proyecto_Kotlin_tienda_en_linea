class Carrito {
    private val productosEnCarrito = mutableMapOf<String, Int>()

    var onProductoAgregado: ((Producto, Int) -> Unit)? = null

    fun agregarProducto(producto: Producto, cantidad: Int) {
        if (cantidad > producto.cantidadDisponible) {
            println("No hay suficiente stock disponible lo sentimos, elige otro producto.")
            return
        }

        producto.cantidadDisponible -= cantidad
        productosEnCarrito[producto.nombre] = productosEnCarrito.getOrDefault(producto.nombre, 0) + cantidad
        println("Tu producto ha sido agregado al carrito de compra: ${producto.nombre} (x$cantidad)")

        // Disparar evento
        onProductoAgregado?.invoke(producto, cantidad)
    }

    fun eliminarProducto(nombreProducto: String): Boolean {
        val claveReal = productosEnCarrito.keys.find { it.equals(nombreProducto, ignoreCase = true) }

        if (claveReal != null) {
            val cantidadEliminada = productosEnCarrito[claveReal] ?: 0
            productosEnCarrito.remove(claveReal)

            val producto = productosDisponibles.find { it.nombre.equals(claveReal, ignoreCase = true) }
            producto?.cantidadDisponible = (producto?.cantidadDisponible ?: 0) + cantidadEliminada

            println("Has eliminado el producto del carrito y se han devuelto $cantidadEliminada unidades al stock.")
            return true
        } else {
            println("El producto '$nombreProducto' no está en el carrito, lo sentimos elige otra opción de las que tenemos disponibles.")
            return false
        }
    }

   fun mostrarCarrito() {
    if (productosEnCarrito.isEmpty()) {
        println("\n🛒 El carrito está vacío, elige algun producto.")
        return
    }

    val tasaImpuesto = 0.13 // IVA 13%
    var subtotalGeneral = 0.0

    println("\n🛍️ Tu carrito de compras es el siguiente")
    println("=".repeat(60))
    println("%-20s %-10s %-12s %s".format("Producto", "Cantidad", "Precio U.", "Subtotal"))
    println("-".repeat(60))

    productosEnCarrito.forEach { (nombre, cantidad) ->
        val producto = productosDisponibles.find { it.nombre.equals(nombre, ignoreCase = true) }
        val precioUnitario = producto?.precio ?: 0.0
        val subtotal = precioUnitario * cantidad
        subtotalGeneral += subtotal

        println("%-20s %-10d %-12s $%.2f".format(
            nombre,
            cantidad,
            "$${"%.2f".format(precioUnitario)}",
            subtotal
        ))
    }

    val impuestos = subtotalGeneral * tasaImpuesto
    val totalConImpuestos = subtotalGeneral + impuestos

    println("-".repeat(60))
    println("%-44s $%.2f".format("Subtotal:", subtotalGeneral))
    println("%-44s $%.2f".format("IVA (13%):", impuestos))
    println("%-44s $%.2f".format("TOTAL:", totalConImpuestos))
}


    fun generarFactura() {
        if (productosEnCarrito.isEmpty()) {
            println("No hay productos en el carrito para generar factura, por favor elige producto.")
            return
        }

        val tasaImpuesto = 0.13 // agrega el 13% de IVA
        var total = 0.0
        println("\n Tu Factura de compra es:")
        println("Producto".padEnd(15) + "Cantidad".padEnd(10) + "Precio U.".padEnd(10) + "Subtotal")

        productosEnCarrito.forEach { (nombre, cantidad) ->
            val producto = productosDisponibles.find { it.nombre.equals(nombre, ignoreCase = true) }
            val precioUnitario = producto?.precio ?: 0.0
            val subtotal = precioUnitario * cantidad
            total += subtotal
            println("${nombre.padEnd(15)} ${cantidad.toString().padEnd(10)} ${"$${precioUnitario}".padEnd(10)} $${"%.2f".format(subtotal)}")
        }

        val impuesto = total * tasaImpuesto
        val totalConImpuesto = total + impuesto

        println("\nSubtotal: $${"%.2f".format(total)}")
        println("Impuesto (13%): $${"%.2f".format(impuesto)}")
        println("Total a pagar: $${"%.2f".format(totalConImpuesto)}")
        println("Gracias por su compra, te esperamos a la proxima!")

        productosEnCarrito.clear()
    }

}
