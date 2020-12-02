import java.io.File

fun main() {
    println("Starting Day 1 A Test 1")
    calculatePartA(2020, "Input/2020_1_A_test1")
    println("Starting Day 1 A Real")
    calculatePartA(2020, "Input/2020_1_A")

    println("Starting Day 1 B Test 1")
    calculatePartB(2020, "Input/2020_1_A_test1")
    println("Starting Day 1 B Real")
    calculatePartB(2020, "Input/2020_1_A")
}

fun calculatePartA(targetNum: Int, File: String, start: Int = 0): Information2D {
    val readData = readData(File)
    val sorted = readData.sorted()

    var lowIndex = start
    var highIndex = sorted.size - 1

    while (sorted[lowIndex] + sorted[highIndex] != targetNum && highIndex > lowIndex) {
        if (sorted[lowIndex] + sorted[highIndex] < targetNum) {
            lowIndex++
        }
        if (sorted[lowIndex] + sorted[highIndex] > targetNum) {
            highIndex--
        }
    }

    println("lowIndex : $lowIndex")
    println("highIndex : $highIndex")
    println("low : ${sorted[lowIndex]}")
    println("high : ${sorted[highIndex]}")
    println("Product : ${sorted[lowIndex] * sorted[highIndex]}")
    return Information2D(lowIndex, highIndex)

}

fun calculatePartB(targetNum: Int, File: String): Information2D {
    val readData = readData(File)
    val sorted = readData.sorted()

    var lowIndex = 0
    var middleIndex = 1
    var highIndex = sorted.size - 1

    while (sorted[lowIndex] + sorted[middleIndex] + sorted[highIndex] != targetNum) {
        val missingSum = targetNum - sorted[lowIndex]
        val calculatePartA = calculatePartA(missingSum, File, lowIndex)

        if(calculatePartA.highIndex > calculatePartA.lowIndex){
            middleIndex = calculatePartA.lowIndex
            highIndex = calculatePartA.highIndex
            break
        } else {
            lowIndex++
        }
    }

    println("lowIndex : $lowIndex")
    println("middleIndex : $middleIndex")
    println("highIndex : $highIndex")
    println("low : ${sorted[lowIndex]}")
    println("middle : ${sorted[middleIndex]}")
    println("high : ${sorted[highIndex]}")
    println("Product : ${sorted[lowIndex] * sorted[middleIndex] * sorted[highIndex]}")
    return Information2D(lowIndex, highIndex)
}

fun readData(input: String): List<Int> {
    val myList = mutableListOf<Int>()
    File(localdir + input).forEachLine { myList.add(it.toInt()) }
    return myList
}


class Information2D(
    var lowIndex: Int,
    var highIndex: Int
) {

}
