package com.calebjares.opensudoku.data

import com.calebjares.opensudoku.data.move.Move

/**
 * The current solving state of a puzzle, including moves taken and moves undone.
 *
 * Given a list of [Move]s, you can construct the current state of a puzzle.
 */
data class PuzzleSolveState(
    /** A chronological list of [Move]s, starting with the first move. */
    val previousMoves: List<Move>,
    /**
     * A chronological list of [Move]s that have been undone, starting with the last undone move.
     *
     * Applying this list in order will return the puzzle solve to the state it was at before any
     * undoes.
     */
    val undoneMoves: List<Move>,
)