package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class CellAddress(val address: String) {
    // e.g. R5C5
}