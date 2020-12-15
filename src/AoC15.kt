import java.io.File
import kotlin.math.pow

fun main() {
    println("Starting Day 15 A Test 1")
    calculateDay15PartA("Input/2020_Day15_A_Test1")
    println("Starting Day 15 A Real")
    calculateDay15PartA("Input/2020_Day15_A")


    println("Starting Day 15 B Test 1")
    //calculateDay15PartB("Input/2020_Day15_B_Test1")
    println("Starting Day 15 B Real")
    //calculateDay15PartB("Input/2020_Day15_A")
}

fun calculateDay15PartA(file: String): Int {
    val numberList = readDay15Data(file)
    var cache = mutableMapOf<Int, Int>()
    val finishTime = 30000000

    for (i in 0 until finishTime) {
        if (i < numberList.size) {
            if (i > 0) {
                cache[numberList[i - 1]] = i - 1
            }
        } else {
            val lastNumber = numberList[i - 1]
            if (cache.containsKey(lastNumber)) {
                numberList.add(i - cache[lastNumber]!!-1)
            } else {
                numberList.add(0)
            }
            cache[numberList[i - 1]] = i - 1
        }

    }
    println(numberList)
    println(numberList[finishTime - 1])

    return numberList[finishTime - 1]
}


fun calculateDay15PartB(file: String): Long {

    return -1
}

fun readDay15Data(input: String): ArrayList<Int> {
    val numberList = arrayListOf<Int>()

    val readLines = File(localdir + input).readLines()

    readLines[0].split(",").forEach { numberList.add(it.toInt()) }

    return numberList
}