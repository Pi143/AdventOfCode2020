import java.io.File

fun main() {
    println("Starting Day 3 A Test 1")
    calculateDay3PartA("Input/2020_Day3_A_Test1")
    println("Starting Day 3 A Real")
    calculateDay3PartA("Input/2020_Day3_A")


    println("Starting Day 3 B Test 1")
    calculateDay3PartB("Input/2020_Day3_A_Test1")
    println("Starting Day 3 B Real")
    calculateDay3PartB("Input/2020_Day3_A")
}

fun calculateDay3PartA(file: String) {
    val day3Data = readDay3Data(file)
    val movement = Pair(3, 1)
    val treeCounter = countTrees(day3Data, movement)

    println("$treeCounter trees hit.")
}

fun calculateDay3PartB(file: String) {
    val day3Data = readDay3Data(file)
    val movementList = ArrayList<Pair<Int,Int>>()
    movementList.add(Pair(1,1))
    movementList.add(Pair(3,1))
    movementList.add(Pair(5,1))
    movementList.add(Pair(7,1))
    movementList.add(Pair(1,2))

    var treeProduct = 1
    for (movement in movementList){
        val treeCounter = countTrees(day3Data, movement)
       treeProduct *= treeCounter
    }

    println("$treeProduct is the treeproduct.")
}

fun countTrees( day3Data: MutableList<List<Boolean>>, movement: Pair<Int, Int>): Int {
    var position = Pair(0, 0) //First=x=right; Second=y=down
    var treeCounter = 0
    val slideLength = day3Data.size

    while (position.second < slideLength) {
        if (isTree(position, day3Data)) {
            treeCounter++
        }
        position = Pair(position.first + movement.first, position.second + movement.second)
    }
    return treeCounter
}

fun isTree(position: Pair<Int, Int>, day3Data: MutableList<List<Boolean>>): Boolean {
    val patternLength = day3Data[0].size

    val right = position.first % patternLength
    val down = position.second


    return day3Data[down][right]
}


fun readDay3Data(input: String): MutableList<List<Boolean>> {
    val map: MutableList<List<Boolean>> = ArrayList()

    File(localdir + input).forEachLine {
        map.add(readMapLine(it))
    }
    return map
}

fun readMapLine(string: String): MutableList<Boolean> {
    val rowList: MutableList<Boolean> = ArrayList()
    string.forEach {
        if (it == '#') {
            rowList.add(true)
        } else {
            rowList.add(false)
        }
    }
    return rowList
}
