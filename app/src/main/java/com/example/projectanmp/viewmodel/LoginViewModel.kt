package com.example.projectanmp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.projectanmp.model.User
import com.example.projectanmp.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val loginResultLD = MutableLiveData<User?>()
    val loadingLD = MutableLiveData<Boolean>()
    private val job = Job()
    fun login(username: String, password: String) {
        loadingLD.postValue(true)
        launch {
            val db = UserDatabase.buildDatabase(getApplication())
            val user = db.userDao().login(username, password)
            loginResultLD.postValue(user)
            loadingLD.postValue(false)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}