fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Error! No filename given!")
        return
    }

    val problem = Parser(args[0]).parse()
    if (problem == null) {
        println("Error in file format!")
        return
    } else {
        val (cnf, numVars) = problem
        val solution = Solver().Solve(cnf, numVars)
        println(solution)
    }
    }