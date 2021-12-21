package com.example.a1valettest.utils

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatchers

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IODispatchers

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatchers

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatchers