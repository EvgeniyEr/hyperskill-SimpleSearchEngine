package search

import java.io.File

fun main(args: Array<String>) {
    val people = File(args[1]).readLines()
    val invertedIndex = getInvertedIndex(people)

    while (true) {
        println()
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
        val menuItemNumber = readLine()!!.toInt()

        when (menuItemNumber) {
            0 -> {
                break
            }
            1 -> {
                println()
                println("Enter a name or email to search all suitable people.")
                val dataToSearch = readLine()!!.toUpperCase()

                val qtyOfPeopleFound = invertedIndex[dataToSearch]?.size ?: 0
                if (qtyOfPeopleFound == 0) {
                    println("No matching people found.")
                } else {
                    println("$qtyOfPeopleFound persons found:")
                    for (lineIndex in invertedIndex[dataToSearch]!!){
                        println(people[lineIndex])
                    }
                }
            }
            2 -> {
                println("=== List of people ===")
                for (line in people) {
                    println(line)
                }
            }
            else -> {
                println()
                println("Incorrect option! Try again.")
            }
        }
    }
    println()
    println("Bye!")
}

fun getInvertedIndex(people: List<String>): MutableMap<String, MutableList<Int>> {
    val invertedIndex = mutableMapOf<String, MutableList<Int>>()
    for (lineIndex in people.indices) {
        val words = people[lineIndex].split(" ")
        for (j in words.indices) {
            var word = words[j].toUpperCase()
            if (invertedIndex[word] == null) {
                invertedIndex[word] = mutableListOf(lineIndex)
            } else {
                invertedIndex[word]!!.add(lineIndex)
            }
        }
    }
    return invertedIndex
}