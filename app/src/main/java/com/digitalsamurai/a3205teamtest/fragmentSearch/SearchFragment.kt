package com.digitalsamurai.a3205teamtest.fragmentSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.digitalsamurai.a3205teamtest.R
import com.digitalsamurai.a3205teamtest.StartApplication
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class SearchFragment : MvpAppCompatFragment() ,InterfaceSearchView {

    @Inject
    lateinit var presenterProvider : Provider<PresenterSearchFragment>

    val presenter by moxyPresenter {presenterProvider.get()};

    private lateinit var editSearch : EditText
    private lateinit var progressBarLoading : ProgressBar
    private lateinit var recyclerData : RecyclerView
    private lateinit var textViewError : TextView
    private lateinit var buttonSearch : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {

        StartApplication.getDagger().injectSearchFragment(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        editSearch = view.findViewById(R.id.fr_search_edit_search)

        //идея работает, но упираешься в лимит по запросам на api.github при частом наборе
//        editSearch.addTextChangedListener {
//            if (editSearch.text?.toString() != null || editSearch.text.toString() != ""){
//                presenter.searchUsers(editSearch.text.toString())
//            }
//        }

        textViewError = view.findViewById(R.id.fr_search_textview_error)
        progressBarLoading = view.findViewById(R.id.fr_search_progressBar_loading)
        recyclerData  = view.findViewById(R.id.fr_search_recycler_data)
        presenter.setAdapterInRecycler(recyclerData)
        buttonSearch = view.findViewById(R.id.fr_search_button_search)
        buttonSearch.setOnClickListener{
            if (editSearch.text?.toString() != null || editSearch.text.toString() != ""){
                presenter.searchUsers(editSearch.text.toString())
            } else{
                showError(0)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun showLoading() {
        progressBarLoading.visibility = View.VISIBLE
        recyclerData.visibility = View.GONE
        textViewError.visibility = View.GONE
    }

    override fun showError(error : Int) {
        when(error){
            0->{
                textViewError.setText("Enter username for searching")
            }
            1->{
                textViewError.setText("Something wrong :(")
            }
            2->{
                textViewError.setText("Request error")
            }

        }
        progressBarLoading.visibility = View.GONE
        recyclerData.visibility = View.GONE
        textViewError.visibility = View.VISIBLE
    }

    override fun showData() {
        progressBarLoading.visibility = View.GONE
        recyclerData.visibility = View.VISIBLE
        textViewError.visibility = View.GONE
    }
}