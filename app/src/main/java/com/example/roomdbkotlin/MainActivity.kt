package com.example.roomdbkotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roomdbkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "User.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                db.userDao().insert(User("Penan Rajput", "48258756", "Pune", 20))
            }
        }

        binding.button2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val list = db.userDao().getAllUser()
                if (list.isNotEmpty()) {
                    val user = list[0]
                    launch(Dispatchers.Main) {
                        binding.textView.text = user.name
                        binding.textView2.text = user.number
                        binding.textView3.text = user.address
                        binding.textView4.text = user.age.toString()
                    }
                }
            }
        }
    }
}