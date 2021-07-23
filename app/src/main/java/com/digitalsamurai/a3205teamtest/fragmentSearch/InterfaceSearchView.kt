package com.digitalsamurai.a3205teamtest.fragmentSearch

import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState


interface InterfaceSearchView : MvpView {
    @SingleState
    fun showLoading()
    @SingleState
    fun showError(error : Int)
    @SingleState
    fun showData()
}