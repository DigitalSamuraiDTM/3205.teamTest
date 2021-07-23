package com.digitalsamurai.a3205teamtest.dagger2

import com.digitalsamurai.a3205teamtest.fragmentDownloads.DownloadsFragment
import com.digitalsamurai.a3205teamtest.fragmentSearch.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface AppComponent {
    fun injectSearchFragment(fr : SearchFragment)

    fun injectDownloadsFragment(fr : DownloadsFragment)


}