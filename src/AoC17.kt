import java.io.File

fun main() {
    println("Starting Day 17 A Test 1")
    calculateDay17PartA("Input/2020_Day17_A_Test1")
    println("Starting Day 17 A Real")
    calculateDay17PartA("Input/2020_Day17_A")


    println("Starting Day 17 B Test 1")
    calculateDay17PartB("Input/2020_Day17_A_Test1")
    println("Starting Day 17 B Real")
    calculateDay17PartB("Input/2020_Day17_A")
}

fun calculateDay17PartA(file: String): Int {
    var activeCubes = readDay17Data(file)
    val cycles = 6
    for (i in 0 until cycles) {
        var newActiveCubes = mutableSetOf<Cube>()
        val generatedMesh = generateMesh(activeCubes)

        for (cube in generatedMesh) {
            val amountNeighbors = countNeighbors(cube, activeCubes)
            if (cube in activeCubes && amountNeighbors in 2 until 4) {
                newActiveCubes.add(cube)
            }
            if (cube !in activeCubes && amountNeighbors == 3) {
                newActiveCubes.add(cube)
            }
        }
        activeCubes = newActiveCubes
    }


    println("amount of actives cubes is ${activeCubes.size}")
    return activeCubes.size
}

fun countNeighbors(cube: Cube, activeCubes: Set<Cube>): Int {
    var amountNeighbors = 0
    val x = cube.x
    val y = cube.y
    val z = cube.z

    for (xAdd in -1 until 2) {
        for (yAdd in -1 until 2) {
            for (zAdd in -1 until 2) {
                if (!(xAdd == 0 && yAdd == 0 && zAdd == 0) && activeCubes.contains(Cube(x + xAdd, y + yAdd, z + zAdd))) {
                    amountNeighbors++
                }

            }
        }
    }
    return amountNeighbors
}

fun countNeighbors4(cube: Cube4, activeCubes: Set<Cube4>): Int {
    var amountNeighbors = 0
    val x = cube.x
    val y = cube.y
    val z = cube.z
    val w = cube.w

    for (xAdd in -1 until 2) {
        for (yAdd in -1 until 2) {
            for (zAdd in -1 until 2) {
            for (wAdd in -1 until 2) {
                if (!(xAdd == 0 && yAdd == 0 && zAdd == 0 && wAdd==0) && activeCubes.contains(Cube4(x + xAdd, y + yAdd, z + zAdd,w +wAdd))) {
                    amountNeighbors++
                }
            }

            }
        }
    }
    return amountNeighbors
}

fun generateMesh(activeCubes: Set<Cube>): MutableSet<Cube> {
    var cubeMesh = activeCubes.toMutableSet()

    for (cube in activeCubes) {
        val x = cube.x
        val y = cube.y
        val z = cube.z

        for (xAdd in -1 until 2) {
            for (yAdd in -1 until 2) {
                for (zAdd in -1 until 2) {
                    cubeMesh.add(Cube(x + xAdd, y + yAdd, z + zAdd))

                }
            }
        }
    }

    return cubeMesh

}

fun generateMesh4(activeCubes: Set<Cube4>): MutableSet<Cube4> {
    var cubeMesh = activeCubes.toMutableSet()

    for (cube in activeCubes) {
        val x = cube.x
        val y = cube.y
        val z = cube.z
        val w = cube.w

        for (xAdd in -1 until 2) {
            for (yAdd in -1 until 2) {
                for (zAdd in -1 until 2) {
                for (wAdd in -1 until 2) {
                    cubeMesh.add(Cube4(x + xAdd, y + yAdd, z + zAdd, w+wAdd))
                }

                }
            }
        }
    }

    return cubeMesh

}



fun calculateDay17PartB(file: String): Int {
    var activeCubes = readDay17Data4(file)
    val cycles = 6
    for (i in 0 until cycles) {
        var newActiveCubes = mutableSetOf<Cube4>()
        val generatedMesh = generateMesh4(activeCubes)

        for (cube in generatedMesh) {
            val amountNeighbors = countNeighbors4(cube, activeCubes)
            if (cube in activeCubes && amountNeighbors in 2 until 4) {
                newActiveCubes.add(cube)
            }
            if (cube !in activeCubes && amountNeighbors == 3) {
                newActiveCubes.add(cube)
            }
        }
        activeCubes = newActiveCubes
    }


    println("amount of actives cubes is ${activeCubes.size}")
    return activeCubes.size
}

fun readDay17Data(input: String): Set<Cube> {
    var activeCubes = mutableSetOf<Cube>()

    val readLines = File(localdir + input).readLines()

    for (lineIndex in readLines.indices) {
        val line = readLines[lineIndex]
        for (charIndex in line.indices)
            if (line[charIndex] == '#') {
                activeCubes.add(Cube(charIndex, lineIndex, 0))
            }

    }

    return activeCubes
}


fun readDay17Data4(input: String): Set<Cube4> {
    var activeCubes = mutableSetOf<Cube4>()

    val readLines = File(localdir + input).readLines()

    for (lineIndex in readLines.indices) {
        val line = readLines[lineIndex]
        for (charIndex in line.indices)
            if (line[charIndex] == '#') {
                activeCubes.add(Cube4(charIndex, lineIndex, 0,0))
            }

    }

    return activeCubes
}

class Cube(x: Int, y: Int, z: Int) {
    val x = x
    val y = y
    val z = z

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Cube) return false
        return x == other.x && y == other.y && z == other.z
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 71 * result + z
        return result
    }
}

class Cube4(x: Int, y: Int, z: Int, w:Int) {
    val x = x
    val y = y
    val z = z
    val w = w

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Cube4) return false
        return x == other.x && y == other.y && z == other.z && w == other.w
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        result = 31 * result + w
        return result
    }
}
