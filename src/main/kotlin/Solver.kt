class Solver {
    fun Solve(cnf: List<Clause>, numVars: Int): Array<Boolean>? {
        if (cnf.any { it.isEmpty() }) {
            return null
        }

        val resolvent: MutableList<Clause> = cnf.toMutableList()
        val resolventSet: MutableSet<Clause> = cnf.toMutableSet()
        var i = 0
        while (i < resolvent.size) {
            for (j in (i + 1).until(resolvent.size)) {
                val posInINegInJ = resolvent[i].positive
                    .filter { it in resolvent[j].negative }

                if (posInINegInJ.isNotEmpty()) {
                    val intersection = posInINegInJ.first()
                    val newPositive = resolvent[i].positive
                        .minus(intersection)
                        .union(resolvent[j].positive)

                    val newNegative = resolvent[j].negative
                        .minus(intersection)
                        .union(resolvent[i].negative)

                    val newClause = Clause(newPositive, newNegative)
                    if (newClause.isEmpty()) {
                        return null
                    }

                    if (newClause.positive
                            .filter { it in newClause.negative }
                            .isNotEmpty()
                        || resolventSet.contains(newClause)) {

                        continue
                    }

                    resolvent.add(newClause)
                    resolventSet.add(newClause)
                }

                val posInJNegInI = resolvent[j].positive
                    .filter { it in resolvent[i].negative }

                if (posInJNegInI.isNotEmpty()) {
                    val intersection = posInJNegInI.first()
                    val newPositive = resolvent[j].positive
                        .minus(intersection)
                        .union(resolvent[i].positive)

                    val newNegative = resolvent[i].negative
                        .minus(intersection)
                        .union(resolvent[j].negative)

                    val newClause = Clause(newPositive, newNegative)
                    if (newClause.isEmpty()) {
                        return null
                    }

                    if (newClause.positive
                            .filter { it in newClause.negative }
                            .isNotEmpty()
                        || resolventSet.contains(newClause)) {

                        continue
                    }

                    resolvent.add(newClause)
                    resolventSet.add(newClause)
                }
            }

            i++
        }

        val solution = Array(numVars) { false }
        val isSet = Array(numVars) { false }
        for (k in 0.until(numVars)) {
            var minClauseId = -1
            var minUnset = -1
            for (j in 0.until(resolvent.size)) {
                val clause = resolvent[j]
                if (clause.positive.map { solution[it - 1] }.count{ it } != 0
                    || clause.negative.map { !solution[it - 1] && isSet[it - 1] }
                        .count{ it } != 0) {

                    continue
                }

                val unset = clause.positive.filter { !isSet[it - 1] }.size
                    + clause.negative.filter { !isSet[it - 1] }.size

                if (minUnset == -1 || minUnset > unset) {
                    minUnset = unset
                    minClauseId = j
                }
            }

            if (minUnset == -1) {
                break
            }

            val minClause = resolvent[minClauseId]
            var succeeded = false
            for (variablePlusOne in minClause.positive) {
                val variable = variablePlusOne - 1
                if (!isSet[variable]) {
                    solution[variable] = true
                    isSet[variable] = true
                    succeeded = true
                    break
                }
            }

            if (!succeeded) {
                for (variablePlusOne in minClause.negative) {
                    val variable = variablePlusOne - 1
                    if (!isSet[variable]) {
                        isSet[variable] = true
                        break
                    }
                }
            }
        }

        return solution
    }
}
