import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var universeArray = parseToUniverseArray(input)
        universeArray = expandUniverse(universeArray)

        val galaxyList = findGalaxies(universeArray)

        var sum = 0
        for(first in galaxyList){
            for(second in galaxyList){
                if(first == second){
                    continue
                }
                sum += abs(first.first - second.first) + abs(first.second - second.second)
            }
        }

        return sum/2
    }

    fun part2(input: List<String>): Long {
        var universeArray = parseToUniverseArray(input)
        universeArray = expandUniverseX1M(universeArray)

        val galaxyList = findGalaxies(universeArray)

        var sum: Long = 0
        for(first in galaxyList){
            for(second in galaxyList){
                if(first == second){
                    continue
                }
                sum += getDistance(universeArray, first, second)
            }
        }

        return sum/2
    }


    val input = part1(readInput("../resources/Day11"))
    val input2 = part2(readInput("../resources/Day11"))
    println(input)
    println(input2)
}

fun parseToUniverseArray(input: List<String>): List<List<Char>> {
    return input.map { e -> e.toList() }
}

fun expandUniverse(map: List<List<Char>>): List<List<Char>>{
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

    var muteMap = map.toMutableList()

    for(i in rows.indices){
        muteMap.add(rows[i] + i, List(muteMap[0].size){'.'})
    }
    muteMap = transpose(muteMap).toMutableList()
    for(i in cols.indices){
        muteMap.add(cols[i] + i, List(muteMap[0].size){'.'})
    }

    return transpose(muteMap)
}

fun expandUniverseX1M(map: List<List<Char>>): List<List<Char>>{
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
    var muteMap = map.toMutableList()
    for(row in rows){
        muteMap[row] = List(muteMap[0].size){'X'}
    }
    muteMap = transpose(muteMap).toMutableList()
    for(col in cols){
        muteMap[col] = List(muteMap[0].size){'X'}
    }

    return transpose(muteMap)
}

fun findGalaxies(array: List<List<Char>>): List<Pair<Int,Int>>{
    val list = ArrayList<Pair<Int,Int>>()

    for(y in array.indices){
        for(x in array[y].indices){
            if(array[y][x] == '#') list.add(Pair(y,x))
        }
    }

    return list
}

fun getDistance(array: List<List<Char>>, first: Pair<Int,Int>, second: Pair<Int,Int>): Long{
    var sum: Long = 0
    var lowerB = if(first.first <= second.first) first.first else second.first
    var higherB = if(first.first > second.first) first.first else second.first

    for(i in lowerB..< higherB){
        sum += if(array[i][0] == 'X') 1000000
               else 1
    }

    lowerB = if(first.second <= second.second) first.second else second.second
    higherB = if(first.second > second.second) first.second else second.second

    for(i in lowerB..< higherB){
        sum += if(array[0][i] == 'X') 1000000
        else 1
    }

    return sum
}

fun <T> transpose(array: List<List<T>>): List<List<T>>{
    val transposed = ArrayList<List<T>>(array[0].size)

    for(x in array[0].indices){
        val row = ArrayList<T>(array.size)
        for(y in array.indices){
            row.add(array[y][x])
        }
        transposed.add(row)
    }

    return transposed
}
