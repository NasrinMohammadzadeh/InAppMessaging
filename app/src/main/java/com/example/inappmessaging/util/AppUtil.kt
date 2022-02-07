package com.example.inappmessaging.util

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import javax.inject.Inject

class AppUtil @Inject constructor() {
  fun showDialog(activity: Activity, dialog: DialogFragment) {
    val fragTransaction: FragmentTransaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
    fragTransaction.add(dialog, "dialog")
    fragTransaction.commitAllowingStateLoss()
  }
}
