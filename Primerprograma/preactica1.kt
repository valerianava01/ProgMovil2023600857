import java.util.Scanner

val scanner = Scanner(System.`in`)

fun sumarTresNumeros(): Double {
    print("Ingrese el primer número: ")
    val numero1 = scanner.nextDouble()
    print("Ingrese el segundo número: ")
    val numero2 = scanner.nextDouble()
    print("Ingrese el tercer número: ")
    val numero3 = scanner.nextDouble()
    val resultado = numero1 + numero2 + numero3
    println("La suma es: $resultado")
    return resultado
}

fun ingresarNombre() {
    print("Ingrese su nombre completo: ")
    scanner.nextLine() 
    val nombre = scanner.nextLine()
    println("Su nombre es: $nombre")
}

fun calcularTiempoVivido() {
    print("Ingrese su año de nacimiento: ")
    val anioNacimiento = scanner.nextInt()
    print("Ingrese su mes de nacimiento (1-12): ")
    val mesNacimiento = scanner.nextInt()
    print("Ingrese su día de nacimiento: ")
    val diaNacimiento = scanner.nextInt()
    
    print("Ingrese el año actual: ")
    val anioActual = scanner.nextInt()
    print("Ingrese el mes actual (1-12): ")
    val mesActual = scanner.nextInt()
    print("Ingrese el día actual: ")
    val diaActual = scanner.nextInt()
    
    val aniosVividos = anioActual - anioNacimiento
    val mesesVividos = (anioActual - anioNacimiento) * 12 + (mesActual - mesNacimiento)
    val diasVividos = aniosVividos * 365 + (mesesVividos % 12) * 30 + (diaActual - diaNacimiento)
    val horasVividas = diasVividos * 24
    val minutosVividos = horasVividas * 60
    val segundosVividos = minutosVividos * 60
    
    println("Tiempo vivido:")
    println("$aniosVividos años, $mesesVividos meses,$diasVividos días, $horasVividas horas, $minutosVividos minutos, $segundosVividos segundos")
}

fun main() {
    while (true) {
        println("Escoja una opción:")
        println("---- MENÚ ----")
        println("1. Suma de 3 números")
        println("2. Ingresar nombre completo")
        println("3. Cálculo de vida (tiempo vivido)")
        println("4. Cierre de programa")
        print("Seleccione una opción: ")

        val opcion = if (scanner.hasNextInt()) scanner.nextInt() else {
            scanner.next() 
            -1
        }

        when (opcion) {
            1 -> sumarTresNumeros()
            2 -> ingresarNombre()
            3 -> calcularTiempoVivido()
            4 -> {
                println("Cerrando el programa...")
                break
            }
            else -> println("Opción no válida. Intente nuevamente.")
        }
        println() 
    }
    scanner.close()
}
