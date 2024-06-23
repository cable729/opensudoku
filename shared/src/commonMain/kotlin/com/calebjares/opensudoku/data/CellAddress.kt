package com.calebjares.opensudoku.data

/** The cell address, where (1,1) represents the top left. */
data class CellAddress(
    val row: Int,
    val column: Int
)