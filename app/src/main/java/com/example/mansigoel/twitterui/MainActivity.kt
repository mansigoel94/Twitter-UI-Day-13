package com.example.mansigoel.twitterui

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    var text: CharSequence? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //read text from edit text and accordingly change count of textview for characters remaining
        et_TextArea.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    text = p0
                    tv_chars_remaining.text = "" + (140 - p0.length)
                }
            }
        })

        //on click of submit button close keyboard and raise toast
        submit_button.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(submit_button.getWindowToken(), 0)
            Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT).show()
        }

        //populate lower recycler view with button to open camera  and video
        // and also show recent images
        val list = getAllShownImagesPath(this)
        list.reverse()
        val adapter = Adapter(list, this)
        rv_recent.setLayoutManager(GridLayoutManager(
                this, 3, LinearLayoutManager.VERTICAL, false))
        rv_recent.setAdapter(adapter)
        rv_recent.setHasFixedSize(true)
        rv_recent.setItemViewCacheSize(20);
        rv_recent.setDrawingCacheEnabled(true);
        rv_recent.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

        /*  for (str in list) {
              Log.i("Mansi", "onCreate: " + str)
          }*/
    }

    fun getAllShownImagesPath(activity: Activity): ArrayList<String> {
        val uri: Uri
        val cursor: Cursor?
        val column_index_data: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursor = activity.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        return listOfAllImages
    }
}
