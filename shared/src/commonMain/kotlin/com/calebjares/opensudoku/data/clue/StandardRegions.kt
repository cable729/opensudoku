package com.calebjares.opensudoku.data.clue

import com.calebjares.opensudoku.data.CellAddress

/** The nine standard Sudoku regions. */
enum class StandardRegions(
    /**
     * The number identifier of the region, as defined by a phone pad or num pad.
     *
     * E.g. 1 is top-left and 9 is bottom-right.
     */
    val number: Int,
    val region: RectangularRegion,
) {
    TOP_LEFT(number = 1,
        RectangularRegion(topLeft = CellAddress(1, 1), bottomRight = CellAddress(3, 3))
    ),
    TOP_MIDDLE(number = 2,
        RectangularRegion(topLeft = CellAddress(1, 4), bottomRight = CellAddress(3, 6))
    ),
    TOP_RIGHT(number = 3,
        RectangularRegion(topLeft = CellAddress(1, 7), bottomRight = CellAddress(3, 9))
    ),
    MIDDLE_LEFT(number = 4,
        RectangularRegion(topLeft = CellAddress(4, 1), bottomRight = CellAddress(6, 3))
    ),
    MIDDLE_MIDDLE(number = 5,
        RectangularRegion(topLeft = CellAddress(4, 4), bottomRight = CellAddress(6, 6))
    ),
    MIDDLE_RIGHT(number = 6,
        RectangularRegion(topLeft = CellAddress(4, 7), bottomRight = CellAddress(6, 9))
    ),
    BOTTOM_LEFT(number = 7,
        RectangularRegion(topLeft = CellAddress(7, 1), bottomRight = CellAddress(9, 3))
    ),
    BOTTOM_MIDDLE(number = 8,
        RectangularRegion(topLeft = CellAddress(7, 4), bottomRight = CellAddress(9, 6))
    ),
    BOTTOM_RIGHT(number = 9,
        RectangularRegion(topLeft = CellAddress(7, 7), bottomRight = CellAddress(9, 9))
    );
}