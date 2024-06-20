package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class CellValue(
    val value: Int // no support for letters, etc... I think.
)