import java.util.*

fun main() {
    println("Hola mundo")
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //  val > var
    // Duck Typing
    var ejemploVariable = " Adrian Eguez "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;

    // Variable primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

// Paramatros nombrados
//    calcularSueldo(sueldo = 10.00)
//    calcularSueldo(sueldo = 10.00, tasa = 15.00)
    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00)

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1,null)

}

// void -> Unit
fun imprimirNombre(nombre: String): Unit {
    // "Nombre: " + variable + " bienvenido";
    println("Nombre : ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        bonoEspecial.dec()
        return sueldo * (100 / tasa) + bonoEspecial
    }
}

abstract class Numeros(
    //entre parentesis es el constructor primario
//    Ejemplo:
//    unoProp: Int, // (Parametro (sin modificador de acceso))
//    private var uno: Int, // Propiedad Publica Clase numeros.uno
//    var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
//    public var uno: Int,
//    Propiedad de la clase protected numeros.numeroUno
    protected val numeroUno: Int,

    protected val numeroDos: Int,


    ) {
    // var cedula: string
    init { //bloque de codigo de constructor primario
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos;
        println("Inicializando")
    }
}

class Suma( // Constructor Primario Suma
    uno: Int, // Parametro
    dos: Int // Parametro
) : Numeros(uno, dos) { // <- Constructor del Padre

    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor(//  Segundo constructor
        uno: Int?, // parametros
        dos: Int // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor(//  tercer constructor
        uno: Int, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    )
    // Si no lo necesitamos al bloque de codigo "{}" lo omitimos
}