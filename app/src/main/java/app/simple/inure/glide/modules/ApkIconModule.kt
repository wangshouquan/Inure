package app.simple.inure.glide.modules

import android.content.Context
import android.graphics.Bitmap
import app.simple.inure.glide.apkIcon.ApkIcon
import app.simple.inure.glide.apkIcon.ApkIconLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.LibraryGlideModule

@GlideModule
class ApkIconModule : LibraryGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(ApkIcon::class.java, Bitmap::class.java, ApkIconLoader.Factory())
    }
}