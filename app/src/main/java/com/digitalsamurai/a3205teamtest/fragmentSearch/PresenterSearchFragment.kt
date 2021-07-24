package com.digitalsamurai.a3205teamtest.fragmentSearch

import androidx.recyclerview.widget.RecyclerView
import com.digitalsamurai.a3205teamtest.retrofit.DataUserRepo
import com.digitalsamurai.a3205teamtest.retrofit.RetrofitService
import kotlinx.coroutines.*
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Response

class PresenterSearchFragment : MvpPresenter<InterfaceSearchView>() {

    private lateinit var adapter : AdapterSearchData
    private var data = ArrayList<DataUserRepo>()
    private  var JobDataUser : Job? = null
    private  var userName: String? = null


    val ERROR_SOMETHING_WRONG = 1
    val ERROR_REQUEST = 2

    //notify необходим, для того, чтобы во время переворота экрана adapter знал об username, т.к. после поворота всё с чистого листа
    override fun attachView(view: InterfaceSearchView?) {
        adapter.notifyDataSetChanged(userName)
        super.attachView(view)
    }

    //связь между ресайклером и адаптером
    fun setAdapterInRecycler(recycler : RecyclerView){
        adapter = AdapterSearchData(data)
        recycler.adapter = adapter
    }

    //поиск репозиториев пользователя
    fun searchUsers(username : String) = GlobalScope.launch(Dispatchers.Main) {
        viewState.showLoading()
        userName = username
        //если корутины не было, то создаем ее и запускаем, если была, то отменяем запущенную корутину и запускаем новую
        //проверки сделаны для избежания повторных запросов на сервер
        if (JobDataUser != null) {
            if (JobDataUser!!.isActive) {
                JobDataUser!!.cancel()
            }
            JobDataUser = launch { querySearch(username) }
        } else{
            JobDataUser = launch {  querySearch(username) }


        }
    }

    //запрос на получение репозиториев

    fun querySearch(username : String) {
        RetrofitService.create().searchRepositories(username).enqueue(object : retrofit2.Callback<ArrayList<DataUserRepo>>{
            override fun onResponse(
                call: Call<ArrayList<DataUserRepo>>,
                response: Response<ArrayList<DataUserRepo>>
            ) {
                data.clear()
                if (response.body() != null){

                    data.addAll(response.body()!!)
                    viewState.showData()

                    adapter.notifyDataSetChanged(username)
                } else{
                    viewState.showError(ERROR_SOMETHING_WRONG)
                }
            }

            override fun onFailure(call: Call<ArrayList<DataUserRepo>>, t: Throwable) {
                viewState.showError(ERROR_REQUEST)
            }

        })

    }
}