package com.calebjares.opensudoku.data.id

import kotlin.jvm.JvmInline

/**
 * The unique ID that identifies the sudokupad app puzzle.
 *
 * E.g. for https://sudokupad.app/jgdFdqhmn2, it would be "jgdFdqhmn2".
 */
@JvmInline
value class SudokuPadPuzzleId(override val id: String): PuzzleId