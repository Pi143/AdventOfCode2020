import java.io.File

fun main() {
    println("Starting Day 10 A Test 1")
    calculateDay10PartA("Input/2020_Day10_A_Test1")
    println("Starting Day 10 A Real")
    calculateDay10PartA("Input/2020_Day10_A")


    println("Starting Day 10 B Test 1")
    calculateDay10PartB("Input/2020_Day10_A_Test1")
    println("Starting Day 10 B Real")
    calculateDay10PartB("Input/2020_Day10_A")
}

fun calculateDay10PartA(file: String): Int {
    val Day10Data = readDay10Data(file)
    val deviceJolt = Day10Data.max()!! + 3
    val chargingJolt = 0
    val sortedJolts = (Day10Data + chargingJolt + deviceJolt).sorted()

    val jumpMap = mutableMapOf(1 to 0, 2 to 0, 3 to 0)

    for (i in 1 until sortedJolts.size) {
        val jumpSize = sortedJolts[i] - sortedJolts[i - 1]
        jumpMap[jumpSize] = jumpMap[jumpSize]!! + 1
    }

    println(jumpMap)
    println(jumpMap[1]!! * jumpMap[3]!!)

    return jumpMap[1]!! * jumpMap[3]!!
}


fun calculateDay10PartB(file: String): Long {
    val Day10Data = readDay10Data(file)
    val deviceJolt = Day10Data.max()!! + 3
    val chargingJolt = 0
    val sortedJolts = (Day10Data + chargingJolt + deviceJolt).sorted()

    var amountValidConfigs:Long = 1

    val jumpSizeLists = mutableListOf<List<Int>>()
    var currentJumpSizeList = mutableListOf<Int>()

    for (i in 1 until sortedJolts.size) {
        when (val jumpSize = sortedJolts[i] - sortedJolts[i - 1]) {
            1, 2 -> currentJumpSizeList.add(jumpSize)
            3 -> {
                if (currentJumpSizeList.isNotEmpty()) {
                    jumpSizeLists.add(currentJumpSizeList)
                }
                currentJumpSizeList = mutableListOf()
            }
            else -> throw Exception("Jumpsize to high")
        }
    }

    for (currentList in jumpSizeLists) {
        //split in groups
        var amountValidSublists = 0
        val possibleSublists = createSublists(currentList)
        for (possibleSublistCombination in possibleSublists) {
            var isValid = true
            for (partOfSublist in possibleSublistCombination) {
                if (partOfSublist.sum() > 3) {
                    isValid = false
                }
            }
            if (isValid) {
                amountValidSublists++
            }
        }
        amountValidConfigs *= amountValidSublists
    }

    println("$amountValidConfigs are valid")
    return amountValidConfigs
}

fun createSublists(currentList: List<Int>): List<List<List<Int>>> {
    when (currentList.size) {
        0 -> throw Exception("List is Empty")
        1 -> return listOf(listOf(currentList))
        2 -> return listOf(listOf(currentList), listOf(listOf(currentList[0]), listOf(currentList[1])))
        3 -> return listOf(
            listOf(currentList),
            listOf(listOf(currentList[0]), listOf(currentList[1]), listOf(currentList[2])),
            listOf(listOf(currentList[0], currentList[1]), listOf(currentList[2])),
            listOf(listOf(currentList[0]), listOf(currentList[1], currentList[2]))
        )
        else -> {
            var newSublistList: MutableList<List<List<Int>>> = mutableListOf()
            val sublist1 = createSublists(currentList.subList(1, currentList.size))
            for (listConfig in sublist1) {
                newSublistList.add((listConfig + listOf(listOf(currentList[0]))))
            }
            val sublist2 = createSublists(currentList.subList(2, currentList.size))
            for (listConfig in sublist2) {
                newSublistList.add((listConfig + listOf(listOf(currentList[0], currentList[1]))))
            }
            val sublist3 = createSublists(currentList.subList(3, currentList.size))
            for (listConfig in sublist3) {
                newSublistList.add((listConfig + listOf(listOf(currentList[0], currentList[1], currentList[2]))))
            }
            return newSublistList
        }
    }
}

fun readDay10Data(input: String): MutableList<Int> {
    val joltList: MutableList<Int> = ArrayList()

    File(localdir + input).forEachLine { joltList.add(it.toInt()) }

    return joltList
}




