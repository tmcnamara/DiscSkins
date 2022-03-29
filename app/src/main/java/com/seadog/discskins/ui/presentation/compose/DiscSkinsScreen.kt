package com.seadog.discskins.ui.presentation.compose

enum class DiscSkinsScreen {
    RoundListView,
    RoundView,
    LoadRoundView,
    FeedbackView;

    companion object {
        fun fromRoute(route: String?): DiscSkinsScreen =
            when (route?.substringBefore("/")) {
                RoundListView.name -> RoundListView
                RoundView.name -> RoundView
                LoadRoundView.name -> LoadRoundView
                FeedbackView.name -> FeedbackView
                null -> RoundListView
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}