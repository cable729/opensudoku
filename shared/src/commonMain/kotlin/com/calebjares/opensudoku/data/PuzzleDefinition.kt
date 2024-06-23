package com.calebjares.opensudoku.data

import com.calebjares.opensudoku.data.clue.Clue
import com.calebjares.opensudoku.data.id.PuzzleId

/**
 * The immutable definition of a puzzle that does not change when moves are made.
 */
data class PuzzleDefinition(
    val puzzleId: PuzzleId,

    val width: Int,
    val height: Int,

    val clues: Set<Clue>

    // TODO: ruleset, visuals
)