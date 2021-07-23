package com.digitalsamurai.a3205teamtest.fragmentDownloads

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.digitalsamurai.a3205teamtest.room.AppDatabase
import com.digitalsamurai.a3205teamtest.room.DownloadedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import java.io.File
import kotlin.collections.ArrayList

class PresenterDownloadsFragment : MvpPresenter<InterfaceDownloadsFragment>(){

    private lateinit var adapter : AdapterDownloadedRepos
    private  var data = ArrayList<DownloadedRepository>()



    fun setAdapterInRecycler(recycler : RecyclerView){
        adapter = AdapterDownloadedRepos(data)

        recycler.adapter = adapter
    }

    fun observeInDatabase(lifecycle: LifecycleOwner) {
        val liveData  = AppDatabase.create().daoDownloadedRepository().observeData()
        liveData.observe(lifecycle, object : Observer<List<DownloadedRepository>> {
            override fun onChanged(t: List<DownloadedRepository>?) {
                for(i : Int in 0..t!!.size-1){
                    //проверку существования файла можно было бы попробовать сделать c помощью broadcastReceiver
                        //но в тз про это не было вообще ни слова, поэтому это просто моя фича
                    if (!File("/storage/emulated/0/Download/"+t.get(i).fileName).exists()){
                        CoroutineScope(Dispatchers.IO).launch {
                            AppDatabase.create().daoDownloadedRepository().deleteRepository(t.get(i))
                        }
                        //делаем возврат, чтобы лишний раз не перезагружать adapter (т.к. будет в бд будет изменение и будет вызвана эта функция)
                        return
                    }
                }
                data.clear()
                data.addAll(t!!)
                adapter.notifyDataSetChanged()
            }

        })
    }


}