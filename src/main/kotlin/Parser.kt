import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

class Parser(filename: String) {
    private val bufferedReader: BufferedReader = File(filename).bufferedReader()

    fun parse(): Pair<List<Clause>, Int>? {
        val parametersString = skipComments()
        if (!parametersString.startsWith("p cnf")) {
            return null
        }

        val params = parametersString.dropWhile { c: Char -> !c.isDigit() }
        val (numVars, numClauses) = params.split(" ").map { s -> s.toInt() }

        val cnf = mutableListOf<Clause>()
        for (i in 1..numClauses) {
            val rawClause = bufferedReader.readLine()
                .split(" ")
                .filter {it.isNotEmpty()}
                .map { s -> s.toInt() }

            if (rawClause.last() != 0
                || rawClause.count { it == 0 } != 1
                || rawClause.count {abs(it) > numVars} != 0) {
                return null
            }

            cnf.add(Clause(rawClause.dropLast(1)))
        }

        return Pair(cnf, numVars)
    }

    private fun skipComments(): String {
        var inputString = bufferedReader.readLine()
        while (inputString[0] == 'c') {
            inputString = bufferedReader.readLine()
        }

        return inputString
    }
}
