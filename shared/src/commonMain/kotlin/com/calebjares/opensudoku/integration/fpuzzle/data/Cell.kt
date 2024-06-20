package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.Serializable

@Serializable
data class Cell(
    // todo: what is nullable??
    val candidates: List<CellValue>? = null,
    val region: Int? = null,
    val value: CellValue? = null,
    val given: Boolean? = null,
    val cornerPencilMarks: List<CellValue>? = null,
    val centerPencilMarks: List<CellValue>? = null,
    // todo: c ???
    val c: HexColor? = null,
    val highlight: HexColor? = null,
)