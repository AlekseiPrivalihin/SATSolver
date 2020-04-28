class Solver {
    fun Solve(cnf: List<Clause>, numVars: Int): MutableList<Boolean> {
        for (i in 0.until(cnf.size)) {
            for (j in (i + 1).until(cnf.size)) {
                val posInINegInJ = cnf[i].positive.filter { it in cnf[j].negative }
                if (posInINegInJ.isNotEmpty()) {
                    val intersection = posInINegInJ.first()
                    cnf[j].negative.remove(intersection)
                    cnf[i].positive.remove(intersection)
                    cnf[j].mergeWith(cnf[i])
                    cnf[i].clear()
                    break
                }

                val posInJNegInI = cnf[j].positive.filter { it in cnf[i].negative }
                if (posInJNegInI.isNotEmpty()) {
                    val intersection = posInJNegInI.first()
                    cnf[i].negative.remove(intersection)
                    cnf[j].positive.remove(intersection)
                    cnf[j].mergeWith(cnf[i])
                    cnf[i].clear()
                    break
                }

            }
        }

        val solution = MutableList(numVars) { false }
        for (clause in cnf) {
            if (clause.isEmpty()
                || clause.positive.filter { it in clause.negative }.isNotEmpty()) {

                continue
            }

            for (varToSet in clause.positive) {
                solution[varToSet - 1] = true
            }
        }

        return solution
    }
}
