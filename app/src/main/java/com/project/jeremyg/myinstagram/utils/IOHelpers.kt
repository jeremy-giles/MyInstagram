package com.project.jeremyg.myinstagram.utils

import android.content.Context
import com.project.jeremyg.myinstagram.App
import com.project.jeremyg.myinstagram.R
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IOHelpers @Inject constructor(context: Context) {

    private var context: Context? = context

    fun writeGalleryToInternalStorage(data: String) {
        context?.openFileOutput(context?.getString(R.string.user_gallery_internal_storage),
                Context.MODE_PRIVATE).use {
            it?.write(data.toByteArray())
        }
    }

    fun readGalleryFromInternalStorage(): File {
        return File(context?.filesDir, context?.getString(R.string.user_gallery_internal_storage))
    }
}