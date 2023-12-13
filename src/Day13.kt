fun main() {
    fun part1(input: List<String>): Int {
        val list = findEmptyLines(input)
        val list2 = getBlocksOfListOfString(input, list)

        val listRows = ArrayList<Int>()
        val listCols = ArrayList<Int>()

        list2.forEach {
            val array = parseToUniverseArray(it)
            val array_T = transpose(array)
            for(y in 0..<array.size-1){
                if(compareLines(y, y+1, array)){
                    listRows.add(y+1)
                }
            }
            for(y in 0..<array_T.size-1){
                if(compareLines(y, y+1, array_T)){
                    listCols.add(y+1)
                }
            }
        }
        return listCols.sum() + 100 * listRows.sum()
    }

    fun part2(input: List<String>): Int {
        val list = findEmptyLines(input)
        val list2 = getBlocksOfListOfString(input, list)

        val listRows = ArrayList<Int>()
        val listCols = ArrayList<Int>()

        list2.forEach {
            val array = parseToUniverseArray(it)
            val array_T = transpose(array)
            for(y in 0..<array.size-1){
                if(compareSmudgedLines(y, y+1, array, 0)){
                    listRows.add(y+1)
                }
            }
            for(y in 0..<array_T.size-1){
                if(compareSmudgedLines(y, y+1, array_T, 0)){
                    listCols.add(y+1)
                }
            }
        }
        return listCols.sum() + 100 * listRows.sum()
    }


    val output = part1(readInput("../resources/Day13"))
    val output2 = part2(readInput("../resources/Day13"))
    println(output)
    println(output2)
}
fun compareLines(line1: Int, line2: Int, array: List<List<Char>>): Boolean{
    if(line1 < 0 || line2 >= array.size) return true
    if(array[line1] != array[line2]) return false
    return compareLines(line1-1, line2+1, array)
}

fun compareSmudgedLines(index1: Int, index2: Int, array: List<List<Char>>, faults: Int): Boolean{
    if(faults > 1) return false
    if(index1 < 0 || index2 >= array.size) return faults == 1
    if(!exactMatch(array[index1], array[index2]) && !almostMatch(array[index1], array[index2])) return false

    return compareSmudgedLines(index1-1, index2+1, array, if(almostMatch(array[index1], array[index2])) faults + 1 else faults)
}

fun exactMatch(line1: List<Char>, line2: List<Char>): Boolean{
    var difs = 0

    for(i in line1.indices){
        if(line1[i] != line2[i]) difs++
    }

    return difs == 0
}

fun almostMatch(line1: List<Char>, line2: List<Char>): Boolean{
    var difs = 0

    for(i in line1.indices){
        if(line1[i] != line2[i]) difs++
    }

    return difs == 1
}

fun findEmptyLines(input: List<String>): List<Int>{
    val list = ArrayList<Int>()

    for(i in input.indices){
        if(input[i] == "")
            list.add(i)
    }

    return list
}

fun getBlocksOfListOfString(input: List<String>, delimiter: List<Int>): List<List<String>>{
    val result = ArrayList<List<String>>()

    var prev = 0
    for(i in delimiter.indices){
        result.add(input.subList(prev, delimiter[i]))
        prev = delimiter[i] + 1
    }
    result.add(input.subList(prev, input.size))

    return result
}


