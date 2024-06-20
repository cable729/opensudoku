package com.calebjares.opensudoku.integration.sudokupad

/**
 *
 * Reverse engineered from the site https://app.crackingthecryptic.com. The sources show the scripts
 * fetching the puzzle from https://app.crackingthecryptic.com/api/puzzle/olig7yj3gj.
 *
 * The source eventually points to `loadFPuzzle` in `puzpatcher.bundle.js`.
 *
 * The fpuzzle in question is from f-puzzles.com. Following that and looking at the
 * `Import Export.js` script, we find the definition of a puzzle.
 */
data class SudokupadPuzzle(
    val x: String
)

