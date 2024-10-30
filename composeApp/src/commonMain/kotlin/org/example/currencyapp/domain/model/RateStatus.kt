package org.example.currencyapp.domain.model

import androidx.compose.ui.graphics.Color
import org.example.currencyapp.ui.theme.freshColor
import org.example.currencyapp.ui.theme.staleColor

enum class RateStatus(
    val title: String,
    val color: Color
) {
    Idle(
        title = "Rates",
        color = Color.White
    ),
    Fresh(
        title = "Fresh rates",
        color = freshColor
    ),
    Stale(
        title = "Rates are not fresh",
        color = staleColor
    )
}