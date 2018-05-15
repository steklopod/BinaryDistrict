# Chess problem solution #

### Problem description ###

The problem is to find all unique configurations of a set of normal chess pieces on a chess board with dimensions M×N where none of the pieces is in a position to take any of the others. Providing the number of results is useful, but not enough to complete the assignment. Assume the colour of the piece does not matter, and that there are no pawns among the pieces.

### How do use it ###

Clone it and then use `sbt run` to run console app or the benchmarks.

### Algorithms ###

* bruteforce.BruteForceSolver - simply enumerates all the positions of pieces in the field. It's too slow.
* dfs.withoutcache.DFSSolver - just DFS search. It's best compromise for less memory usage and speed.
* dfs.withoutcache.ParallelDFSSolver - Parallel `DFSSolver` using fork-join recursive task.
In solutions in 'withcache' package I use bloom filter from guava for reduce recheck already seen states.
* dfs.withcache.DFSSolver - DFS search with exclusion of already seen steps.
* dfs.withcache.DFSSolverWithRotations - DFS search with exclusion of already seen rotated steps. The best solver in many not large cases.
* dfs.withcache.WeakDFSSolverWithRotations - DFSSolverWithRotations without results deduplication. It's not universal for very big cases, but it can solve challenge problems fast anyway. Be careful with it :) Please, configure expected insertions number of bloom filter in big problems case.
* dfs.withcache.ParallelDFSSolverWithRotations - Parallel `DFSSolverWithRotations` using fork-join recursive task.

### Results ###

## 7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight

There are result times for `ChallengeProblemSolutionBenchmark` JMH benchmark on my MacBook Pro (using JDK 8u31 on Intel Core i5-4278U):

```
[info] ChallengeProblemSolutionBenchmark.dfsWithCacheSolver                         avgt    6  26,612 ± 0,830   s/op
[info] ChallengeProblemSolutionBenchmark.dfsWithRotationsAndCacheSolver             avgt    6  26,549 ± 1,350   s/op
[info] ChallengeProblemSolutionBenchmark.dfsWithoutCacheSolver                      avgt    6  52,199 ± 3,043   s/op
[info] ChallengeProblemSolutionBenchmark.parallelDfsWithCacheSolver                 avgt    6  24,968 ± 0,810   s/op
[info] ChallengeProblemSolutionBenchmark.weakDfsWithRotationsWithCacheSolver        avgt    6  18,466 ± 0,185   s/op
```

The total number of unique configurations is 3063828.

## 6×9 board with 2 Kings, 1 Queen, 1 Bishop, 1 Rook and 1 Knight

Only WeakDFSSolverWithRotations with cache can solve this task with 4Gb head limit. There are result time for `SecondChallengeProblemSolutionBenchmark` JMH benchmark:

```
[info] SecondChallengeProblemSolutionBenchmark.weakDfsWithRotationsWithCacheSolver  avgt    2  65,730           s/op
```

The total number of unique configurations is 20136752.

## 5x5 board, 2 Rooks and 4 Knights

There are results for a small problem.

```
SolversBenchmarks.dfsWithCacheSolver                   avgt   15  0,060 ± 0,002   s/op
SolversBenchmarks.dfsWithRotationsAndCacheSolver       avgt   15  0,061 ± 0,002   s/op
SolversBenchmarks.dfsWithoutCacheSolver                avgt   15  0,130 ± 0,004   s/op
SolversBenchmarks.parallelDfsSolver                    avgt   15  0,136 ± 0,008   s/op
SolversBenchmarks.parallelDfsWithCacheSolver           avgt   15  0,051 ± 0,003   s/op
SolversBenchmarks.weakDfsWithRotationsWithCacheSolver  avgt   15  0,058 ± 0,002   s/op
```

The total number of unique configurations is 1402.