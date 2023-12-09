fun main() {
    fun part1(input: List<String>): Long {
        var sum = 0L
        for(line in input){
            val nums = parseLine(line)
            sum += recursion(nums)
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for(line in input){
            val nums = parseLine(line)
            sum += recursion2(nums)
        }

        return sum
    }


    val input = part1(readInput("../resources/Day09"))
    val input2 = part2(readInput("../resources/Day09"))
    println(input)
    println(input2)
}

fun parseLine(input: String): List<Long>{
    return input.split(" ").map { e -> e.toLong() }
}

fun getDif(input: List<Long>): List<Long>{
    val list = ArrayList<Long>(input.size - 1)

    for(i in 1..<input.size){
        list.add(input[i] - input[i-1])
    }

    return list
}

fun isAllZeroes(input: List<Long>): Boolean{
    for(n in input){
        if(n != 0L) return false
    }
    return true
}

fun recursion(input: List<Long>): Long{
    if(isAllZeroes(input)) return 0

    return recursion(getDif(input)) + input[input.size - 1]
}

fun recursion2(input: List<Long>): Long{
    if(isAllZeroes(input)) return 0

    return input[0] - recursion2(getDif(input))
}
