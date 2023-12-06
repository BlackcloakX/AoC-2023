fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0]
                .split(":        ")[1]
                .split("     ")
                .map { e -> e.toInt() }

        val distances = input[1]
                .split(":   ")[1]
                .split("   ")
                .map { e -> e.toInt() }

        var res = 1
        for(j in times.indices){
            var valid = 0

            for(i in 1..<times[j]){
                if((times[j] - i) * i > distances[j]){
                    valid++
                }
            }
            res *= valid
        }

        return res
    }

    fun part2(input: List<String>): Long {
        val time = input[0]
                .split(":")[1].trim()
                .split(" ")
                .map { e -> e.trim() }
                .fold(""){acc: String, s: String -> acc + s }
                .toLong()

        val distance = input[1]
            .split(":")[1].trim()
            .split(" ")
            .map { e -> e.trim() }
            .fold(""){acc: String, s: String -> acc + s }
            .toLong()

        var first:Long = 0
        for(i in 1..<time){
            if((time - i) * i > distance){
                first = i
                break
            }
        }
        var last:Long = 0
        for(i in time-1 downTo 1){
            if((time - i) * i > distance){
                last = i
                break
            }
        }

        return last - first +1
    }


    val input = part1(readInput("../resources/Day06"))
    val input2 = part2(readInput("../resources/Day06"))
    println(input)
    println(input2)
}
