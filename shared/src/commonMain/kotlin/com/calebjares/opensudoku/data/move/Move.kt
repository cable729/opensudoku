package com.calebjares.opensudoku.data.move

/**
 * An action by the player that modifies the puzzle solve in a significant way that can be
 * snapshot so that it can be undone or redone.
 */
sealed interface Move

