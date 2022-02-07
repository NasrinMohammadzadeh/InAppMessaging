package com.example.inappmessaging

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inappmessaging.inappmessaging.InAppMessageHandler
import com.example.inappmessaging.util.OpenDeepLinkEvent
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.InAppMessage
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var messaging: InAppMessageHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: OpenDeepLinkEvent) {
        if (event.deepLink != null) {
            try {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(event.deepLink)
                startActivity(i)
            } catch (e: java.lang.Exception) {
            }
        }
    }


    override fun onResume() {
        super.onResume()
        FirebaseInAppMessaging.getInstance().setMessageDisplayComponent { inAppMessage: InAppMessage, callBacks: FirebaseInAppMessagingDisplayCallbacks? ->
            messaging.checkMessageType(inAppMessage,callBacks!!, this)
        }
    }
}