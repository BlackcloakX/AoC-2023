fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for(s in input){
            val first = s.first { c -> c.isDigit() }
            val last = s.last { c -> c.isDigit() }

            result += first.digitToInt() * 10 + last.digitToInt()
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val numbers = setOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                             "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        var result = 0
        for(s in input){
            val first = s.findAnyOf(numbers)
            val last = s.findLastAnyOf(numbers)

            val num1: Int = if(first != null) getNum(first.second) else throw NoSuchElementException()
            val num2: Int = if(last != null) getNum(last.second) else throw NoSuchElementException()

            result += num1 * 10 + num2
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("../resources/Day01_test")
//    check(part1(testInput) == 3)

    val input = part1(readInput("../resources/Day01"))
    val input2 = part2(readInput("../resources/Day01"))
    println(input)
    println(input2)
}

fun getNum(s: String): Int{
    return when(s){
        "zero", "0" -> 0
        "one", "1" -> 1
        "two", "2" -> 2
        "three", "3" -> 3
        "four", "4" -> 4
        "five", "5" -> 5
        "six", "6" -> 6
        "seven", "7" -> 7
        "eight", "8" -> 8
        "nine", "9" -> 9
        else -> throw IllegalArgumentException()
    }
}
