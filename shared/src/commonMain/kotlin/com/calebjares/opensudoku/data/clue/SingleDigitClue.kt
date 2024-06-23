package com.calebjares.opensudoku.data.clue

import com.calebjares.opensudoku.data.CellAddress

/**
 * A [Clue] that is about a single cell.
 *
 * Note: this serves no purpose currently, but may be useful in the future.
 */
interface SingleDigitClue : Clue {
    val target: CellAddress
}