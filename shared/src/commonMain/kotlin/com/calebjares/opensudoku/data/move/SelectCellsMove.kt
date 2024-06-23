package com.calebjares.opensudoku.data.move

import com.calebjares.opensudoku.data.CellAddress

/** A [Move] that modifies the cell selection. */
data class SelectCellsMove(val cells: Set<CellAddress>): Move {
    companion object {
        /** A [SelectCellsMove] that clears the selection. */
        val CLEAR_SELECTION = SelectCellsMove(cells = emptySet())
    }
}