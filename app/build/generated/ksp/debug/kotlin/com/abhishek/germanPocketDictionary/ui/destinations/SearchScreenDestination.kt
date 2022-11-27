package com.abhishek.germanPocketDictionary.ui.destinations

import androidx.annotation.RestrictTo
import androidx.compose.runtime.Composable
import com.abhishek.germanPocketDictionary.ui.search.SearchScreen
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.Direction

public object SearchScreenDestination : DirectionDestination {
         
    public operator fun invoke(): Direction = this
    
    @get:RestrictTo(RestrictTo.Scope.SUBCLASSES)
    override val baseRoute: String = "search_screen"

    override val route: String = baseRoute
    
    @Composable
    override fun DestinationScope<Unit>.Content(
        dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<Unit>.() -> Unit
    ) {
		SearchScreen(
			navigator = destinationsNavigator
		)
    }
    
}