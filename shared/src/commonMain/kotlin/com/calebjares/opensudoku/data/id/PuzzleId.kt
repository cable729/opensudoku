package com.calebjares.opensudoku.data.id

/** A uniquely identified puzzle across any platform. */
sealed interface PuzzleId {
    /** The puzzle's ID */
    val id: String
}

