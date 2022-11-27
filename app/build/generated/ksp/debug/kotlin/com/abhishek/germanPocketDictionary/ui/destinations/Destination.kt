package com.abhishek.germanPocketDictionary.ui.destinations

import com.ramcosta.composedestinations.spec.*

/**
 * Handy typealias of [TypedDestination] when you don't
 * care about the generic type (probably most cases for app's use)
 */
public typealias Destination = TypedDestination<*>

/**
 * TypedDestination is a sealed version of [DestinationSpec]
 */
public sealed interface TypedDestination<T>: DestinationSpec<T>

/**
 * DirectionDestination is a sealed version of [DirectionDestinationSpec]
 */
public sealed interface DirectionDestination: TypedDestination<Unit>, DirectionDestinationSpec

