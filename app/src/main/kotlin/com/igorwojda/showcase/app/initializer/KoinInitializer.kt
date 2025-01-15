package com.igorwojda.showcase.app.initializer

import android.content.Context
import androidx.startup.Initializer
import com.igorwojda.showcase.app.appModule
import com.igorwojda.showcase.base.baseModule
import com.igorwojda.showcase.favourite.featureFavouriteModules
import com.igorwojda.showcase.profile.featureProfilesModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return GlobalContext.startKoin {
            androidLogger()
            androidContext(context)

            modules(appModule)
            modules(baseModule)
            modules(featureFavouriteModules)
            modules(com.igorwojda.showcase.album.featureAlbumModules)
            modules(featureProfilesModules)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
