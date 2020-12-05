import java.io.File

fun main() {
    //tests()
    println("Starting Day 5 A Test 1")
    calculateDay5PartA("Input/2020_Day5_A_Test1")
    println("Starting Day 5 A Real")
    calculateDay5PartA("Input/2020_Day5_A")


    println("Starting Day 5 B Test 1")
    calculateDay5PartB("Input/2020_Day5_A_Test1")
    println("Starting Day 5 B Real")
    calculateDay5PartB("Input/2020_Day5_A")
}

fun tests(){
    assert(calculateDay5PartA("Input/2020_Day5_A_Test1")==820)

}

fun calculateDay5PartA(file: String): Int {
    val Day5Data = readDay5Data(file)
    var maxChecksum = 0
    for (seatDef in Day5Data) {
        val checkSum = seatDef.first * 8 + seatDef.second
        maxChecksum = if (checkSum > maxChecksum)  checkSum else maxChecksum
    }
    println("$maxChecksum is the highestChecksum")
    return maxChecksum
}


fun calculateDay5PartB(file: String) {
    val checkSumList = (0..calculateDay5PartA(file)).toList().toMutableList()
    val Day5Data = readDay5Data(file)
    for (seatDef in Day5Data) {
        val checkSum = seatDef.first * 8 + seatDef.second
        checkSumList.remove(checkSum)
    }
    val removalList: MutableList<Int> = ArrayList()
    for (checkSum in checkSumList){
        if(checkSumList.contains(checkSum+1) || checkSumList.contains(checkSum-1)){
            removalList.add(checkSum)
        }
    }
    checkSumList.removeAll(removalList)

    println(checkSumList)
}

fun readDay5Data(input: String): MutableList<Pair<Int, Int>> {
    val seatList: MutableList<Pair<Int, Int>> = ArrayList()

    File(localdir + input).forEachLine { seatList.add(decodeSeat(it))  }

    return seatList
}

fun decodeSeat(it: String): Pair<Int, Int> {
    val rowPattern = "([F|B])+".toRegex()
    val rowDefintion = rowPattern.find(it)?.value
    val colPattern = "([L|R])+".toRegex()
    val colDefintion = colPattern.find(it)?.value

    val rowInt = Integer.parseInt(rowDefintion?.replace('F', '0')?.replace('B', '1'),2)
    val colInt = Integer.parseInt(colDefintion?.replace('L', '0')?.replace('R', '1'),2)

    return Pair(rowInt,colInt)

}

