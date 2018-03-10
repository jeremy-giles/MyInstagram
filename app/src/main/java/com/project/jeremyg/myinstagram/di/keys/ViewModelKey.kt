package com.project.jeremyg.myinstagram.di.keys

import kotlin.reflect.KClass

import android.arch.lifecycle.ViewModel
import dagger.MapKey

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey (
    val value: KClass<out ViewModel>
)