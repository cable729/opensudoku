package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class HexColor(
    // e.g. "#00FF00"
    val hexString: String
)