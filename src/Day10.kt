fun main() {
    // S transition needs to be entered manually in getNextPipe()/getNextPipeNoChange()
    // If part2-result is wrong: Change S transition to the other possibility
    fun part1(input: List<String>): Int {
        val array = parseInputToArray(input)
        Pipe.setArray(array)
        val startPos = getStartingPosition(array)
        var slow = startPos
        var fast = startPos
        var count = 0

        do{
            slow = slow.getNextPipe()
            fast = fast.getNextPipe().getNextPipe()
            count++
        }while(fast != startPos)

        return count
    }

    fun part2(input: List<String>): Int {
        val array = Pipe.getArray()
        var count = 0
        for(line in array){
            var curCount = 0
            var cur: Pipe? = null
            for(p in line){
                if(p.enteredFrom == 'S'){
                    cur = p
                    curCount = 0
                }
                else{
                    if(cur != null && p.enteredFrom == null) curCount++
                    else if(p.getNextPipeNoChange() != null && p.getNextPipeNoChange()!!.enteredFrom == 'N'){
                        count += curCount
                        cur = null
                        curCount = 0
                    }
                }
            }
        }

        return count
    }

    val input = part1(readInput("../resources/Day10"))
    val input2 = part2(readInput("../resources/Day10"))
    println(input)
    println(input2)
}

fun getPipe(x: Int, y: Int, array: List<List<Pipe>>, enteredFrom: Char?): Pipe?{
    if(x < 0 || y < 0 || y >= array.size || x >= array[0].size) return null
    val res = array[array.size - 1 - y][x]
    if(enteredFrom != null) res.enteredFrom = enteredFrom
    return res
}

fun parseInputToArray(input: List<String>): ArrayList<ArrayList<Pipe>>{
    val array = ArrayList<ArrayList<Pipe>>()
    for(y in input.indices){
        val list = ArrayList<Pipe>()
        for(x in input[y].indices){
            list.add(Pipe(x, input.size - 1 - y, input[y][x], null))
        }
        array.add(list)
    }
    return array
}

fun getStartingPosition(array: List<List<Pipe>>): Pipe{
    for(y in array.indices){
        for(x in array[y].indices){
            if(array[y][x].pipeType == 'S') return getPipe(x, array.size - 1 - y, array,null)!!
        }
    }
    throw NoSuchElementException()
}

data class Pipe(val x: Int, val y: Int, val pipeType: Char, var enteredFrom: Char?){
    fun getNextPipe(): Pipe{
        return when(val type = getPipe(x, y, arr, null)!!.pipeType){
            'J' -> if(enteredFrom == 'W') getPipe(x,y+1,arr,'S')!! else getPipe(x-1, y, arr, 'E')!!
            'L' -> if(enteredFrom == 'N') getPipe(x+1,y,arr,'W')!! else getPipe(x, y+1, arr, 'S')!!
            'F' -> if(enteredFrom == 'E') getPipe(x,y-1,arr,'N')!! else getPipe(x+1, y, arr, 'W')!!
            '7' -> if(enteredFrom == 'W') getPipe(x,y-1,arr,'N')!! else getPipe(x-1, y, arr, 'E')!!
            '|' -> if(enteredFrom == 'N') getPipe(x,y-1,arr,'N')!! else getPipe(x, y+1, arr, 'S')!!
            '-' -> if(enteredFrom == 'W') getPipe(x+1,y,arr,'W')!! else getPipe(x-1, y, arr, 'E')!!
            'S' -> getPipe(x, y-1, arr, 'N')!!
            else -> {throw NoSuchElementException(type.toString())}
        }
    }

    fun getNextPipeNoChange(): Pipe?{
        return when(val type = getPipe(x, y, arr, null)?.pipeType){
            null -> null
            'J' -> if(enteredFrom == 'W') getPipe(x,y+1,arr,null) else getPipe(x-1, y, arr, null)
            'L' -> if(enteredFrom == 'N') getPipe(x+1,y,arr,null) else getPipe(x, y+1, arr, null)
            'F' -> if(enteredFrom == 'E') getPipe(x,y-1,arr,null) else getPipe(x+1, y, arr, null)
            '7' -> if(enteredFrom == 'W') getPipe(x,y-1,arr,null) else getPipe(x-1, y, arr, null)
            '|' -> if(enteredFrom == 'N') getPipe(x,y-1,arr,null) else getPipe(x, y+1, arr, null)
            '-' -> if(enteredFrom == 'W') getPipe(x+1,y,arr,null) else getPipe(x-1, y, arr, null)
            'S' -> getPipe(x, y-1, arr, null)
            '.' -> null
            else -> {throw NoSuchElementException(type.toString())}
        }
    }

    companion object{
        var arr = ArrayList<ArrayList<Pipe>>()

        fun setArray(input: ArrayList<ArrayList<Pipe>>){
            arr = input
        }

        fun getArray(): ArrayList<ArrayList<Pipe>>{
            return arr
        }
    }
}
