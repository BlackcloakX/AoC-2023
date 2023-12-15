fun main() {
    fun part1(input: List<String>): Int {
        val list = input[0].split(",")

        var sum = 0
        for(s in list){
            sum += getHash(s)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val map: HashMap<Int, MutableList<Pair<String,Int>>> = HashMap()
        for(i in 0..255){
            map[i] = ArrayList()
        }
        val list = input[0].split(",")

        for(e in list){
            val line = e.split("-", "=")
            println("${line[0]}  ${line[1]}")
            val pair: Pair<String, Int> = Pair(line[0], if(line[1].isEmpty()) -1 else line[1].toInt())

            if(e.contains("=")){
                val ll: MutableList<Pair<String, Int>> = map[getHash(pair.first)]!!
                val entry = ll.indexOfFirst { p -> p.first == pair.first }
                if( entry == -1){
                    ll.add(pair)
                }
                else{
                    ll[entry] = pair
                    if(pair.second == -1) throw IllegalArgumentException()
                }
            }
            else if(e.contains("-")){
                map[getHash(pair.first)]!!.removeIf { p -> p.first == pair.first}
            }
        }
        var sum = 0
        for(i in 0..255){
            for(entry in map[i]!!.indices){
                sum += (i+1) * (entry + 1) * map[i]!![entry].second
            }
        }

        return sum
    }


    val output = part1(readInput("../resources/Day15"))
    val output2 = part2(readInput("../resources/Day15"))
    println(output)
    println(output2)
}

private fun getHash(string: String): Int{
    var sum = 0
    for(c in string){
        sum = ((sum + c.code) * 17) % 256
    }

    return sum
}
