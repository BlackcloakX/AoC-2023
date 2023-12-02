import kotlin.math.max

fun main() {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    fun part1(input: List<String>): Int {
        var sum = 0
        for(s in input){
            var isValid = true

            val line = s.split(":")
            if(line.size > 2) throw RuntimeException("Too many slices!")
            val cubeLine = line[1]

            cubeLine.split(";").forEach { expr ->
                var r = 0
                var g = 0
                var b = 0
                expr.split(",").forEach{ expr2 ->
                    val numColor = expr2.trimStart().split(" ")
                    if(numColor.size > 2) throw RuntimeException("Too many slices!")
                    when(numColor[1]){
                        "red" -> r += numColor[0].toInt()
                        "green" -> g+= numColor[0].toInt()
                        "blue" -> b+= numColor[0].toInt()
                    }
                }
                if(r > maxRed || g > maxGreen || b > maxBlue) isValid = false
            }
            if(isValid) sum += line[0].split(" ")[1].toInt()
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for(s in input){
            var minR = 0
            var minG = 0
            var minB = 0
            val line = s.split(":")
            if(line.size > 2) throw RuntimeException("Too many slices!")
            val cubeLine = line[1]

            cubeLine.split(";").forEach { expr ->
                var r = 0
                var g = 0
                var b = 0
                expr.split(",").forEach{ expr2 ->
                    val numColor = expr2.trimStart().split(" ")
                    if(numColor.size > 2) throw RuntimeException("Too many slices!")
                    when(numColor[1]){
                        "red" -> r += numColor[0].toInt()
                        "green" -> g+= numColor[0].toInt()
                        "blue" -> b+= numColor[0].toInt()
                    }
                }
                minR = max(minR, r)
                minG = max(minG, g)
                minB = max(minB, b)
            }

            sum += minR * minG * minB
        }

        return sum
    }

    val result = part1(readInput("../resources/Day02"))
    val result2 = part2(readInput("../resources/Day02"))
    println(result)
    println(result2)
}
