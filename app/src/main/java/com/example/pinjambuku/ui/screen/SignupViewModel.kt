package com.example.pinjambuku.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinjambuku.model.LoginResponse
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.model.SignupItem
import com.example.pinjambuku.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: BookRepository) : ViewModel() {
    private val _result = MutableLiveData<ResultNetwork<LoginResponse>>()
    val result: LiveData<ResultNetwork<LoginResponse>> = _result

    //loading first page
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signup(item: SignupItem) {
        setLoading(true)
        viewModelScope.launch {
            _result.value = repository.signup( item)
        }
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setLoginSetting(loginResponse: LoginResponse) {
        viewModelScope.launch {
            repository.saveLoginSetting(loginResponse.login, loginResponse.idUser)
        }
    }

}