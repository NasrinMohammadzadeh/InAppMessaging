package com.example.inappmessaging.inappmessaging

import android.app.Activity
import com.example.inappmessaging.util.AppUtil
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.*
import javax.inject.Inject


class InAppMessageHandler @Inject constructor(private val appUtil: AppUtil) {

  fun checkMessageType(inAppMessage: InAppMessage, callBacks: FirebaseInAppMessagingDisplayCallbacks, activity: Activity) {
    val messageType = inAppMessage.messageType
    messageType?.let { type ->
      when (type) {
        MessageType.CARD -> {
          val cardMessage = inAppMessage as CardMessage
          val dialog = InAppMessagingCardDialog.newInstance(
            title = cardMessage.title.text!!,
            body = cardMessage.body?.text,
            primaryActionText = cardMessage.primaryAction.button?.text?.text,
            secondaryActionText = cardMessage.secondaryAction?.button?.text?.text,
            bodyColor = cardMessage.backgroundHexColor,
            textColor = cardMessage.title.hexColor,
            primaryBtnTextColor = cardMessage.primaryAction.button?.text?.hexColor,
            secondaryBtnTextColor = cardMessage.secondaryAction?.button?.text?.hexColor,
            image = cardMessage.portraitImageData?.imageUrl,
            actionUrl = cardMessage.primaryAction.actionUrl,
            secondryActionUrl = cardMessage.secondaryAction?.actionUrl
          )
          appUtil.showDialog(activity, dialog)
          dialog.setOnActionSelectedListener(object :
            InAppMessagingCardDialog.OnActionSelectedListener {
            override fun onDismiss() {
              callBacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK)
            }
          })
        }
        MessageType.BANNER -> {
          val bannerMessage = inAppMessage as BannerMessage
          val dialog = InAppMessagingBannerDialog.newInstance(
            title = bannerMessage.title.text!!,
            body = bannerMessage.body?.text,
            bodyColor = bannerMessage.backgroundHexColor,
            textColor = bannerMessage.title.hexColor,
            image = bannerMessage.imageData?.imageUrl,
            actionUrl = bannerMessage.action?.actionUrl
          )
          appUtil.showDialog(activity, dialog)
          dialog.setOnActionSelectedListener(object :
            InAppMessagingBannerDialog.OnActionSelectedListener {
            override fun onDismiss() {
              callBacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK)
            }
          })
        }
        MessageType.MODAL -> {
          val modalMessage = inAppMessage as ModalMessage
          val dialog = InAppMessagingModalDialog.newInstance(
            title = modalMessage.title.text!!,
            body = modalMessage.body?.text,
            actionText = modalMessage.action?.button?.text?.text,
            bodyColor = modalMessage.backgroundHexColor,
            textColor = modalMessage.title.hexColor,
            btnBodyColor = modalMessage.action?.button?.buttonHexColor,
            btnTextColor = modalMessage.action?.button?.text?.hexColor,
            image = modalMessage.imageData?.imageUrl,
            actionUrl = modalMessage.action?.actionUrl
          )
          appUtil.showDialog(activity, dialog)
          dialog.setOnActionSelectedListener(object :
            InAppMessagingModalDialog.OnActionSelectedListener {
            override fun onDismiss() {
              callBacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK)
            }
          })
        }
        MessageType.IMAGE_ONLY -> {
          val imageOnlyMessage = inAppMessage as ImageOnlyMessage
          val dialog = InAppMessagingImageDialog.newInstance(
            image = imageOnlyMessage.imageData.imageUrl,
            actionUrl = imageOnlyMessage.action?.actionUrl
          )
          appUtil.showDialog(activity, dialog)
          dialog.setOnActionSelectedListener(object :
            InAppMessagingImageDialog.OnActionSelectedListener {
            override fun onDismiss() {
              callBacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK)
            }
          })
        }
        else -> {
          // Received an unsupported message
        }
      }
    }
  }
}
