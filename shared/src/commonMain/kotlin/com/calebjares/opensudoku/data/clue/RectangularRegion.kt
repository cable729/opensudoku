package com.calebjares.opensudoku.data.clue

import com.calebjares.opensudoku.data.CellAddress

data class RectangularRegion(
    val topLeft: CellAddress,
    val bottomRight: CellAddress
): Region {
    override val cells: Set<CellAddress>
        get() = TODO("Not yet implemented")
}