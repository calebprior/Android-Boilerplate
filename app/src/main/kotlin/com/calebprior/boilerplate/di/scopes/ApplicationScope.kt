package com.calebprior.boilerplate.di.scopes

import javax.inject.Scope


/**
 * Any @Provides method marked with this will survive for the entire app lifetime
 * They will act as a singleton, for example if you want the same presenter to be use for every
 * instance of its associated screen then mark the provides method for that presenter with this.
 * The presenter will not be recreated each time the the screen is opened
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope