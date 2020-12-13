import java.io.File

val floor = 0
val emptySeat = 1
val occupiedSeat = 2
fun main() {
    println("Starting Day 11 A Test 1")
    calculateDay11PartA("Input/2020_Day11_A_Test1")
    println("Starting Day 11 A Real")
    calculateDay11PartA("Input/2020_Day11_A")


    println("Starting Day 11 B Test 1")
    calculateDay11PartB("Input/2020_Day11_A_Test1")
    println("Starting Day 11 B Real")
    calculateDay11PartB("Input/2020_Day11_A")
}

fun calculateDay11PartA(file: String): Int {
    val Day11Data = readDay11Data(file)


    var oldSeatConfig = Day11Data.map { it.clone() }.toTypedArray()
    var newSeatConfig = sitPeopleA(Day11Data)

    while (!(oldSeatConfig contentDeepEquals newSeatConfig)) {
        oldSeatConfig = newSeatConfig.map { it.clone() }.toTypedArray()
        newSeatConfig = sitPeopleA(oldSeatConfig)
    }

    val occupiedSeats = newSeatConfig.sumBy { row -> row.count { it == occupiedSeat } }
    println("$occupiedSeats seats are occupied")
    return occupiedSeats
}

fun sitPeopleA(seatConfig: Array<IntArray>): Array<IntArray> {
    val nextSeatConfig = seatConfig.map { it.clone() }.toTypedArray()
    val rows = seatConfig.size
    val columns = seatConfig[0].size


    for (line in 0 until rows) {
        for (seat in 0 until columns) {
            nextSeatConfig[line][seat] = determineNextSittingA(line, seat, seatConfig)
        }
    }
    return nextSeatConfig
}

fun determineNextSittingA(row: Int, seat: Int, seatConfig: Array<IntArray>): Int {
    return when (seatConfig[row][seat]) {
        emptySeat -> {
            if (countOccupiedDirectNeighbors(row, seat, seatConfig) == 0) {
                occupiedSeat
            } else {
                seatConfig[row][seat]
            }
        }
        occupiedSeat -> {
            if (countOccupiedDirectNeighbors(row, seat, seatConfig) >= 4) {
                emptySeat
            } else {
                seatConfig[row][seat]
            }
        }
        else -> seatConfig[row][seat]
    }
}

fun countOccupiedDirectNeighbors(row: Int, seat: Int, seatConfig: Array<IntArray>): Int {
    var occupiedNeighbors = 0
    val rows = seatConfig.size
    val columns = seatConfig[0].size
    if (row > 0) {
        if (seat > 0) {
            if (seatConfig[row - 1][seat - 1] == occupiedSeat) {
                occupiedNeighbors++
            }
        }
        if (seatConfig[row - 1][seat] == occupiedSeat) {
            occupiedNeighbors++
        }
        if (seat < columns - 1) {
            if (seatConfig[row - 1][seat + 1] == occupiedSeat) {
                occupiedNeighbors++
            }
        }
    }
    if (seat > 0) {
        if (seatConfig[row][seat - 1] == occupiedSeat) {
            occupiedNeighbors++
        }
    }
    if (seat < columns - 1) {
        if (seatConfig[row][seat + 1] == occupiedSeat) {
            occupiedNeighbors++
        }
    }

    if (row < rows - 1) {
        if (seat > 0) {
            if (seatConfig[row + 1][seat - 1] == occupiedSeat) {
                occupiedNeighbors++
            }
        }
        if (seatConfig[row + 1][seat] == occupiedSeat) {
            occupiedNeighbors++
        }
        if (seat < columns - 1) {
            if (seatConfig[row + 1][seat + 1] == occupiedSeat) {
                occupiedNeighbors++
            }
        }
    }

    return occupiedNeighbors

}


fun calculateDay11PartB(file: String): Int {
    val Day11Data = readDay11Data(file)


    var oldSeatConfig = Day11Data.map { it.clone() }.toTypedArray()
    var newSeatConfig = sitPeopleA(Day11Data)

    while (!(oldSeatConfig contentDeepEquals newSeatConfig)) {
        oldSeatConfig = newSeatConfig.map { it.clone() }.toTypedArray()
        newSeatConfig = sitPeopleB(oldSeatConfig)
    }

    val occupiedSeats = newSeatConfig.sumBy { row -> row.count { it == occupiedSeat } }
    println("$occupiedSeats seats are occupied")
    return occupiedSeats
}

fun sitPeopleB(seatConfig: Array<IntArray>): Array<IntArray> {
    val nextSeatConfig = seatConfig.map { it.clone() }.toTypedArray()
    val rows = seatConfig.size
    val columns = seatConfig[0].size


    for (line in 0 until rows) {
        for (seat in 0 until columns) {
            nextSeatConfig[line][seat] = determineNextSittingB(line, seat, seatConfig)
        }
    }
    return nextSeatConfig
}

fun determineNextSittingB(row: Int, seat: Int, seatConfig: Array<IntArray>): Int {
    return when (seatConfig[row][seat]) {
        emptySeat -> {
            if (countOccupiedDistantNeighbors(row, seat, seatConfig) == 0) {
                occupiedSeat
            } else {
                seatConfig[row][seat]
            }
        }
        occupiedSeat -> {
            if (countOccupiedDistantNeighbors(row, seat, seatConfig) >= 5) {
                emptySeat
            } else {
                seatConfig[row][seat]
            }
        }
        else -> seatConfig[row][seat]
    }
}

fun countOccupiedDistantNeighbors(row: Int, seat: Int, seatConfig: Array<IntArray>): Int {
    var occupiedNeighbors = 0

    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, -1, -1, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, -1, 0, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, -1, 1, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, 0, -1, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, 0, 1, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, 1, -1, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, 1, 0, seatConfig)
    occupiedNeighbors += isNextVisbleSeatInDirectionOccupied(row, seat, 1, 1, seatConfig)


    return occupiedNeighbors

}

fun isNextVisbleSeatInDirectionOccupied(row: Int, col: Int, rowDir: Int, colDir: Int, seatConfig: Array<IntArray>): Int {
    var currentConsideredRow = row + rowDir
    var currentConsideredColumn = col + colDir
    while (seatConfig.getOrNull(currentConsideredRow)?.getOrNull(currentConsideredColumn) != null){
        when(seatConfig.getOrNull(currentConsideredRow)?.getOrNull(currentConsideredColumn)){
            floor -> {
            currentConsideredRow += rowDir
            currentConsideredColumn += colDir}
            occupiedSeat -> return 1
            emptySeat -> return 0
        }
    }
    return 0
}


fun readDay11Data(input: String): Array<IntArray> {


    val lines = File(localdir + input).readLines()
    val rows = lines.size
    val columns = lines[0].length
    val seatConfig: Array<IntArray> = Array(rows) { IntArray(columns) }

    for (line in 0 until rows) {
        for (seat in 0 until columns) {
            when (lines[line][seat]) {
                '.' -> seatConfig[line][seat] = floor
                'L' -> seatConfig[line][seat] = emptySeat
                '#' -> seatConfig[line][seat] = occupiedSeat
            }
        }
    }

    return seatConfig
}




