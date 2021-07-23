package com.digitalsamurai.a3205teamtest.fragmentDownloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalsamurai.a3205teamtest.R
import com.digitalsamurai.a3205teamtest.StartApplication
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class DownloadsFragment : MvpAppCompatFragment() , InterfaceDownloadsFragment {


    @Inject
    lateinit var presenterProvider : Provider<PresenterDownloadsFragment>

    val presenter by moxyPresenter {presenterProvider.get()};

    private lateinit var recyclerData : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        StartApplication.getDagger().injectDownloadsFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_downloads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerData = view.findViewById(R.id.fr_downloadedRepo_recycler_data)
        presenter.setAdapterInRecycler(recyclerData)
        presenter.observeInDatabase(viewLifecycleOwner)


        super.onViewCreated(view, savedInstanceState)

    }
}