package com.digitalsamurai.a3205teamtest.dagger2

import androidx.annotation.NonNull
import com.digitalsamurai.a3205teamtest.fragmentDownloads.PresenterDownloadsFragment
import com.digitalsamurai.a3205teamtest.fragmentSearch.PresenterSearchFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MainModule {

    @Provides
    @Singleton
    @NonNull
    fun getPresenterSearchFragment() : PresenterSearchFragment{
        return PresenterSearchFragment()
    }

    @Provides
    @Singleton
    @NonNull
    fun getPresenterDownloadsFragment() : PresenterDownloadsFragment{
        return PresenterDownloadsFragment()
    }


}