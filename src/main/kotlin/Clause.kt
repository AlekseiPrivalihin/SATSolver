class Clause(rawClause: List<Int>) {
    val positive: MutableSet<Int> = mutableSetOf()
    val negative: MutableSet<Int> = mutableSetOf()

    init {
        for (i in rawClause) {
            if (i < 0) {
                negative.add(-i)
            } else {
                positive.add(i)
            }
        }
    }

    fun isEmpty(): Boolean {
        return positive.isEmpty() && negative.isEmpty()
    }

    fun mergeWith(clause: Clause) {
        negative.addAll(clause.negative)
        positive.addAll(clause.positive)
    }

    fun clear() {
        negative.clear()
        positive.clear()
    }
}