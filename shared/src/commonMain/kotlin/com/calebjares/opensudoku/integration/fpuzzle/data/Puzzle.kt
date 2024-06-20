package com.calebjares.opensudoku.integration.fpuzzle.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A sudoku puzzle in f-puzzle format.
 *
 * Reverse engineered from f-puzzles.com. From the `Import Export.js` script, we find the definition
 * of a puzzle.
 */
@Serializable
data class Puzzle(
    /** The length of each side of the puzzle. */
    val size: Int,

    // INFO

    /** The name of the puzzle */
    val title: String? = null,
    /** The author who created the puzzle */
    val author: String? = null,
    val customRuleset: String? = null,
    /** Whether to highlight conflicts. I'm not sure why this is in the puzzle definition. */
    val highlightConflicts: Boolean? = null, // todo: default false and not make nullable?

    // GRID

    val grid: List<List<Cell>>,

    // BOOLEAN CONSTRAINTS

    /** The positive diagonal constraint (extra region from bottom left to top right). */
    @SerialName("diagonal+")
    val diagonalPlus: Boolean? = null, // todo: default false and not make nullable?
    /** The minus diagonal constraint (extra region from top left to bottom right). */
    @SerialName("diagonal-")
    val diagonalMinus: Boolean? = null, // todo: default false and not make nullable?
    /** No two cells a chess knight's move apart can be equal. */
    @SerialName("antiknight")
    val antiKnight: Boolean? = null, // todo: default false and not make nullable?
    /** No two cells a chess king's move apart can be equal. */
    @SerialName("antiking")
    val antiKing: Boolean? = null, // todo: default false and not make nullable?
    /**
     * Cells that appear in the same relative position in their region must contain different
     * digits.
     */
    @SerialName("disjointgroups")
    val disjointGroups: Boolean? = null, // todo: default false and not make nullable?
    /** Neighboring cells must not be consecutive. */
    val nonconsecutive: Boolean? = null, // todo: default false and not make nullable?

    // TOOL CONSTRAINTS

    @SerialName("extraregion")
    val extraRegion: List<Constraint>? = null,
    val odd: List<Constraint>? = null,
    val even: List<Constraint>? = null,
    val thermometer: List<Constraint>? = null,
    @SerialName("slowthermometer")
    val slowThermometer: List<Constraint>? = null,
    val palindrome: List<Constraint>? = null,
    @SerialName("killercage")
    val killerCage: List<Constraint>? = null,
    @SerialName("littlekillersum")
    val littleKillerSum: List<Constraint>? = null,
    @SerialName("sandwichsum")
    val sandwichSum: List<Constraint>? = null,
    val difference: List<Constraint>? = null,
    val ratio: List<Constraint>? = null,
    val clone: List<Constraint>? = null,
    val arrow: List<Constraint>? = null,
    @SerialName("betweenline")
    val betweenLine: List<Constraint>? = null,
    val minimum: List<Constraint>? = null,
    val maximum: List<Constraint>? = null,
    val xv: List<Constraint>? = null,
    val quadruple: List<Constraint>? = null,

    // COSMETICS
    val text: List<Cosmetic>? = null,
    val circle: List<Cosmetic>? = null,
    val rectangle: List<Cosmetic>? = null,
    val line: List<Cosmetic>? = null,
    val cage: List<Cosmetic>? = null,
)