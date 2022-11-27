package com.abhishek.germanPocketDictionary.ui

import com.abhishek.germanPocketDictionary.ui.destinations.*

/**
 * Class generated if any Composable is annotated with `@Destination`.
 * It aggregates all [TypedDestination]s in their [NavGraph]s.
 */
public object NavGraphs {

    public val root: NavGraph = NavGraph(
        route = "root",
        startRoute = SplashScreenDestination,
        destinations = listOf(
            AgreementScreenDestination,
			HomeScreenDestination,
			SearchScreenDestination,
			SplashScreenDestination
        )
    )
}