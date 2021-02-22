package search

import java.io.File

fun main(args: Array<String>) {
    val people = File(args[1]).readLines()
    val searchEngine = SearchEngine(people)
    searchEngine.run()
}

class SearchEngine(val people: List<String>) {

    private val invertedIndex = getInvertedIndex(people)
    private var currentStrategy = SearchingStrategies.ALL

    enum class SearchingStrategies {
        ALL, ANY, NONE
    }

    fun run() {
        while (true) {
            printMenu()
            val menuItemNumber = readLine()!!.toInt()

            when (menuItemNumber) {
                0 -> {
                    break
                }
                1 -> {
                    println()
                    println("Select a matching strategy: ALL, ANY, NONE")

                    try {
                        currentStrategy = SearchingStrategies.valueOf(readLine()!!.toUpperCase())
                    } catch (e: IllegalArgumentException) {
                        println("Invalid search strategy")
                        continue
                    }

                    println()
                    println("Enter a name or email to search all suitable people.")
                    val dataToSearch = readLine()!!.toUpperCase().split(" ")
                    var indexes = mutableListOf<Int>()

                    if (dataToSearch.isEmpty()) {
                        printOfFoundPeople(indexes)
                        continue
                    }

                    when (currentStrategy) {
                        SearchingStrategies.ALL -> {
                            // Ищем только строки, которые содержат сразу все слова из запроса.
                            if (invertedIndex.containsKey(dataToSearch[0])) {
                                indexes = invertedIndex[dataToSearch[0]]!!
                                for (word in dataToSearch) {
                                    var nextIndexes: MutableList<Int> = if (invertedIndex.containsKey(word)) {
                                        invertedIndex[word]!!
                                    } else {
                                        emptyList<Int>().toMutableList()
                                    }
                                    indexes = indexes.intersect(nextIndexes).toMutableList()
                                }
                            }
                        }
                        SearchingStrategies.ANY -> {
                            // Ищем строки, содержащие хотя бы одно слово из запроса.
                            for (word in dataToSearch) {
                                if (invertedIndex.containsKey(word)) {
                                    indexes.addAll(invertedIndex[word]!!)
                                }
                            }
                            indexes = indexes.distinct().toMutableList()
                        }
                        SearchingStrategies.NONE -> {
                            // Ищем строки, которые вообще не содержат слов из запроса
                            var notShowingIndices = mutableSetOf<Int>()
                            for (word in dataToSearch) {
                                if (invertedIndex.containsKey(word)) {
                                    notShowingIndices.addAll(invertedIndex[word]!!)
                                }
                            }
                            indexes = invertedIndex.values.flatten().distinct().filter {
                                !notShowingIndices.contains(it)
                            }.toMutableList()
                        }
                    }
                    printOfFoundPeople(indexes)
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

    private fun getInvertedIndex(people: List<String>): MutableMap<String, MutableList<Int>> {
        val invertedIndex = mutableMapOf<String, MutableList<Int>>()
        for (lineIndex in people.indices) {
            val words = people[lineIndex].split(" ")
            for (j in words.indices) {
                val word = words[j].toUpperCase()
                if (invertedIndex[word] == null) {
                    invertedIndex[word] = mutableListOf(lineIndex)
                } else {
                    invertedIndex[word]?.add(lineIndex)
                }
            }
        }
        return invertedIndex
    }

    private fun printMenu() {
        println()
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
    }

    private fun printOfFoundPeople(indexes: MutableList<Int>) {
        println()
        if (indexes.isEmpty()) {
            println("No matching people found.")
        } else {
            val qtyOfPeopleFound = indexes.size
            println("$qtyOfPeopleFound persons found:")
            for (lineIndex in indexes) {
                println(people[lineIndex])
            }
        }
    }
}