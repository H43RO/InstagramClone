package com.haerokim.instagramclone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post_upload.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostUploadActivity : AppCompatActivity() {

    var filePath : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_upload)

        view_pictures.setOnClickListener {
            getPicture()
        }

        upload_btn.setOnClickListener {
            if(filePath == ""){
                Toast.makeText(this, "업로드할 사진을 선택해주세요", Toast.LENGTH_LONG).show()
            }else{
                uploadPost(filePath)
            }
        }

        user_info.setOnClickListener {
            startActivity(Intent(this, UserInfo::class.java))
        }

        all_feed.setOnClickListener {
            startActivity(Intent(this, InstagramPostListActivity::class.java))

        }

        my_feed.setOnClickListener {
            startActivity(Intent(this, MyFeedList::class.java))

        }

    }

    fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val uri: Uri = data!!.data!! //선택한 이미지의 위치를 찾아줌 (상대경로)
            filePath = getImageFilePath(uri)

        }
    }

    fun getImageFilePath(contentUri:Uri):String{
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA) //필터링
        val cursor = contentResolver.query(contentUri,projection,null,null,null) //절대경로 얻는 인덱스
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) //cursor
        }
        return cursor.getString(columnIndex) //cursor에 해당하는 이미지의 절대경로 나옴
    }

    fun uploadPost(filePath : String){
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("images/*"), file) // RequestBody의 타입이 images 이다
        val part = MultipartBody.Part.createFormData("image", file.name,fileRequestBody) // '이미지'라는 이름, 파일명 서버에게 보냄
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as MasterApplication).service.uploadPost(
            part, content
        ).enqueue(object: Callback<Post> {
            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                Log.d("error","실패")
            }

            override fun onResponse(call: retrofit2.Call<Post>, response: Response<Post>) {
                if(response.isSuccessful){
                    val post = response.body()
                    Log.d("pathh", post!!.content)

                    startActivity(Intent(this@PostUploadActivity, InstagramPostListActivity::class.java))
                }
            }
        })
    }

    fun getContent():String{
        return content_input.text.toString()
    }

}
