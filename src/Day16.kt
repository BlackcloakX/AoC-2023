fun main() {
    fun part1(input: List<String>, inputBeam: Beam?): Int {
        val array = input.map { e -> e.toList() }.map{l -> l.map { e -> Tile(e, false) }}
        val beamList = ArrayList<Beam>()
        if(inputBeam != null){
            beamList.add(inputBeam)
        }
        else{
            beamList.add(Beam(MyPair(0,0), Direction.EAST, true))
        }

        var count = 0
        var prevSum = 0
        while(beamList.isNotEmpty()){
            for(i in beamList.indices){
                beamList[i].moveToNextPosition(array, beamList)
            }
            beamList.removeIf { !it.active }

            if(count >= 10){
                count = 0
                var sum = 0
                for(y in array){
                    for(x in y){
                        if(x.energized) sum++
                    }
                }
                if(prevSum == sum){
                    break
                }
                else{
                    prevSum = sum
                }
            }
            count++
        }

        count = 0
        for(y in array){
            for(x in y){
                if(x.energized) count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var max = 0

        for(x in input[0].indices){
            val s = part1(input, Beam(MyPair(0, x), Direction.SOUTH, true))
            val n = part1(input, Beam(MyPair(input.size-1, x), Direction.NORTH, true))
            if(s > max) max = s
            if(n > max) max = n
        }
        for(y in input.indices){
            val s = part1(input, Beam(MyPair(y, 0), Direction.EAST, true))
            val n = part1(input, Beam(MyPair(y, input[0].length-1), Direction.WEST, true))
            if(s > max) max = s
            if(n > max) max = n
        }
        return max
    }


    val output = part1(readInput("../resources/Day16"), null)
    val output2 = part2(readInput("../resources/Day16"))
    println(output)
    println(output2)
}

private data class Tile(var type: Char, var energized: Boolean){}

private class Beam(var position: MyPair<Int, Int>, var direction: Direction, var active: Boolean){
    fun moveToNextPosition(array: List<List<Tile>>, beamList: MutableList<Beam>){
        array[position.first][position.second].energized = true
        when(TileDir(array[position.first][position.second].type,direction)){
            TileDir('.', Direction.NORTH), TileDir('|', Direction.NORTH) -> {
                if(active && position.first - 1 >= 0){ position.first -= 1 }
                else if(active) active = false
            }
            TileDir('/', Direction.NORTH) -> {
                if(active && position.second + 1 < array[0].size){
                    position.second += 1
                    direction = Direction.EAST
                }
                else if(active) active = false
            }
            TileDir('\\', Direction.NORTH) -> {
                if(active && position.second - 1 >= 0){
                    position.second -= 1
                    direction = Direction.WEST
                }
                else if(active) active = false
            }
            TileDir('-', Direction.NORTH) -> {
                if(active){
                    if(position.second + 1 < array[0].size) beamList.add(Beam(MyPair(position.first, position.second + 1), Direction.EAST, true))
                    if(position.second - 1 >= 0){
                        position.second -= 1
                        direction = Direction.WEST
                    } else active = false
                }
            }
            // South
            TileDir('.', Direction.SOUTH), TileDir('|', Direction.SOUTH) -> {
                if(active && position.first + 1 < array.size){ position.first += 1 }
                else if(active) active = false
            }
            TileDir('\\', Direction.SOUTH) -> {
                if(active && position.second + 1 < array[0].size){
                    position.second += 1
                    direction = Direction.EAST
                }
                else if(active) active = false
            }
            TileDir('/', Direction.SOUTH) -> {
                if(active && position.second - 1 >= 0){
                    position.second -= 1
                    direction = Direction.WEST
                }
                else if(active) active = false
            }
            TileDir('-', Direction.SOUTH) -> {
                if(active){
                    if(position.second + 1 < array[0].size) beamList.add(Beam(MyPair(position.first, position.second + 1), Direction.EAST, true))
                    if(position.second - 1 >= 0){
                        position.second -= 1
                        direction = Direction.WEST
                    } else active = false
                }
            }
            // West
            TileDir('.', Direction.WEST), TileDir('-', Direction.WEST) -> {
                if(active && position.second - 1 >= 0){ position.second -= 1 }
                else if(active) active = false
            }
            TileDir('/', Direction.WEST) -> {
                if(active && position.first + 1 < array.size){
                    position.first += 1
                    direction = Direction.SOUTH
                }
                else if(active) active = false
            }
            TileDir('\\', Direction.WEST) -> {
                if(active && position.first - 1 >= 0){
                    position.first -= 1
                    direction = Direction.NORTH
                }
                else if(active) active = false
            }
            TileDir('|', Direction.WEST) -> {
                if(active){
                    if(position.first + 1 < array[0].size) beamList.add(Beam(MyPair(position.first + 1, position.second), Direction.SOUTH, true))
                    if(position.first - 1 >= 0){
                        position.first -= 1
                        direction = Direction.NORTH
                    } else active = false
                }
            }
            // East
            TileDir('.', Direction.EAST), TileDir('-', Direction.EAST) -> {
                if(active && position.second + 1 < array[0].size){ position.second += 1 }
                else if(active) active = false
            }
            TileDir('/', Direction.EAST) -> {
                if(active && position.first - 1 >= 0){
                    position.first -= 1
                    direction = Direction.NORTH
                }
                else if(active) active = false
            }
            TileDir('\\', Direction.EAST) -> {
                if(active && position.first + 1 < array.size){
                    position.first += 1
                    direction = Direction.SOUTH
                }
                else if(active) active = false
            }
            TileDir('|', Direction.EAST) -> {
                if(active){
                    if(position.first + 1 < array[0].size) beamList.add(Beam(MyPair(position.first + 1, position.second), Direction.SOUTH, true))
                    if(position.first - 1 >= 0){
                        position.first -= 1
                        direction = Direction.NORTH
                    } else active = false
                }
            }

        }
    }
}

data class MyPair<T, R>(var first: T, var second: R){}

private data class TileDir(val type: Char, val direction: Direction){}

//Direction.NORTH -> if(active && position.first - 1 >= 0){
//    position.first -= 1
//}
//Direction.WEST -> if(active && position.second - 1 >= 0){
//    position.second -= 1
//}
//Direction.SOUTH -> if(active && position.first + 1 < array.size){
//    position.first += 1
//}
//Direction.EAST -> if(active && position.second + 1 < array[0].size){
//    position.first += 1
//}
