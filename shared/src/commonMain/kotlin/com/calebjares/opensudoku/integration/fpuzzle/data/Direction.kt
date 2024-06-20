package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Direction {
    @SerialName("UR") UP_RIGHT,
    @SerialName("DR") DOWN_RIGHT,
    @SerialName("DL") DOWN_LEFT,
    @SerialName("UL") UP_LEFT,
}