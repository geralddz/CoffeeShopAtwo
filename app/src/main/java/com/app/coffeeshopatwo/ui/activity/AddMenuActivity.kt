package com.app.coffeeshopatwo.ui.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.database.SharedPref
import com.app.coffeeshopatwo.databinding.ActivityAddMenuBinding
import com.app.coffeeshopatwo.databinding.ActivityAdminBinding
import com.app.coffeeshopatwo.databinding.ActivityMainBinding
import com.app.coffeeshopatwo.entity.Menu
import com.app.coffeeshopatwo.utils.BitmapConverter

class AddMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMenuBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    private lateinit var imageUri: Uri
    private var img:String=""
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)
        sharedPref = SharedPref(this)
        getData()

        binding.tvaddmenu.setOnClickListener {
            selectimage()
        }

        binding.btnSaveMenu.setOnClickListener {
            val namaMenu = binding.etnamaMenu.text.toString()
            val descMenu = binding.etdescMenu.text.toString()
            val hargaMenu = binding.ethargaMenu.text.toString()
            var photo = ""
            val intent = intent.extras
            if (intent!=null){
                database.menuDao().updateMenu(Menu(
                    id.toInt(),namaMenu,hargaMenu.toInt(),descMenu,img))
                Toast.makeText(this, "Data Menu Berhasil DiUpdate", Toast.LENGTH_SHORT).show()
            }else{
                if(img==""){
                    photo = "null"
                    Log.e("photo",photo)
                }else{
                    photo = img
                    Log.e("photo",photo)
                }
                database.menuDao().insertMenu(Menu(
                    null,namaMenu,hargaMenu.toInt(),descMenu,photo))
                Toast.makeText(this, "Data Menu Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this, AdminActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP ; Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
        }
    }

    fun getData(){
        val intent = intent.extras
        if (intent!=null){
            val menu = database.menuDao().getMenuId(intent.getInt("id"))
            id = menu.menu_id.toString()
            binding.etnamaMenu.setText(menu.nama)
            binding.etdescMenu.setText(menu.deskripsi)
            binding.ethargaMenu.setText(menu.harga.toString())
            img = menu.foto.toString()
            if (img=="null"){
                binding.ivphotobook.setImageResource(R.drawable.photo)
            }else{
                val bitmap = BitmapConverter.converterStringToBitmap(img)
                binding.ivphotobook.setImageBitmap(bitmap)
            }
        }
    }

    private fun selectimage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            val inputStream = this.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val bitmapstring = BitmapConverter.converterBitmapToString(bitmap)
            binding.ivphotobook.setImageBitmap(bitmap)
            img = bitmapstring
        }
    }
}