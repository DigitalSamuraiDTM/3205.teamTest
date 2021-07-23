package com.digitalsamurai.a3205teamtest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.digitalsamurai.a3205teamtest.dagger2.AppComponent
import com.digitalsamurai.a3205teamtest.dagger2.DaggerAppComponent
import com.digitalsamurai.a3205teamtest.dagger2.MainModule


class StartApplication : Application() {
    override fun onCreate() {
        context = baseContext
        daggerComponent = createDagger()
        super.onCreate()
    }

    fun createDagger() : AppComponent{
        return DaggerAppComponent.builder().mainModule(MainModule()).build()
    }

    companion object{

        @SuppressLint("StaticFieldLeak")
        private  lateinit var context : Context

        private lateinit var daggerComponent : AppComponent
        fun getDagger() : AppComponent{
            return daggerComponent
        }

        fun getContext(): Context {
            return context
        }

    }
}