fun main() {
    fun part1(input: List<String>): Int {
        val array = input.map { e -> e.toMutableList() }

        for(y in array.indices){
            for(x in array[0].indices){
                if(array[y][x] == 'O'){
                    val currentPos = Pair(y,x)
                    val spot = findNextOpenSpot(currentPos, array, Direction.NORTH)
                    moveRock(currentPos, spot, array)
                }
            }
        }

        var sum = 0

        for(y in array.indices){
            for(x in array.indices){
                if(array[y][x] == 'O') sum += array.size - y
            }
        }

        return sum
    }
// real solution needs some kind of cycle-detection
    fun part2(input: List<String>): Int {
        val array = input.map { e -> e.toMutableList() }

        var prevSum = -1
        var sum = 0

        var i = 0
        while(i < 1000 || i % 10 == 0){

            prevSum = sum
            sum = 0

            for(y in array.indices){
                for(x in array.indices){
                    if(array[y][x] == 'O') sum += array.size - y
                }
            }

            println("i: $i, sum: $sum")
            tiltCycle(array)
            i++
        }

        return sum
    }


    val output = part1(readInput("../resources/Day14"))
    val output2 = part2(readInput("../resources/Day14"))
    println(output)
    println(output2)
    println(1000000000 % 9)
    println(1000 % 9)
}

enum class Direction{
    NORTH, WEST, SOUTH, EAST;
}

private fun tiltCycle(array: List<MutableList<Char>>){
    var xrange = array[0].indices as IntProgression
    var yrange = array.indices as IntProgression

    for(direction in Direction.entries){
        when(direction){
            Direction.SOUTH -> yrange = array.size-1 downTo 0
            Direction.EAST -> xrange = array[0].size-1 downTo 0
            else -> {}
        }
        for(y in yrange){
            for(x in xrange){
                if(array[y][x] == 'O'){
                    val currentPos = Pair(y,x)
                    val spot = findNextOpenSpot(currentPos, array, direction)
                    moveRock(currentPos, spot, array)
                }
            }
        }
    }
}

private fun findNextOpenSpot(currentPos: Pair<Int,Int>, array: List<List<Char>>, direction: Direction): Pair<Int,Int>{
    var dy = 0
    var dx = 0
    when(direction){
        Direction.NORTH -> dy = -1
        Direction.SOUTH -> dy = 1
        Direction.EAST -> dx = 1
        Direction.WEST -> dx = -1
    }

    var i = 1
    while(currentPos.first + i * dy >= 0 && currentPos.first + i * dy < array.size &&
          currentPos.second + i * dx >= 0 && currentPos.second + i * dx < array[0].size &&
          array[currentPos.first + i * dy][currentPos.second + i * dx] == '.'){
        i++
    }
    i--

    return Pair(currentPos.first + i * dy, currentPos.second + i * dx)
}

private fun moveRock(currentPos: Pair<Int, Int>, targetPos: Pair<Int,Int>, array: List<MutableList<Char>>){
    if(currentPos == targetPos) return

    array[targetPos.first][targetPos.second] = 'O'
    array[currentPos.first][currentPos.second] = '.'
}
