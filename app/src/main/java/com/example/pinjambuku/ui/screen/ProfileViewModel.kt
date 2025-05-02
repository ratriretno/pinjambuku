package com.example.pinjambuku.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinjambuku.model.ProfileResponse
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: BookRepository) : ViewModel() {
    private val _result = MutableLiveData<ResultNetwork<ProfileResponse>>()
    val result: LiveData<ResultNetwork<ProfileResponse>> = _result

    //loading first page
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun profile(id : String) {
        setLoading(true)
        viewModelScope.launch {
            _result.value = repository.profile(id)
        }
    }


    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun logout() {
        viewModelScope.launch {
            repository.saveLoginSetting(false, "")
        }
    }

}