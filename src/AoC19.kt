import java.io.File

fun main() {
    println("Starting Day 19 A Test 1")
    calculateDay19PartA("Input/2020_Day19_Rules_Test1", "Input/2020_Day19_A_Messages_Test1")
    println("Starting Day 19 A Real")
    calculateDay19PartA("Input/2020_Day19_Rules", "Input/2020_Day19_A_Messages")


    println("Starting Day 19 B Test 1")
    //calculateDay19PartB("Input/2020_Day19_A_Test1")
    println("Starting Day 19 B Real")
    calculateDay19PartA("Input/2020_Day19_Rules_B", "Input/2020_Day19_A_Messages")
}

fun calculateDay19PartA(ruleFile: String, messageFile: String): Int {
    var rules = readDay19Data(ruleFile)
    var messages = readDay19Data(messageFile)
    var ruleDepth = 10

    var ruleMap = mutableMapOf<String, String>()

    for (rule in rules) {
        val ruleName = rule.split(": ")[0]
        val ruleDef = rule.split(": ")[1]
        ruleMap[ruleName] = ruleDef.removeSurrounding("\"")
    }

    for (i in 0 until ruleDepth) {
        var ruleName = "0"
        val ruleDef = ruleMap[ruleName]!!
        val singleRuleParts = ruleDef.split(" ")
        println(i)

        for (singleRulePartIterator in singleRuleParts) {
            val singleRulePart = singleRulePartIterator.replace("(","").replace(")","").replace("+","").replace("?","")
            if (singleRulePart != "|" && singleRulePart != "a" && singleRulePart != "b" && singleRulePart.isNotBlank()) {
                val singleRule = ruleMap[singleRulePart]!!
                ruleMap[ruleName] = ruleMap[ruleName]!!.replace("(?<=(^|\\())$singleRulePart(?=(\\+| |\\)))".toRegex(), "($singleRule) ")
                ruleMap[ruleName] = ruleMap[ruleName]!!.replace("(?<=( |\\())$singleRulePart(?=(\\+| |\\)))".toRegex(), " ($singleRule) ")
                ruleMap[ruleName] = ruleMap[ruleName]!!.replace("(?<=( |\\())$singleRulePart(?=(\\+|$|\\)))".toRegex(), " ($singleRule)")
                ruleMap[ruleName] = ruleMap[ruleName]!!.replace("(?<=(^|\\())$singleRulePart(?=(\\+|$|\\)))".toRegex(), "($singleRule)")
            }
        }
    }

    val allPossibilities = ruleMap["0"]
    println("$allPossibilities is the config for the valid regEx")

    var allSpacelessPossibilities = allPossibilities?.replace("\\s".toRegex(), "")
    println("$allSpacelessPossibilities is the valid regEx")
    var validMessages = 0
    for (message in messages) {
        if (message.matches(allSpacelessPossibilities!!.toRegex())) {
            validMessages++
        }
    }
    println("$validMessages are valid")
    return validMessages
}

fun calculateDay19PartB(file: String): Long {
    var calculations = readDay19Data(file)

    return -1
}


fun readDay19Data(input: String): List<String> {

    return File(localdir + input).readLines()
}
