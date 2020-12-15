import java.io.File
import kotlin.math.pow

fun main() {
    println("Starting Day 14 A Test 1")
    calculateDay14PartA("Input/2020_Day14_A_Test1")
    println("Starting Day 14 A Real")
    calculateDay14PartA("Input/2020_Day14_A")


    println("Starting Day 14 B Test 1")
    calculateDay14PartB("Input/2020_Day14_B_Test1")
    println("Starting Day 14 B Real")
    calculateDay14PartB("Input/2020_Day14_A")
}

fun calculateDay14PartA(file: String): Long {
    var memory: MutableMap<Long, Long> = HashMap()
    var orMask: Long = 0  //Ones
    var andMask: Long = Long.MAX_VALUE  //Zeroes

    val commandLines = File(localdir + file).readLines()

    for (line in commandLines) {
        val (command, paramList) = parseCommand(line)

        when (command) {
            "mask" -> {
                val (orTemp, andTemp) = readMask(paramList[0].toString())
                orMask = orTemp
                andMask = andTemp

            }
            "mem" -> {
                memory[paramList[0].toString().toLong()] =
                    applyBitmask(number = paramList[1].toString().toLong(), orMask = orMask, andMask = andMask)
            }
        }
    }

    val memorySum = memory.map { it.value }.sum()
    println(memory)
    println("Memorysum: $memorySum")
    return memorySum
}

fun parseCommand(line: String): Pair<String, List<Any>> {
    val split = line.split(" = ")
    val command = split[0]
    val value = split[1]

    if (command.contains("mem")) {
        val position = "\\d+".toRegex().find(command)
        val paramList = listOf(position?.value.toString().toInt(), value.toInt())
        return Pair("mem", paramList)
    }
    val paramList = listOf(value)

    return Pair(command, paramList)

}


fun calculateDay14PartB(file: String): Long {
    var memory: MutableMap<Long, Long> = HashMap()
    var mask = ""

    val commandLines = File(localdir + file).readLines()

    for (line in commandLines) {
        val (command, paramList) = parseCommand(line)

        when (command) {
            "mask" -> {
                mask = paramList[0].toString()
            }
            "mem" -> {
                val addressList = applyBitmask(number = paramList[0].toString().toLong(), mask = mask)
                for(address in addressList){
                    memory[address]= paramList[1].toString().toLong()
                }
            }
        }
    }

    val memorySum = memory.map { it.value }.sum()
    println(memory)
    println("Memorysum: $memorySum")
    return memorySum
}

fun applyBitmask(number: Long, orMask: Long = 0, andMask: Long = Long.MAX_VALUE): Long {
    return (number or orMask) and andMask
}

fun readMask(maskString: String): Pair<Long, Long> {
    var orMask: Long = 0  //Ones  -> Zeroes do nothing
    var andMask: Long = Long.MAX_VALUE  //Zeroes -> Ones do nothing
    val reversed = maskString.reversed()

    for (charIndex in maskString.indices) {
        when (reversed[charIndex]) {
            '1' -> {
                orMask += 2.toDouble().pow(charIndex).toLong()
            }
            '0' -> {
                andMask -= 2.toDouble().pow(charIndex).toLong()
            }
            'X' -> {
            }
        }
    }

    return Pair(orMask, andMask)
}

fun applyBitmask(number: Long, mask: String): MutableSet<Long> {
    val reversed = mask.reversed()
    var numberList: MutableSet<Long> = mutableSetOf(number)

    for (charIndex in mask.indices) {
        var tempNumberList: MutableSet<Long> = numberList.toMutableSet()

        when (reversed[charIndex]) {
            '1' -> {
                for (n in numberList) {
                    tempNumberList.remove(n)
                    tempNumberList.add(n or 2.toDouble().pow(charIndex).toLong())
                }
            }
            '0' -> {
                /*
                for (n in numberList) {
                    tempNumberList.remove(n)
                    tempNumberList.add(n and Long.MAX_VALUE - 2.toDouble().pow(charIndex).toLong())
                }

                 */
            }
            'X' -> {
                for (n in numberList) {
                    tempNumberList.remove(n)
                    tempNumberList.add(n and Long.MAX_VALUE - 2.toDouble().pow(charIndex).toLong())
                    tempNumberList.add(n or 2.toDouble().pow(charIndex).toLong())
                }
            }
        }
        numberList = tempNumberList

    }
    return numberList
}

class Command(command: String, paramList: ArrayList<Int>)