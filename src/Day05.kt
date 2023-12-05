class RangePair(private val startSource:Long, private val startDestination:Long, private val range: Long){
    private val dif = startSource - startDestination

    fun getNext(input: Long): Long{
        return input - dif
    }

    fun getSource(): Long { return startSource }
    fun getDestination(): Long { return startDestination }
    fun getRange(): Long { return range }
}
fun main() {
    fun fillHashMap(
        startIdx: Int,
        nextIdx: Int,
        input: List<String>,
        map: HashMap<Long, RangePair>
    ) {
        for (i in startIdx + 1..<nextIdx - 1) {
            val tmp = input[i].split(" ")
            val range = tmp[2].toLong()

            map[tmp[1].toLong()] = RangePair(tmp[1].toLong(), tmp[0].toLong(), range)
        }
    }

    fun propagate(
        seeds: List<Long>,
        seedMap: HashMap<Long, RangePair>
    ): List<Long> {
        return seeds.map { e ->
            run{
                val tmp = seedMap.values
                var res = e;
                for (v in tmp) {
                    if (e in v.getSource()..<v.getSource() + v.getRange()) {
                        res = v.getNext(e)
                        break
                    }
                }

                res
            }
        }
    }

    fun part1(input: List<String>): Long {
        var seeds = input[0]
            .split(": ")[1]
            .split(" ")
            .map { e -> e.toLong() }

        val seedIdx = input.indexOfFirst { e -> e.startsWith("seed-to-soil") }
        val soilIdx = input.indexOfFirst { e -> e.startsWith("soil-to-fertilizer") }
        val fertIdx = input.indexOfFirst { e -> e.startsWith("fertilizer-to-water") }
        val waterIdx = input.indexOfFirst { e -> e.startsWith("water-to-light") }
        val lightIdx = input.indexOfFirst { e -> e.startsWith("light-to-temperature") }
        val tempIdx = input.indexOfFirst { e -> e.startsWith("temperature-to-humidity") }
        val humidIdx = input.indexOfFirst { e -> e.startsWith("humidity-to-location") }

        val seedMap = HashMap<Long, RangePair>()
        val soilMap = HashMap<Long, RangePair>()
        val fertMap = HashMap<Long, RangePair>()
        val waterMap = HashMap<Long, RangePair>()
        val lightMap = HashMap<Long, RangePair>()
        val tempMap = HashMap<Long, RangePair>()
        val humidMap = HashMap<Long, RangePair>()

        fillHashMap(seedIdx, soilIdx, input, seedMap)
        fillHashMap(soilIdx, fertIdx, input, soilMap)
        fillHashMap(fertIdx, waterIdx, input, fertMap)
        fillHashMap(waterIdx, lightIdx, input, waterMap)
        fillHashMap(lightIdx, tempIdx, input, lightMap)
        fillHashMap(tempIdx, humidIdx, input, tempMap)
        fillHashMap(humidIdx, input.size + 1, input, humidMap)

        seeds = propagate(seeds, seedMap)
        seeds = propagate(seeds, soilMap)
        seeds = propagate(seeds, fertMap)
        seeds = propagate(seeds, waterMap)
        seeds = propagate(seeds, lightMap)
        seeds = propagate(seeds, tempMap)
        seeds = propagate(seeds, humidMap)

        return seeds.min()
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    val input = part1(readInput("../resources/Day05"))
    val input2 = part2(readInput("../resources/Day05"))
    println(input)
    println(input2)
}
