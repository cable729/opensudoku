package com.calebjares.opensudoku.data

import com.calebjares.opensudoku.data.clue.GivenDigitClue
import com.calebjares.opensudoku.data.clue.StandardRegions
import com.calebjares.opensudoku.data.id.FakePuzzleId
import com.calebjares.opensudoku.data.id.PuzzleId

/** Creates a classic sudoku with the [givenDigits]. */
fun classicSudoku(givenDigits: Set<GivenDigitClue>, puzzleId: PuzzleId = FakePuzzleId("asdf")) = PuzzleDefinition(
    puzzleId = puzzleId,
    width = 9,
    height = 9,
    clues = buildSet {
        addAll(givenDigits)
        addAll(StandardRegions.entries.map { it.region })
    }
)