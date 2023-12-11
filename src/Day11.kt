fun main() {
    fun part1(input: List<String>): Int {
        val universeMap = parseToUniverseMap(input)
        universeMap.forEach { println(it) }

        expandUniverse(universeMap)

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    val input = part1(readInput("../resources/Day11"))
    val input2 = part2(readInput("../resources/Day11"))
    println(input)
    println(input2)
}

fun parseToUniverseMap(input: List<String>): List<List<Char>>{
    return input.map { e -> e.toList() }
}

fun expandUniverse(map: List<List<Char>>){
    val rows = ArrayList<Int>()
    val cols = ArrayList<Int>()

    for(y in map.indices){
        var universeFound = false
        for(x in map[y].indices){
            if(map[y][x] == '#') universeFound = true
        }
        if(!universeFound) rows.add(y)
    }
    for(x in map[0].indices){
        var universeFound = false
        for(y in map.indices){
            if(map[y][x] == '#') universeFound = true
        }
        if(!universeFound) cols.add(x)
    }
    println(rows)
    println(cols)
}

fun transpose(array: List<List<Char>>){
    val transposed = ArrayList<ArrayList<Char>>()
    for(y in array.indices){
        for(x in array[y].indices){
            val tmp = array
        }
    }
}
