import java.io.File

fun main() {
    //testsDay6()
    println("Starting Day 6 A Test 1")
    calculateDay6PartA("Input/2020_Day6_A_Test1")
    println("Starting Day 6 A Real")
    calculateDay6PartA("Input/2020_Day6_A")


    println("Starting Day 6 B Test 1")
    calculateDay6PartB("Input/2020_Day6_A_Test1")
    println("Starting Day 6 B Real")
    calculateDay6PartB("Input/2020_Day6_A")
}

fun testsDay6(){

}

fun calculateDay6PartA(file: String): Int {
    val day6Data = readDay6Data(file, false)
    var answerCount = 0
    for (answer in day6Data) {
        answerCount += answer.size
        println(answer)
    }
    println("$answerCount is amount of 'yes' answers-")
    return answerCount
}


fun calculateDay6PartB(file: String): Int {
    val day6Data = readDay6Data(file, true)
    var answerCount = 0
    for (answer in day6Data) {
        answerCount += answer.size
        println(answer)
    }
    println("$answerCount is amount of 'yes' answers-")
    return answerCount
}

fun readDay6Data(input: String, union: Boolean): MutableList<Set<Char>> {
    val answerList: MutableList<Set<Char>> = ArrayList()

    val lines = File(localdir + input).readLines()
    var checkedAnswers: Set<Char> = mutableSetOf()

    if(union) {
        checkedAnswers = checkedAnswers + "abcdefghijklmnopqrstuvwxyz".toCharArray().toSet()
    }
    for (line in lines) {
        if (line.isBlank()) {
            answerList.add(checkedAnswers)
            checkedAnswers = mutableSetOf()
            if(union) {
                checkedAnswers = checkedAnswers + "abcdefghijklmnopqrstuvwxyz".toCharArray().toSet()
            }
        } else {
            val information = line.toCharArray()
            if(union){
                checkedAnswers = checkedAnswers.intersect(information.toSet())

            }else {
                checkedAnswers = checkedAnswers + information.toSet()
            }
        }
    }
    answerList.add(checkedAnswers)
    return answerList
}


