import java.io.File

fun main() {
    println("Starting Day 18 A Test 1")
    calculateDay18PartA("Input/2020_Day18_A_Test1")
    println("Starting Day 18 A Real")
    calculateDay18PartA("Input/2020_Day18_A")


    println("Starting Day 18 B Test 1")
    calculateDay18PartB("Input/2020_Day18_A_Test1")
    println("Starting Day 18 B Real")
    calculateDay18PartB("Input/2020_Day18_A")
}

fun calculateDay18PartA(file: String): Long {
    var calculations = readDay18Data(file)

    var result: Long = 0

    for (singleCalc in calculations) {
        val reversedCalc = singleCalc.asReversed()
        result += calculateValue(reversedCalc)
    }

    println("result: $result")
    return result
}

fun calculateValue(calculations: List<String>): Long {

    if (calculations.size == 1) {
        return calculations[0].toLong()
    }

    var currentInput = calculations[0]
    if (currentInput.contains(")")) {
        var bracketsOpen = 0
        var currentPosition = 0
        bracketsOpen += calculations[currentPosition].count { ")".contains(it) }
        bracketsOpen -= calculations[currentPosition].count { "(".contains(it) }

        while (bracketsOpen > 0) {
            currentPosition++
            bracketsOpen += calculations[currentPosition].count { ")".contains(it) }
            bracketsOpen -= calculations[currentPosition].count { "(".contains(it) }
        }

        if (currentPosition == calculations.size - 1) {
            val subList = calculations.toMutableList()
            subList[0] = subList[0].removeSuffix(")")
            subList[currentPosition] = subList[currentPosition].removePrefix("(")

            return calculateValue(subList)
        } else {
            val subList = calculations.subList(0, currentPosition + 1).toMutableList()
            subList[0] = subList[0].removeSuffix(")")
            subList[currentPosition] = subList[currentPosition].removePrefix("(")

            when (calculations[currentPosition + 1]) {
                "+" -> return calculateValue(subList) + calculateValue(calculations.subList(currentPosition + 2, calculations.size))
                "*" -> return calculateValue(subList) * calculateValue(calculations.subList(currentPosition + 2, calculations.size))
            }
        }

    }

    currentInput.contains("(")

    val number = calculations[0].toLong()
    when (calculations[1]) {
        "+" -> return number + calculateValue(calculations.subList(2, calculations.size))
        "*" -> return number * calculateValue(calculations.subList(2, calculations.size))
    }

    return 0
}

fun calculateDay18PartB(file: String): Long {
    var calculations = readDay18Data(file)

    var result: Long = 0

    for (singleCalc in calculations) {
        val reversedCalc = singleCalc.asReversed()
        val advancedCalcs = addMultiplicationBrackets(reversedCalc)

        result += calculateValue(advancedCalcs)
    }

    println("result: $result")
    return result
}

fun addMultiplicationBrackets(reversedcalc: List<String>): List<String> {
    val mutableCalcs = reversedcalc.toMutableList()

    //even is number
    for (currentPosition in 0 until mutableCalcs.size-1 step 2) {
        if (reversedcalc[currentPosition + 1] == "+") {
            if (!reversedcalc[currentPosition].contains("(")) {
                mutableCalcs[currentPosition] = mutableCalcs[currentPosition] + ")"
            } else {
                var bracketsOpen = 0
                var bracketPosition = currentPosition
                bracketsOpen += reversedcalc[bracketPosition].count { "(".contains(it) }
                bracketsOpen -= reversedcalc[bracketPosition].count { ")".contains(it) }

                while (bracketsOpen > 0) {
                    bracketPosition--
                    bracketsOpen += reversedcalc[bracketPosition].count { "(".contains(it) }
                    bracketsOpen -= reversedcalc[bracketPosition].count { ")".contains(it) }
                }

                mutableCalcs[bracketPosition] = mutableCalcs[bracketPosition] + ")"
            }

            if (!reversedcalc[currentPosition + 2].contains(")")) {
                mutableCalcs[currentPosition + 2] = "(" + mutableCalcs[currentPosition + 2]
            } else {
                var bracketsOpen = 0
                var bracketPosition = currentPosition + 2
                bracketsOpen += reversedcalc[bracketPosition].count { ")".contains(it) }
                bracketsOpen -= reversedcalc[bracketPosition].count { "(".contains(it) }

                while (bracketsOpen > 0) {
                    bracketPosition++
                    bracketsOpen += reversedcalc[bracketPosition].count { ")".contains(it) }
                    bracketsOpen -= reversedcalc[bracketPosition].count { "(".contains(it) }
                }

                mutableCalcs[bracketPosition] = "(" + mutableCalcs[bracketPosition]
            }
        }
    }

    return mutableCalcs

}

fun readDay18Data(input: String): MutableList<List<String>> {
    var calculations = mutableListOf<List<String>>()

    File(localdir + input).forEachLine { calculations.add(it.split(" ")) }

    return calculations
}
