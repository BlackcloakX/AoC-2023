class Range(val start: Long, val range: Long) {
    val last = start + range - 1
//    fun getLast(): Long{
//        return last
//    }

    fun isDistinct(other: Range): Boolean{
        return this.last < other.start || this.start > other.last
    }

    override fun toString(): String{
        return "(src: $start, range: $range)"
    }
}

class Mapping(val mapRange:Range, val startDest: Long){
    val dif = startDest - mapRange.start

    fun getOverlap(seed: Range):List<Range>{
        val list = ArrayList<Range>()
        if(mapRange.start <= seed.start && seed.last <= mapRange.last){
            list.add(Range(startDest - mapRange.start + seed.start, seed.range))
        }
        else if(seed.start <= mapRange.start && mapRange.last <= seed.last){
            list.add(Range(startDest, mapRange.range))
            if(seed.start < mapRange.start){
                list.add(Range(seed.start, mapRange.start - seed.start))
            }
            if(seed.last > mapRange.last){
                list.add(Range(mapRange.last + 1, seed.last - mapRange.last))
            }
        }
        else if(seed.start < mapRange.start){
            list.add(Range(startDest, seed.last + 1 - mapRange.start))
            list.add(Range(seed.start, mapRange.start - seed.start))
        }
        else if(seed.last > mapRange.last){
            list.add(Range(startDest - mapRange.start + seed.start, mapRange.last + 1 - seed.start)) // check right length
            list.add(Range(mapRange.last + 1, seed.last - mapRange.last))
        }

        return list
    }

    fun isDistinct(other: Range): Boolean{
        return mapRange.last < other.start || mapRange.start > other.last
    }

    fun getNext(input: Long): Long{
        return input + dif
    }

    override fun toString(): String{
        return "(src: ${mapRange.start}, dest: $startDest, range: ${mapRange.range})"
    }
}
fun main() {
    fun fillHashMap(
        startIdx: Int,
        nextIdx: Int,
        input: List<String>,
        map: HashMap<Long, Mapping>
    ) {
        for (i in startIdx + 1..<nextIdx - 1) {
            val tmp = input[i].split(" ")
            val range = tmp[2].toLong()

            map[tmp[1].toLong()] = Mapping(Range(tmp[1].toLong(), range), tmp[0].toLong())
        }
    }

    fun propagate(
        seeds: List<Long>,
        seedMap: HashMap<Long, Mapping>
    ): List<Long> {
        return seeds.map { e ->
            run{
                val tmp = seedMap.values
                var res = e
                for (v in tmp) {
                    if (e in v.mapRange.start..v.mapRange.last) {
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

        val seedMap = HashMap<Long, Mapping>()
        val soilMap = HashMap<Long, Mapping>()
        val fertMap = HashMap<Long, Mapping>()
        val waterMap = HashMap<Long, Mapping>()
        val lightMap = HashMap<Long, Mapping>()
        val tempMap = HashMap<Long, Mapping>()
        val humidMap = HashMap<Long, Mapping>()

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

    fun applyMap(
        res: ArrayList<Range>,
        seedMap: HashMap<Long, Mapping>,
        tmp: ArrayList<Range>
    ) {
        var i = 0
        while (i < res.size) {
            val r = res[i]
            var untied = true
            for (j in seedMap.values) {
                if (!j.isDistinct(r)) {
                    val tp = j.getOverlap(r)
                    tmp.add(tp[0])
                    untied = false
                    for (k in 1..<tp.size) {
                        res.add(tp[k])
                    }
                }
            }
            if (untied) {
                tmp.add(r)
            }
            i++
        }
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0]
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

        val seedMap = HashMap<Long, Mapping>()
        val soilMap = HashMap<Long, Mapping>()
        val fertMap = HashMap<Long, Mapping>()
        val waterMap = HashMap<Long, Mapping>()
        val lightMap = HashMap<Long, Mapping>()
        val tempMap = HashMap<Long, Mapping>()
        val humidMap = HashMap<Long, Mapping>()

        fillHashMap(seedIdx, soilIdx, input, seedMap)
        fillHashMap(soilIdx, fertIdx, input, soilMap)
        fillHashMap(fertIdx, waterIdx, input, fertMap)
        fillHashMap(waterIdx, lightIdx, input, waterMap)
        fillHashMap(lightIdx, tempIdx, input, lightMap)
        fillHashMap(tempIdx, humidIdx, input, tempMap)
        fillHashMap(humidIdx, input.size + 1, input, humidMap)

        val list = ArrayList<Range>()
        for(i in 0..< seeds.size/2){
            list.add(Range(seeds[2*i], seeds[2*i+1]))
        }

        var res = list
        var tmp = ArrayList<Range>()
        println(seeds)

        val mapList = listOf(seedMap, soilMap, fertMap, waterMap, lightMap, tempMap, humidMap)

        for(map in mapList){
            applyMap(res, map, tmp)
            res = tmp
            tmp = ArrayList<Range>()
        }

        return res.minOf { e -> e.start }
    }


    val input = part1(readInput("../resources/Day05"))
    val input2 = part2(readInput("../resources/Day05"))
    println(input)
    println(input2)
}
