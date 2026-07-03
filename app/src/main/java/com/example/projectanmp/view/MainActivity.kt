package com.example.projectanmp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ActivityMainBinding
import com.example.projectanmp.model.User
import com.example.projectanmp.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        CoroutineScope(Dispatchers.IO).launch {

            val db = UserDatabase.buildDatabase(this@MainActivity)

            if(db.userDao().selectAllUser().isEmpty()){

                db.userDao().insertAll(
                    User(
                        username = "student",
                        password = "123"
                    )
                )

            }

        }
    }

}