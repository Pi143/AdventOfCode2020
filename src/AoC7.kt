import java.io.File

fun main() {
    println("Starting Day 7 A Test 1")
    calculateDay7PartA("Input/2020_Day7_A_Test1")
    println("Starting Day 7 A Real")
    calculateDay7PartA("Input/2020_Day7_A")


    println("Starting Day 7 B Test 1")
    calculateDay7PartB("Input/2020_Day7_A_Test1")
    println("Starting Day 7 B Real")
    calculateDay7PartB("Input/2020_Day7_A")
}

fun calculateDay7PartA(file: String): Int {
    val Day7Data = readDay7Data(file)
    var answerCount = 0

    for (bag in Day7Data) {
        if(checkContains(bag, Bag("shiny gold", null), Day7Data, 20)){
            answerCount++
        }
    }

    println(answerCount)
    return answerCount
}

fun checkContains(outerBag: MutableMap.MutableEntry<String, List<BagContent>?>, innerBag: Bag, bagDefinitions: MutableMap<String, List<BagContent>?>, depth: Int): Boolean {
    //Sets with objects don't exactly work, but do the deed here
    val containmentSet: MutableSet<BagContent> = HashSet()
    if (outerBag.value == null) {
        return false
    }

    for (bagContent in outerBag.value!!) {
        containmentSet.add(bagContent)
    }

    for (i in 1..depth) {
        val tempContainmentSet: MutableSet<BagContent> = HashSet()
        tempContainmentSet.addAll(containmentSet)
        for (bagContent in containmentSet) {
            val bagDef = bagDefinitions[bagContent.name]
            if (bagDef?.size!! > 0) {
                tempContainmentSet.addAll(bagDef)
            }
        }
        containmentSet.addAll(tempContainmentSet)
    }

    for (bag in containmentSet) {
        if (bag.name == innerBag.name) {
            return true
        }
    }
    return false
}


fun calculateDay7PartB(file: String): Int? {
    val Day7Data = readDay7Data(file)
    val cost = calculateBagCostMap(Day7Data, 10)["shiny gold"]
    println("shiny gold costs $cost")

    return cost
}

fun calculateBagCostMap(bagDefinitions: MutableMap<String, List<BagContent>?>, depth: Int): MutableMap<String, Int?> {
    val contentCostMap: MutableMap<String,Int?> = HashMap()

    for(entry in bagDefinitions){
        contentCostMap[entry.key] = null
    }

    for (i in 1..depth) {

        for (costDef in contentCostMap) {
            val bagDef = bagDefinitions[costDef.key]

//            val bagDef = bagDefinitions.find { it.name == costDef.key }
            if (bagDef?.size == 0) {
                contentCostMap[costDef.key] = 0
            } else {
                var innerCost = 0
                //all bags inside have cost identified
                if (bagDef != null) {
                    for (innerBagDefs in bagDef) {
                        val currentBagCostData = contentCostMap[innerBagDefs.name]

                        if (currentBagCostData != null) {
                            innerCost += innerBagDefs.amount * (currentBagCostData + 1)
                        }

                    }
                }
                // cost added up + number of bags
                contentCostMap[costDef.key] = innerCost
            }
        }

    }

    return contentCostMap

}



fun readDay7Data(input: String): MutableMap<String, List<BagContent>?> {
    val bagMap: MutableMap<String,List<BagContent>?> = HashMap()

    File(localdir + input).forEachLine { bagMap[parseBagDefinition(it).name] =parseBagDefinition(it).content  }

    return bagMap
}

fun parseBagDefinition(line: String): Bag {
    val name = line.split(" bags contain ")[0]
    val contentDefinition = line.split(" bags contain ")[1]
    val contentList: MutableList<BagContent> = ArrayList()

    if (contentDefinition == "no other bags.") {
        return Bag(name, contentList)
    }

    val defSplit = contentDefinition.split(" bag(s)?(, |\\.)?".toRegex())
    for (def in defSplit) {
        if (def.isNotBlank()) {
            val nameContentBag = def.split("\\d ".toRegex())[1]
            val amount = "\\d".toRegex().find(def)?.value?.toInt()
            contentList.add(BagContent(nameContentBag, amount = amount!!))
        }
    }

    return Bag(name, contentList)
}

class Bag(name: String, content: List<BagContent>?) {
    val name = name
    val content = content
}

class BagContent(name: String, amount: Int) {
    val name = name
    val amount = amount
}



