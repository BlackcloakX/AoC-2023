fun main() {
    fun part1(input: List<String>): Int {
        val seeds = input[0]
                .split(": ")[1]
                .split(" ")
                .map { e -> e.toLong() }

        val seedIdx = input.indexOfFirst { e -> e.startsWith("seed-to-soil") }
        val soilIdx = input.indexOfFirst{e -> e.startsWith( "soil-to-fertilizer" ) }
        val fertIdx = input.indexOfFirst{e -> e.startsWith( "fertilizer-to-water" ) }
        val waterIdx = input.indexOfFirst{e -> e.startsWith( "water-to-light" ) }
        val lightIdx = input.indexOfFirst{e -> e.startsWith( "light-to-temperature" ) }
        val tempIdx = input.indexOfFirst{e -> e.startsWith( "temperature-to-humidity" ) }
        val humidIdx = input.indexOfFirst{e -> e.startsWith( "humidity-to-location" ) }

        var seedMap = HashMap<Long, Long>()
        var soilMap = HashMap<Long, Long>()
        var fertMap = HashMap<Long, Long>()
        var waterMap = HashMap<Long, Long>()
        var lightMap = HashMap<Long, Long>()
        var tempMap = HashMap<Long, Long>()
        var humidMap = HashMap<Long, Long>()

        for(i in seedIdx+1..<soilIdx - 1){
            val tmp = input[i].split(" ")
            val range = tmp[2].toInt()

            for(j in 0..<range){
                seedMap[tmp[1].toLong() + j] = tmp[0].toLong() + j
            }
        }

        println("$seedIdx $soilIdx $fertIdx $waterIdx $lightIdx $tempIdx $humidIdx")
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    val input = part1(readInput("../resources/Day05"))
    val input2 = part2(readInput("../resources/Day05"))
    println(input)
    println(input2)
}
