package br.com.souzabrunoj.storekmp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import br.com.souzabrunoj.storekmp.presentation.screens.HomeScreen
import cafe.adriel.voyager.navigator.Navigator
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import com.seiko.imageloader.option.androidContext
import okio.Path.Companion.toOkioPath

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalImageLoader provides remember { generateImageLoader(this) }) {
                Navigator(screen = HomeScreen)
            }
        }
    }
}

fun generateImageLoader(context: Context) = ImageLoader {
    options { androidContext(context) }
    components { setupDefaultComponents() }
    interceptor {
        defaultImageResultMemoryCache()
        memoryCacheConfig {
            maxSizePercent(context, 0.25)
        }

        diskCacheConfig {
            directory(context.cacheDir.resolve("image_cache").toOkioPath())
            maxSizeBytes(512 * (1024 * 2))
        }
    }

}