package com.calebjares.opensudoku.data

/** An attempt to solve a puzzle. */
data class PuzzleAttempt(
    val puzzleDefinition: PuzzleDefinition,
    val solveState: PuzzleSolveState
)