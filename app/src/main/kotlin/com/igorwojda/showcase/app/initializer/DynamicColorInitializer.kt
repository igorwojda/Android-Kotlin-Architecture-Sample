package com.igorwojda.showcase.app.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.google.android.material.color.DynamicColors

class DynamicColorInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        // Apply dynamic colors to all Activities, Fragments, Views
        // (Material 3 library helper class)
        DynamicColors.applyToActivitiesIfAvailable(context.applicationContext as Application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
