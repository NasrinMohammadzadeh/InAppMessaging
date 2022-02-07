package com.example.inappmessaging.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*

fun Fragment.delayJobOnLifeCycle(
  durationInMillis: Long,
  dispatcher: CoroutineDispatcher = Dispatchers.Main,
  block:() -> Unit
): Job = viewLifecycleOwner.let{ lifecycleOwner ->
  lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher){
    delay(durationInMillis)
    block()
  }
}

