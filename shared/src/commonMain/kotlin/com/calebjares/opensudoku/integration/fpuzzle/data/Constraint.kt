package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.Serializable

@Serializable
data class Constraint(
    val lines: List<Line>? = null,
    val cell: Cell? = null, // address?
    val cells: List<CellAddress>? = null,
    val cloneCells: List<CellAddress>? = null, // address?
    val direction: Direction? = null,
    val value: String? = null,
    val values: List<String>? = null,
)

