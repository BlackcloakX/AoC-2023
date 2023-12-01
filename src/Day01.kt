fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for(s in input){
            val first = s.first { c -> c.isDigit() }
            val last = s.last { c -> c.isDigit() }

            result += first.toString().toInt() * 10 + last.toString().toInt()
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val numbers = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        var result = 0
        for(s in input){
            val first = s.findAnyOf(numbers)
            val last = s.findLastAnyOf(numbers)
            val first_n = s.indexOfFirst { c -> c.isDigit() }
            val last_n = s.indexOfLast { c -> c.isDigit() }

            val num1: String = if(first != null && first.first < first_n) {
                getNum(first.second)
            } else{
                s.first { c -> c.isDigit() }.toString()
            }

            val num2: String = if(last != null && last.first > last_n){
                getNum(last.second)
            } else{
                s.last { c -> c.isDigit() }.toString()
            }

            result += num1.toInt() * 10 + num2.toInt()
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

fun getNum(s: String): String{
    return when(s){
        "zero" -> "0"
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> {throw IllegalArgumentException()}
    }
}
