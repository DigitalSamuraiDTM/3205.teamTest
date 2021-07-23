package com.digitalsamurai.a3205teamtest.fragmentSearch

import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.digitalsamurai.a3205teamtest.R
import com.digitalsamurai.a3205teamtest.StartApplication
import com.digitalsamurai.a3205teamtest.retrofit.DataUserRepo
import com.digitalsamurai.a3205teamtest.retrofit.RetrofitService
import com.digitalsamurai.a3205teamtest.room.AppDatabase
import com.digitalsamurai.a3205teamtest.room.DownloadedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class AdapterSearchData(private var data : ArrayList<DataUserRepo>) : RecyclerView.Adapter<AdapterSearchData.SearchViewHolder>() {

    private  var username: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        with(LayoutInflater.from(StartApplication.getContext())){
            return SearchViewHolder(this.inflate(R.layout.item_repository, parent, false))
        }
    }

    fun notifyDataSetChanged(user : String?){
        this.username = user
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.textRepoFullName.setText(data.get(position).fullName)
        holder.textRepoName.setText(data.get(position).name)
        holder.user = username
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var textRepoName : TextView
        var textRepoFullName : TextView
        var buttonDownloadRepo : ImageButton
         var user : String? = null
        init {
            textRepoName = itemView.findViewById(R.id.item_repos_view_repoName)
            textRepoFullName = itemView.findViewById(R.id.item_repos_view_reposFullName)
            buttonDownloadRepo = itemView.findViewById(R.id.item_repos_button_downloadRepo)
            buttonDownloadRepo.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (File("/storage/emulated/0/Download/"+textRepoName.text.toString()).exists()){
                Toast.makeText(StartApplication.getContext(),"File is exist", Toast.LENGTH_SHORT).show()
                return
            }
            setOffDownloadButton(true)


            Toast.makeText(StartApplication.getContext(),"Download started", Toast.LENGTH_SHORT).show()
            RetrofitService.create().downloadZipRepos(user!!, textRepoName.text.toString()).enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.body() !=null){
                        writeZipToDisk(response.body()!!, textRepoName.text.toString())

                    } else {
                        Toast.makeText(StartApplication.getContext(),"Repository is empty :(", Toast.LENGTH_SHORT).show()
                        setOffDownloadButton(false)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(StartApplication.getContext(),"Downloading error", Toast.LENGTH_SHORT).show()
                    setOffDownloadButton(false)
                }

            })
        }
        fun writeZipToDisk(body : ResponseBody, filename : String) = GlobalScope.launch(Dispatchers.Main){
            val file : File
            if (Build.VERSION.SDK_INT< Build.VERSION_CODES.Q){
                 file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/"+filename)
            } else{
                //может не совсем правильное решение, но я просто не нашел четкого ответа, как получить путь на Q+
                    //getExternalDir возвращает неправильный путь до самого приложения (com.android.(...))
                file = File("/storage/emulated/0/Download","/"+filename)
            }
            var fileReader = ByteArray(4096)


            var fileSizeDownloaded = 0;
            //интересный факт встретил я. Удаляю файл руками из папки Downloads, проверка exists возвращает false(файл не существует)
            //но при инициализации встречаю ошибку File exists. Если кто-то посдкажет почему так, то буду рад
            Log.d("3205v", file.exists().toString()+ " "+ file.path.toString())
            file.createNewFile()
            val outputStream = FileOutputStream(file, false)
            var inputStream = body.byteStream()
            while(true){
                var read = inputStream.read(fileReader)
                if (read == -1){
                    break
                }

                outputStream.write(fileReader,0,read)
                fileSizeDownloaded+=read

            }
            outputStream.close()

            AppDatabase.create().daoDownloadedRepository().insertRepository(DownloadedRepository(0,filename, user!!))
            Toast.makeText(StartApplication.getContext(),"Downloading successful", Toast.LENGTH_SHORT).show()
            setOffDownloadButton(false)

        }
        //для избежания повторного клика
        fun setOffDownloadButton(off : Boolean){
            if (off){
                buttonDownloadRepo.visibility = View.INVISIBLE
            } else{
                buttonDownloadRepo.visibility = View.VISIBLE

            }
        }
    }
}