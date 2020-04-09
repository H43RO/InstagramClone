package com.haerokim.instagramclone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post_upload.*

class PostUploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_upload)

        view_pictures.setOnClickListener {
            getPicture()
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
            var a = getImageFilePath(uri)
            Log.d("pathh","path = "+a)

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
}
