package com.example.ex02_capture

import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

	var myLinear: LinearLayout ? = null
	var myLayout: ConstraintLayout ? = null

	var myPath:String? = null

	val sdf = SimpleDateFormat("yyyyMMddHHmmss")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		requestPermission()
		setContentView(R.layout.activity_main)
		myLinear = findViewById(R.id.myLinear)	// 노랑 버튼 클릭시
		myLayout = findViewById(R.id.layout1)
		myPath = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera/"



		// 폴더생성
		File(myPath).mkdirs()
		Log.d("myPath 여", myPath!!)

	}

	fun requestPermission(){

		// WRITE_EXTRNAL_STORAGE의 상태가 PERMISSION_GRANDTED 가 아니라면
		if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
		!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, arrayOf(
				android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
				android.Manifest.permission.READ_EXTERNAL_STORAGE
			), 1234)

		}
	}

	fun yellowGo(vv:View){
		val btn = vv as Button
		Log.d("yellowGo 여","${btn.text}")

		// 캡쳐기능 사용하기
		myLinear!!.isDrawingCacheEnabled = true
		myLayout!!.isDrawingCacheEnabled = true
		var bitmap:Bitmap = myLayout!!.getDrawingCache()


		if(btn.text == "노랑"){
			bitmap = myLinear!!.getDrawingCache()
		}



		val fos = FileOutputStream(myPath!! + "${sdf.format(Date())}.jpg")

		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)




		fos.close()

		// 캡쳐 멈추기
		myLayout!!.isDrawingCacheEnabled = false
		myLinear!!.isDrawingCacheEnabled = false



	}

}