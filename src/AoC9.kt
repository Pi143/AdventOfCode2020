import java.io.File

fun main() {
    println("Starting Day 9 A Test 1")
    calculateDay9PartA("Input/2020_Day9_A_Test1", 5)
    println("Starting Day 9 A Real")
    calculateDay9PartA("Input/2020_Day9_A", 25)


    println("Starting Day 9 B Test 1")
    calculateDay9PartB("Input/2020_Day9_A_Test1", 5)
    println("Starting Day 9 B Real")
    calculateDay9PartB("Input/2020_Day9_A", 25)
}

fun calculateDay9PartA(file: String, preambleLength: Int): Long {
    val Day9Data = readDay9Data(file)

    for (i in preambleLength until Day9Data.size) {
        if (!checkNumberValid(Day9Data.subList(i - preambleLength, i), Day9Data[i])) {
            println(Day9Data[i])
            return Day9Data[i]
        }
    }
    return -1
}


fun calculateDay9PartB(file: String, preambleLength: Int): Long {
    val Day9Data = readDay9Data(file)
    var invalidNumber: Long = -1

    for (i in preambleLength until Day9Data.size) {
        if (!checkNumberValid(Day9Data.subList(i - preambleLength, i), Day9Data[i])) {
            invalidNumber = Day9Data[i]
            break
        }
    }

    for (i in 0 until Day9Data.size) {
        var j = i + 1
        while (j < Day9Data.size && Day9Data.subList(i, j).sum() < invalidNumber) {
            j++
        }
        if (Day9Data.subList(i, j).sum() == invalidNumber) {
            val max = Day9Data.subList(i, j).max()
            val min = Day9Data.subList(i, j).min()
            println("start is ${Day9Data[i]} at index $i and end is ${Day9Data[j - 1]} at index ${j - 1}")
            println("max is $max and min is $min")
            println(max!! + min!!)
            return min + max
        }


    }


    return -1
}


fun checkNumberValid(numberList: MutableList<Long>, targetNum: Long): Boolean {
    val sorted = numberList.sorted()

    var lowIndex = 0
    var highIndex = sorted.size - 1

    while (sorted[lowIndex] + sorted[highIndex] != targetNum && highIndex > lowIndex) {
        if (sorted[lowIndex] + sorted[highIndex] < targetNum) {
            lowIndex++
        }
        if (sorted[lowIndex] + sorted[highIndex] > targetNum) {
            highIndex--
        }
    }

    return lowIndex < highIndex
}


fun readDay9Data(input: String): MutableList<Long> {
    val xmasList: MutableList<Long> = ArrayList()

    File(localdir + input).forEachLine { xmasList.add(it.toLong()) }

    return xmasList
}




