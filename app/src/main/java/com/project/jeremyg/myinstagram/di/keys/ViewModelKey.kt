package com.project.jeremyg.myinstagram.di.keys

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import kotlin.reflect.KClass

import android.arch.lifecycle.ViewModel
import dagger.MapKey

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey (
    val value: KClass<out ViewModel>
)