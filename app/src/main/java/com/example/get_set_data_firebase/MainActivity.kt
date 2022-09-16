package com.example.get_set_data_firebase

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.get_set_data_firebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listview.*







class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var database = FirebaseDatabase.getInstance().reference


        insert_button.setOnClickListener {
            var id = id_input.text.toString()
            var name = name_input.text.toString()
            database.child(id.toString()).setValue(Employee(name))
        }


        viewList_button.setOnClickListener {
            setContentView(R.layout.listview)

            var getdata = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(p0: DataSnapshot) {
                    var sb = StringBuilder()
                    for(i in p0.children){
                        var name = i.child("name").getValue()
//                    var var3 = i.child("var3").getValue()
//                    var var4 = i.child("var4").getValue()
                        sb.append("${i.key}:$name\n")   // $var3 $var4\n")
                    }
                    textView.setText(sb)
                }
            }
            database.addValueEventListener(getdata)
            database.addListenerForSingleValueEvent(getdata)

            update_button.setOnClickListener{
                var txt = textView.text
                var items = txt.split("\n")
                for (item in items){
                    if (item !== "") {
                        database.child(item.split(":")[0]).setValue(Employee(item.split(":")[1]))
                    }


                }
            }
            val bck_button = findViewById<Button>(R.id.back_button)
            bck_button.setOnClickListener {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }







        var getdata = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                var sb = StringBuilder()
                for(i in p0.children){
                    var name = i.child("name").getValue()
//                    var var3 = i.child("var3").getValue()
//                    var var4 = i.child("var4").getValue()
                    sb.append("${i.key} $name\n")   // $var3 $var4\n")
                }
            }

        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}



