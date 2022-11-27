package com.abhishek.germanPocketDictionary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.abhishek.germanPocketDictionary.ui.destinations.*
import com.ramcosta.composedestinations.spec.*
import com.ramcosta.composedestinations.utils.destination
import com.ramcosta.composedestinations.utils.startDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Realization of [NavGraphSpec] for the app.
 * It uses [TypedDestination] instead of [DestinationSpec].
 * 
 * @see [NavGraphSpec]
 */
public data class NavGraph(
    override val route: String,
    override val startRoute: Route,
    val destinations: List<Destination>,
    override val nestedNavGraphs: List<NavGraph> = emptyList()
): NavGraphSpec {
    override val destinationsByRoute: Map<String, Destination> = destinations.associateBy { it.route }
}

/**
 * If this [Route] is a [Destination], returns it
 *
 * If this [Route] is a [NavGraph], returns its
 * start [Destination].
 */
public val Route.startAppDestination: Destination
    get() = startDestination as Destination

/**
 * Finds the [Destination] correspondent to this [NavBackStackEntry].
 * Some [NavBackStackEntry] are not [Destination], but are [NavGraph] instead.
 * If you want a method that works for both, use [route] extension function instead.
 *
 * Use this ONLY if you're sure your [NavBackStackEntry] corresponds to a [Destination],
 * for example when converting from "current NavBackStackEntry", since a [NavGraph] is never
 * the "current destination" shown on screen.
 */
public fun NavBackStackEntry.appDestination(): Destination {
    return destination() as Destination
}

/**
 * Emits the currently active [Destination] whenever it changes. If
 * there is no active [Destination], no item will be emitted.
 */
public val NavController.appCurrentDestinationFlow: Flow<Destination>
    get() = currentBackStackEntryFlow.map { it.appDestination() }

/**
 * Gets the current [Destination] as a [State].
 */
@Composable
public fun NavController.appCurrentDestinationAsState(): State<Destination?> {
    return appCurrentDestinationFlow.collectAsState(initial = null)
}

// region deprecated APIs

/**
 * If this [Route] is a [Destination], returns it
 *
 * If this [Route] is a [NavGraph], returns its
 * start [Destination].
 */
@Deprecated(
    message = "Api will be removed! Use `startAppDestination` instead.",
    replaceWith = ReplaceWith("startAppDestination")
)
public val Route.startDestination: Destination
    get() = startDestination as Destination

/**
 * Finds the destination correspondent to this [NavBackStackEntry] in the root NavGraph, null if none is found
 * or if no route is set in this back stack entry's destination.
 */
@Deprecated(
    message = "Api will be removed! Use `appDestination()` instead.",
    replaceWith = ReplaceWith("appDestination()")
)
public val NavBackStackEntry.navDestination: Destination?
    get() = appDestination()

/**
 * Finds the destination correspondent to this [NavBackStackEntry] in [navGraph], null if none is found
 * or if no route is set in this back stack entry's destination.
 */
@Deprecated(
    message = "Api will be removed! Use `appDestination()` instead.",
    replaceWith = ReplaceWith("appDestination")
)
public fun NavBackStackEntry.navDestination(navGraph: NavGraph = NavGraphs.root): Destination? {
    @Suppress("DEPRECATION")
    return destination(navGraph) as Destination
}

/**
 * Finds the destination correspondent to this [NavBackStackEntry] in [navGraph], null if none is found
 * or if no route is set in this back stack entry's destination.
 */
 @Deprecated(
     message = "Api will be removed! Use `appDestination()` instead.",
     replaceWith = ReplaceWith("appDestination")
 )
public fun NavBackStackEntry.appDestination(navGraph: NavGraph = NavGraphs.root): Destination? {
    @Suppress("DEPRECATION")
    return destination(navGraph) as Destination
}

// endregion
