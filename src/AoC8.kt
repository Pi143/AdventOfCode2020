import java.io.File

fun main() {
    println("Starting Day 8 A Test 1")
    calculateDay8PartA("Input/2020_Day8_A_Test1")
    println("Starting Day 8 A Real")
    calculateDay8PartA("Input/2020_Day8_A")


    println("Starting Day 8 B Test 1")
    calculateDay8PartB("Input/2020_Day8_A_Test1")
    println("Starting Day 8 B Real")
    calculateDay8PartB("Input/2020_Day8_A")
}

fun calculateDay8PartA(file: String): Int {
    val Day8Data = readDay8Data(file)

    val result = runIntructions(Day8Data)
    println(result.second)
    return result.second
}


fun calculateDay8PartB(file: String): Int {
    val Day8Data = readDay8Data(file)

    var modifiedInstructionLists: MutableList<MutableList<Instruction>> = ArrayList()

    for(i in 0 until Day8Data.size){
        var tempList = Day8Data.toMutableList()
        if(Day8Data[i].operation == "nop"){
            tempList[i] = Instruction("jmp", Day8Data[i].param)
        }
        if(Day8Data[i].operation == "jmp"){
            tempList[i] = Instruction("nop", Day8Data[i].param)
        }

        modifiedInstructionLists.add(tempList)
    }

    for(instructions in modifiedInstructionLists){
        val result = runIntructions(instructions)
        if(result.first == instructions.size){
            println(result.second)
            return result.second
        }
    }

    return -1
}


fun runIntructions(instructionList: MutableList<Instruction>): Pair<Int, Int> {
    var accumulator = 0
    var position = 0
    val stopPositions: MutableList<Int> = ArrayList()
    stopPositions.add(instructionList.size)

    while (!stopPositions.contains(position)) {
        stopPositions.add(position)
        //https://youtrack.jetbrains.com/issue/KT-11362
        val (newPosition, newAccumulator) = executeInstruction(instructionList[position], position, accumulator)
        position = newPosition
        accumulator = newAccumulator
    }

    return Pair(position, accumulator)

}

fun executeInstruction(instruction: Instruction, position: Int, accumulator: Int): Pair<Int, Int> {
    return when (instruction.operation) {
        "nop" -> Pair(position+1, accumulator)
        "acc" -> Pair(position+1, accumulator+ instruction.param)
        "jmp" -> Pair(position+instruction.param, accumulator)
        else -> Pair(position, accumulator)
    }

}


fun readDay8Data(input: String): MutableList<Instruction> {
    //will this be ordered?
    val instructionList: MutableList<Instruction> = ArrayList()

    File(localdir + input).forEachLine { instructionList.add(parseInstructionDefinition(it)) }

    return instructionList
}

fun parseInstructionDefinition(line: String): Instruction {
    val operation = line.split(" ")[0]
    val param = line.split(" ")[1].toInt()

    return Instruction(operation, param)
}

class Instruction(operation: String, param: Int) {
    val operation = operation
    val param = param
}




