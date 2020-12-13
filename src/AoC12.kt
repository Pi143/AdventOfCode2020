import java.io.File
import kotlin.math.absoluteValue


const val N = 0
const val S = 180
const val E = 90
const val W = 270
fun main() {
    println("Starting Day 12 A Test 1")
    calculateDay12PartA("Input/2020_Day12_A_Test1")
    println("Starting Day 12 A Real")
    calculateDay12PartA("Input/2020_Day12_A")


    println("Starting Day 12 B Test 1")
    calculateDay12PartB("Input/2020_Day12_A_Test1")
    println("Starting Day 12 B Real")
    calculateDay12PartB("Input/2020_Day12_A")
}

fun calculateDay12PartA(file: String): Int {
    val Day12Data = readDay12Data(file)
    var currentX = 0
    var currentY = 0
    var currentFacing = E

    for (instruction in Day12Data) {
        when (instruction.operation) {
            'N' -> currentY += instruction.param
            'S' -> currentY -= instruction.param
            'E' -> currentX += instruction.param
            'W' -> currentX -= instruction.param
            'R', 'L' -> currentFacing = turnFacing(currentFacing, instruction)
            'F' -> when (currentFacing) {
                N -> currentY += instruction.param
                S, S-360 -> currentY -= instruction.param
                E, E-360 -> currentX += instruction.param
                W, W-360 -> currentX -= instruction.param
                else -> throw Exception("Direction unclear")
            }
        }
    }

    val manhattenDist = currentX.absoluteValue + currentY.absoluteValue
    println("The Manhattan distance from the start is $manhattenDist")

    return manhattenDist
}

fun executeMovementInstruction(currentX: Int, currentY: Int, currentFacing: Int, instruction: MovementInstruction) {
    var nextX = currentX
    var nextY = currentY
    var nextFacing = currentFacing

    when (instruction.operation) {
        'N' -> nextY += instruction.param
        'S' -> nextY -= instruction.param
        'E' -> nextX += instruction.param
        'W' -> nextX -= instruction.param
        'R', 'L' -> nextFacing = turnFacing(currentFacing, instruction)
        'F' -> when (currentFacing) {
            N -> nextY += instruction.param
            S, S-360 -> nextY -= instruction.param
            E, E-360 -> nextX += instruction.param
            W, W-360 -> nextX -= instruction.param
            else -> throw Exception("Direction unclear")
        }
    }

}

fun turnFacing(currentFacing: Int, instruction: MovementInstruction): Int {
    if (instruction.operation == 'R') {
        return (currentFacing + instruction.param) % 360
    }
    if (instruction.operation == 'L') {
        return (currentFacing - instruction.param) % 360
    }
    return -1
}


fun calculateDay12PartB(file: String): Int {
    val Day12Data = readDay12Data(file)
    var speedX = 10
    var speedY = 1
    var currentX = 0
    var currentY = 0

    for (instruction in Day12Data) {
        when (instruction.operation) {
            'N' -> speedY += instruction.param
            'S' -> speedY -= instruction.param
            'E' -> speedX += instruction.param
            'W' -> speedX -= instruction.param
            'R' -> when (instruction.param) {
                0 -> {}
                90 -> {val tempX = speedX
                    speedX = speedY
                speedY = -1*tempX}
                180 -> {
                    speedX *= -1
                    speedY *= -1
                }
                270 -> {val tempY = speedY
                    speedY = speedX
                    speedX = -1*tempY}
                else -> throw Exception("Direction unclear")
            }
            'L' -> when (instruction.param) {
                0 -> {}
                90 -> {val tempY = speedY
                    speedY = speedX
                    speedX = -1*tempY}
                180 -> {
                    speedX *= -1
                    speedY *= -1
                }
                270 -> {val tempX = speedX
                    speedX = speedY
                    speedY = -1*tempX}
                else -> throw Exception("Direction unclear")
            }
            'F' -> {
                currentX += instruction.param * speedX
                currentY += instruction.param * speedY
            }
        }
    }

    val manhattenDist = currentX.absoluteValue + currentY.absoluteValue
    println("The Manhattan distance from the start is $manhattenDist")

    return manhattenDist
}


fun readDay12Data(input: String): MutableList<MovementInstruction> {
    val movementInstructions: MutableList<MovementInstruction> = ArrayList()

    File(localdir + input).forEachLine { movementInstructions.add(readMovementInstruction(it)) }


    return movementInstructions
}

fun readMovementInstruction(instruction: String): MovementInstruction {
    val operation = instruction[0]
    val param = instruction.subSequence(1, instruction.length).toString().toInt()

    return MovementInstruction(operation, param)
}

class MovementInstruction(operation: Char, param: Int) {
    val operation = operation
    val param = param
}



