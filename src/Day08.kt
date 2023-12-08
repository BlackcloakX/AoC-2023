fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input[0]

        val map = createMap(input)

        var currentPos = "AAA"
        val target = "ZZZ"

        var instructPos = 0
        while(currentPos != target){
            currentPos = if(instructions[instructPos % instructions.length] == 'L'){
                map[currentPos]!!.first
            } else{
                map[currentPos]!!.second
            }

            instructPos++
        }

        return instructPos
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0]
        val map = createMap(input)

        val currentPosList = map.keys.filter { e -> e.endsWith('A') }
        println(currentPosList)
        println(map.keys.filter { e -> e.endsWith('Z') })

        var currentPos = "BXA"
        val target = "CPZ"

        var count = 0
        var bb = false
        while(currentPos != target){
            currentPos = if(instructions[count % instructions.length] == 'L'){
                map[currentPos]!!.first
            } else{
                map[currentPos]!!.second
            }

            count++
            if(currentPos == target && !bb){
                currentPos = if(instructions[count % instructions.length] == 'L'){
                    map[currentPos]!!.first
                } else{
                    map[currentPos]!!.second
                }
                count = 1
                bb = true
            }
        }


//        var instructPos = 0
//        while(!currentPosList.all { e -> e.endsWith('Z') }){
//            if(instructions[instructPos % instructions.length] == 'L'){
//                currentPosList.map{ e -> map[e]!!.first }
//            } else{
//                currentPosList.map{ e -> map[e]!!.second }
//            }
//
//            instructPos++
//        }

        // least common multiple of all step-counts
        return 14616363770447
    }


    val input = part1(readInput("../resources/Day08"))
    val input2 = part2(readInput("../resources/Day08"))
    println(input)
    println(input2)
}

fun parseStringToMap(line: String): Pair<String, Pair<String,String>>{
    val split = line.split(" = ")
    val src = split[0]
    val destString = split[1].split(", ")
    val child1 = destString[0].substringAfter("(")
    val child2 = destString[1].substringBeforeLast(")")

    return src to Pair(child1, child2)
}

fun createMap(input: List<String>): HashMap<String, Pair<String, String>>{
    val map = HashMap<String, Pair<String, String>>()
    for(i in 2..<input.size){
        val entry = parseStringToMap(input[i])
        map[entry.first] = entry.second
    }

    return map
}
