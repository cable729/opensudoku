package com.calebjares.opensudoku.data.id

import kotlin.jvm.JvmInline

/** A puzzle ID for fakes. */
@JvmInline
value class FakePuzzleId(override val id: String): PuzzleId