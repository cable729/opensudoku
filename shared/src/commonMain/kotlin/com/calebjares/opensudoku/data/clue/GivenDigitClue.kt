package com.calebjares.opensudoku.data.clue

import com.calebjares.opensudoku.data.CellAddress

data class GivenDigitClue(
    override val target: CellAddress,
    val digit: Int
) : SingleDigitClue