fun main() {
    fun part1(input: List<String>): Long {
        return getSum(input) { nums: List<Long> -> recursion(nums) }
    }

    fun part2(input: List<String>): Long {
        return getSum(input) { nums: List<Long> -> recursion2(nums) }
    }

    val output = part1(readInput("../resources/Day09"))
    val output2 = part2(readInput("../resources/Day09"))
    println(output)
    println(output2)
}

fun getSum(input:List<String>, function: (nums: List<Long>) -> Long): Long{
    var sum = 0L
    for(line in input){
        val nums = parseLine(line)
        sum += function(nums)
    }

    return sum
}

fun parseLine(input: String): List<Long>{
    return input.split(" ").map { e -> e.toLong() }
}

fun recursion(input: List<Long>): Long{
    if(isAllZeroes(input)) return 0

    return recursion(getDif(input)) + input[input.size - 1]
}

fun recursion2(input: List<Long>): Long{
    if(isAllZeroes(input)) return 0

    return input[0] - recursion2(getDif(input))
}

fun getDif(input: List<Long>): List<Long>{
    val list = ArrayList<Long>(input.size - 1)

    for(i in 1..<input.size){
        list.add(input[i] - input[i-1])
    }

    return list
}

fun isAllZeroes(input: List<Long>): Boolean{
    var b = true
    for(n in input){
        if(n != 0L){
            b = false
            break
        }
    }
    return b
}
