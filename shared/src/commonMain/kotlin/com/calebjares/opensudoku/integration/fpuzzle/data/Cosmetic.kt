package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cosmetic(
    val lines: List<Line>? = null,
    val cell: Cell? = null,
    val cells: List<CellAddress>? = null,
    val direction: Direction? = null,
    val value: String? = null,
    @SerialName("baseC")
    val baseColor: HexColor? = null,
    @SerialName("outlineC")
    val outlineColor: HexColor? = null,
    @SerialName("fontC")
    val fontColor: HexColor? = null,
    val size: Float? = null,
    val width: Float? = null,
    // Tool allows angles like 15, 30, 45, etc.
    val angle: Int? = null
)