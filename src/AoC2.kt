import java.io.File

fun main() {
    println("Starting Day 2 A Test 1")
    calculateDay2PartA("Input/2020_Day2_A_Test1")
    println("Starting Day 2 A Real")
    calculateDay2PartA("Input/2020_Day2_A")


    println("Starting Day 2 B Test 1")
    calculateDay2PartB("Input/2020_Day2_A_Test1")
    println("Starting Day 2 B Real")
    calculateDay2PartB("Input/2020_Day2_A")
}

fun calculateDay2PartA(file: String){
    val day2Data = readDay2Data(file)
    var validEntires = 0
    day2Data.forEach { if(checkValidityPartA(it)){ validEntires++} }
    println("$validEntires are valid.")
}

fun calculateDay2PartB(file: String){
    val day2Data = readDay2Data(file)
    var validEntires = 0
    day2Data.forEach { if(checkValidityPartB(it)){ validEntires++} }
    println("$validEntires are valid.")
}

fun readDay2Data(input: String): MutableList<pwWithConfig> {
    val list = mutableListOf<pwWithConfig>()
    File(localdir + input).forEachLine {
        list.add(stringToPwWithConfig(it)) }
    return list
}

fun stringToPwWithConfig(string: String) : pwWithConfig{
    val split1 = string.split(" ")
    val split1A = split1[0].split("-")
    val minAmount = split1A[0].toInt()
    val maxAmount = split1A[1].toInt()
    val split1B = split1[1].split(":")
    val char = split1B[0].toCharArray()[0]
    val password = split1[2]

    return pwWithConfig(minAmount, maxAmount,char,password)

}

fun checkValidityPartA(pwWithConfig: pwWithConfig): Boolean {
    val count = pwWithConfig.password.count { it == pwWithConfig.char }
    if(pwWithConfig.minAmount <= count && count <= pwWithConfig.maxAmount) {
        return true
    }
    return false
}

fun checkValidityPartB(pwWithConfig: pwWithConfig): Boolean {
    if(pwWithConfig.password[pwWithConfig.minAmount-1] == pwWithConfig.char && pwWithConfig.password[pwWithConfig.maxAmount-1] != pwWithConfig.char ||
        pwWithConfig.password[pwWithConfig.minAmount-1] != pwWithConfig.char && pwWithConfig.password[pwWithConfig.maxAmount-1] == pwWithConfig.char) {
        return true
    }
    return false
}

class pwWithConfig(
    val minAmount : Int,
    val maxAmount: Int,
    val char : Char,
    val password: String
)