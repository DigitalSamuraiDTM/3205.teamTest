package com.digitalsamurai.a3205teamtest.fragmentDownloads

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalsamurai.a3205teamtest.R
import com.digitalsamurai.a3205teamtest.StartApplication
import com.digitalsamurai.a3205teamtest.room.AppDatabase
import com.digitalsamurai.a3205teamtest.room.DownloadedRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdapterDownloadedRepos(var data : ArrayList<DownloadedRepository>) :
    RecyclerView.Adapter<AdapterDownloadedRepos.DownloadedRepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadedRepoViewHolder {
        with(LayoutInflater.from(StartApplication.getContext())){
            return DownloadedRepoViewHolder(this.inflate(R.layout.item_downloaded_repository,parent, false))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DownloadedRepoViewHolder, position: Int) {
        holder.repoName.setText(data.get(position).fileName)
        holder.userName.setText("by "+data.get(position).userName)
        holder.repository = data.get(position)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DownloadedRepoViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview),
        View.OnClickListener{
        public var repoName : TextView

        public var userName : TextView
        public var btnRemove : ImageButton
        public var btnFile : ImageButton
        public lateinit var repository : DownloadedRepository
        init {
            repoName = itemview.findViewById(R.id.item_downloadedRepo_view_reposName)
            userName = itemview.findViewById(R.id.item_downloadedRepo_view_user)
            btnRemove = itemview.findViewById(R.id.item_downloadedRepo_btn_remove)
            btnRemove.setOnClickListener(this)
            btnFile  = itemview.findViewById(R.id.item_downloadedRepo_btn_openFile)
            btnFile.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
           when(p0?.id){
               R.id.item_downloadedRepo_btn_remove->{
                    deleteNote()
               }
               R.id.item_downloadedRepo_btn_openFile ->{
                   val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                   StartApplication.getContext().startActivity(intent)
               }
           }
        }

         fun deleteNote() = GlobalScope.launch {
            AppDatabase.create().daoDownloadedRepository().deleteRepository(repository)
        }


    }
}