import java.io.File

fun main() {
    println("Starting Day 4 A Test 1")
    calculateDay4PartA("Input/2020_Day4_A_Test1")
    println("Starting Day 4 A Real")
    calculateDay4PartA("Input/2020_Day4_A")


    println("Starting Day 4 B Test 1")
    calculateDay4PartB("Input/2020_Day4_B_Test1")
    println("Starting Day 4 B Real")
    calculateDay4PartB("Input/2020_Day4_A")
}

fun calculateDay4PartA(file: String) {
    val Day4Data = readDay4Data(file)
    var validPassports = 0
    for (passport in Day4Data) {
        if (isValid(passport, allowNPC = true, validateFields = false)) {
            //println("$passport is valid")
            validPassports++
        } else {
            //println("$passport is NOT valid")
        }
    }
    println("$validPassports passports are valid")
}


fun calculateDay4PartB(file: String) {
    val Day4Data = readDay4Data(file)
    var validPassports = 0
    for (passport in Day4Data) {
        if (isValid(passport, allowNPC = true, validateFields = true)) {
            println("$passport is valid")
            validPassports++
        } else {
            println("$passport is NOT valid")
        }
    }
    println("$validPassports passports are valid")
}

fun isValid(passport: Map<String, String>, allowNPC: Boolean, validateFields: Boolean): Boolean {

    if (!passport.containsKey("byr") || (validateFields && !(validateNumberinRange(
            passport.getValue("byr"),
            1920,
            2002
        )))
    ) {
        println("failed byr")
        return false
    }
    if (!passport.containsKey("iyr") || (validateFields && !(validateNumberinRange(
            passport.getValue("iyr"),
            2010,
            2020
        )))
    ) {
        println("failed iyr")

        return false
    }
    if (!passport.containsKey("eyr") || (validateFields && !(validateNumberinRange(
            passport.getValue("eyr"),
            2020,
            2030
        )))
    ) {        println("failed eyr")

        return false
    }
    if (!passport.containsKey("hgt") || (validateFields && !checkHeight(passport.getValue("hgt")))) {
        println("failed hgt")
        return false
    }
    if (!passport.containsKey("hcl") || (validateFields && !checkHairColor(passport.getValue("hcl")))) {
        println("failed hcl")
        return false
    }
    if (!passport.containsKey("ecl") || (validateFields && !checkEyeColor(passport.getValue("ecl")))) {
        println("failed ecl")
        return false
    }
    if (!passport.containsKey("pid") || (validateFields && !checkPassportId(passport.getValue("pid")))) {
        println("failed pid")
        return false
    }
    if (!passport.containsKey("cid") && !allowNPC) {
        println("failed cid")
        return false
    }
    return true
}

fun checkPassportId(value: String): Boolean {
    return value.matches("([0-9]){9}".toRegex())
}

fun checkEyeColor(value: String): Boolean {
    return value.matches("(amb|blu|brn|gry|grn|hzl|oth)".toRegex())
}

fun checkHairColor(value: String): Boolean {
    return value.matches("#([a-f0-9])*".toRegex())
}

fun checkHeight(value: String): Boolean {
    if (value.endsWith("cm")) {
        return validateNumberinRange(value.split("cm")[0], 150, 193)
    }
    if (value.endsWith("in")) {
        return validateNumberinRange(value.split("in")[0], 59, 76)
    }
    return false
}



fun validateNumberinRange(string: String, min: Int, max: Int): Boolean {
    val inputNumber = string.toInt()
    if (inputNumber in min until max+1) {
        return true
    }
    return false
}

fun readDay4Data(input: String): MutableList<Map<String, String>> {
    val passportList: MutableList<Map<String, String>> = ArrayList()

    val lines = File(localdir + input).readLines()

    var passport: MutableMap<String, String> = mutableMapOf()
    for (line in lines) {
        if (line.isBlank()) {
            passportList.add(passport)
            passport = mutableMapOf()
        } else {
            val information = line.split(" ")
            for (singlePair in information) {
                val keyValue = singlePair.split(":")
                passport[keyValue[0]] = keyValue[1]
            }
        }
    }
    passportList.add(passport)
    return passportList
}
