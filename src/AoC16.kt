import java.io.File

fun main() {
    println("Starting Day 16 A Test 1")
    calculateDay16PartA(
        "Input/2020_Day16_Rules_Test1",
        "Input/2020_Day16_myTicket_Test1",
        "Input/2020_Day16_nearbyTickets_Test1"
    )
    println("Starting Day 16 A Real")
    calculateDay16PartA("Input/2020_Day16_Rules", "Input/2020_Day16_myTicket", "Input/2020_Day16_nearbyTickets")


    println("Starting Day 16 B Test 1")
    calculateDay16PartB(
        "Input/2020_Day16_Rules_Test1",
        "Input/2020_Day16_myTicket_Test1",
        "Input/2020_Day16_nearbyTickets_Test1"
    )
    println("Starting Day 16 B Real")
    calculateDay16PartB("Input/2020_Day16_Rules", "Input/2020_Day16_myTicket", "Input/2020_Day16_nearbyTickets")
}

fun calculateDay16PartA(ruleFile: String, myTicketFile: String, nearbyTicketFiles: String): Int {
    val rules = readDay16Rules(ruleFile)
    val myTicket = readDay16Ticket(myTicketFile)
    val nearbyTickets = readDay16Ticket(nearbyTicketFiles)

    var errorRate = 0

    for (ticket in nearbyTickets) {

        for (number in ticket) {
            var numberValid = false

            for (rule in rules) {
                for (range in rule.rangeList) {
                    if (number in range) {
                        numberValid = true
                    }
                }
            }
            if (!numberValid) {
                errorRate += number
            }
        }
    }


    println("The errorRate ist $errorRate")
    return errorRate
}


fun calculateDay16PartB(ruleFile: String, myTicketFile: String, nearbyTicketFiles: String): Int {
    val rules = readDay16Rules(ruleFile)
    val myTicket = readDay16Ticket(myTicketFile)
    val nearbyTickets = readDay16Ticket(nearbyTicketFiles)

    val validTickets = nearbyTickets.toMutableList()

    var ruleValidity: MutableMap<String, ArrayList<Int>> = mutableMapOf()


    for (ticket in nearbyTickets) {
        for (number in ticket) {
            var numberValid = false

            for (rule in rules) {
                for (range in rule.rangeList) {
                    if (number in range) {
                        numberValid = true
                    }
                }
            }
            if (!numberValid) {
                validTickets.remove(ticket)
            }
        }
    }

    val amountTicketFields = myTicket[0].size
    for (rule in rules) {
        for (i in 0 until amountTicketFields) {

            var validForField = true
            for (ticket in validTickets) {
                var inAnyRange = false
                for (range in rule.rangeList) {
                    if (ticket[i] in range) {
                        inAnyRange = true
                    }
                }
                if (!inAnyRange) {
                    validForField = false
                }

            }

            if (validForField) {
                if (ruleValidity.containsKey(rule.name)) {
                    ruleValidity[rule.name] = (arrayListOf(i) + ruleValidity[rule.name]!!) as ArrayList<Int>
                } else {
                    ruleValidity[rule.name] = arrayListOf(i)
                }
            }
        }
    }


    while (ruleValidity.isNotEmpty()) {
        var foundRoule = -1
        var foundKey: String = ""
        for (validity in ruleValidity) {
            if (validity.value.size == 1) {
                println("")
                print("${validity.key} is valid for: ")
                for (field in validity.value) {
                    print("$field ")
                    foundRoule = field
                    foundKey = validity.key
                }


            }

        }
        ruleValidity.remove(foundKey)
        for (val2 in ruleValidity.keys) {
            if (foundRoule != -1) {
                ruleValidity[val2] = (ruleValidity[val2]!! - arrayListOf(foundRoule)) as ArrayList<Int>
            }

        }
    }

    return -1
}

fun readDay16Rules(input: String): ArrayList<Rule> {
    val ruleList = arrayListOf<Rule>()

    File(localdir + input).forEachLine {
        val split = it.split(": ")
        val name = split[0]
        val params = split[1]
        val paramSplit = params.split(" or ")
        val rangeList: ArrayList<IntRange> = arrayListOf()
        for (param in paramSplit) {
            val rangeSplit = param.split("-")
            val intRange = IntRange(rangeSplit[0].toInt(), rangeSplit[1].toInt())
            rangeList.add(intRange)
        }

        val rule = Rule(name, rangeList)
        ruleList.add(rule)
    }

    return ruleList
}

fun readDay16Ticket(input: String): ArrayList<ArrayList<Int>> {
    val ticketList = arrayListOf<ArrayList<Int>>()

    File(localdir + input).forEachLine {
        val ticket = arrayListOf<Int>()
        for (number in it.split(",")) {
            ticket.add(number.toInt())
        }
        ticketList.add(ticket)
    }


    return ticketList
}

class Rule(name: String, rangeList: ArrayList<IntRange>) {
    val name = name
    val rangeList = rangeList
}
