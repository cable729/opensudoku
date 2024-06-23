package com.calebjares.opensudoku.data.clue

import com.calebjares.opensudoku.data.CellAddress

/** A set of [cells], in which digits cannot repeat. */
// TODO: regions could be abstracted away so that multiple clues, like "digits in [Region] may not repeat" and "values in [Region] may not repeat", could both use regions.
sealed interface Region: Clue {
    val cells: Set<CellAddress>
}