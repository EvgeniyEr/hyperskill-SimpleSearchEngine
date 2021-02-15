package search

fun main() {
    println("Enter the number of people:")
    val qtyOfLines = readLine()!!.toInt()
    val lines = arrayOfNulls<String>(qtyOfLines)

    println("Enter all people:")
    for (i in 0 until qtyOfLines) {
        lines[i] = readLine()!!
    }

    println()
    println("Enter the number of search queries:")
    val qtyOfSearch = readLine()!!.toInt()

    for (i in 0 until qtyOfSearch) {
        println()
        println("Enter data to search people:")
        val dataToSearch = readLine()!!.toUpperCase()

        println()
        println("Found people:")
        var indexes = mutableListOf<Int>()
        for (j in lines.indices) {
            if (lines[j]!!.toUpperCase().contains(dataToSearch)) {
                indexes.add(j)
            }
        }
        if (!indexes.isEmpty()) {
            for (k in indexes) {
                println(lines[k])
            }

        } else {
            println("No matching people found.")
        }
        indexes.clear()
    }
}