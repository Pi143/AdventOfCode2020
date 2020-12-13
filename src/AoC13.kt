import java.io.File

fun main() {
    println("Starting Day 13 A Test 1")
    calculateDay13PartA("Input/2020_Day13_A_Test1")
    println("Starting Day 13 A Real")
    calculateDay13PartA("Input/2020_Day13_A")


    println("Starting Day 13 B Test 1")
    calculateDay13PartB("Input/2020_Day13_A_Test1")
    println("Starting Day 13 B Real")
    calculateDay13PartB("Input/2020_Day13_A")
}

fun calculateDay13PartA(file: String): Int {
    val Day13Data = readDay13Data(file)
    val arrivalTime = Day13Data.first
    val buslist = Day13Data.second

    var minWaittime = Int.MAX_VALUE
    var choosenBusLine = -1

    for (bus in buslist) {
        val waitTime = bus - (arrivalTime % bus)
        if (waitTime < minWaittime) {
            minWaittime = waitTime
            choosenBusLine = bus
        }
    }

    println("The minimal waiting time is $minWaittime with busline $choosenBusLine")
    println("checksum: ${minWaittime * choosenBusLine}")
    return minWaittime * choosenBusLine
}


fun calculateDay13PartB(file: String): Long {
    val Day13Data = readDay13DataB(file)
    val buslist = Day13Data

    var currentTimestamp: Long = 0
    var fullyValid = false
    var stepSize = buslist[0]

    while (!fullyValid && currentTimestamp < 3000000000000000000) {
        currentTimestamp += stepSize
        var valid = true
        for (n in 0 until buslist.size) {
            if((currentTimestamp + n) % buslist[n] != 0.toLong()){
                valid = false
                break
            }else {
                var tempStepSize: Long = 1
                for(i in 0 until n){
                    tempStepSize *= buslist[i]
                }
                stepSize = tempStepSize
            }
        }
        fullyValid = valid
    }


    println("first valid timestamp is : $currentTimestamp")
    return currentTimestamp
}


fun readDay13Data(input: String): Pair<Int, MutableList<Int>> {
    val buslist: MutableList<Int> = ArrayList()
    var arrivalTime = 0


    val lines = File(localdir + input).readLines()
    arrivalTime = lines[0].toInt()
    val buslines = lines[1].split(",").filter { it != "x" }
    buslines.forEach { buslist.add(it.toInt()) }


    return Pair(arrivalTime, buslist)
}

fun readDay13DataB(input: String): MutableList<Long> {
    val buslist: MutableList<Long> = ArrayList()

    val lines = File(localdir + input).readLines()
    val buslines = lines[1].split(",").map {
        if (it == "x") {
            "1"
        } else {
            it
        }
    }
    buslines.forEach { buslist.add(it.toLong()) }

    return buslist
}


