package com.example.inappmessaging.inappmessaging

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.inappmessaging.R
import com.example.inappmessaging.databinding.DialogInAppMessagingCardBinding
import com.example.inappmessaging.util.OpenDeepLinkEvent
import org.greenrobot.eventbus.EventBus

class InAppMessagingCardDialog : DialogFragment() {
  private lateinit var binding: DialogInAppMessagingCardBinding
  private lateinit var listener: OnActionSelectedListener

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    super.onCreateView(inflater, container, savedInstanceState)
    setWindowSetting()
    binding = DataBindingUtil.inflate(inflater, R.layout.dialog_in_app_messaging_card, container, false)
    binding.title = requireArguments().getString(TITLE)
    binding.body = requireArguments().getString(BODY,null)
    binding.primaryActionText = requireArguments().getString(PRIMARY_ACTION_TEXT,null)
    binding.secondaryActionText = requireArguments().getString(SECONDARY_ACTION_TEXT,null)
    binding.bodyColor = requireArguments().getString(BODY_COLOR)
    binding.textColor = requireArguments().getString(TEXT_COLOR)
    binding.primaryBtnTextColor = requireArguments().getString(PRIMARY_BTN_TEXT_COLOR)
    binding.secondaryBtnTextColor = requireArguments().getString(SECONDARY_BTN_TEXT_COLOR)
    binding.image = requireArguments().getString(IMAGE,null)
    bindView()
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.DialogFragmentNormal)
  }

  private fun setWindowSetting() {
    isCancelable = false
    dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
  }

  private fun bindView() {
    binding.tvSecondary.setOnClickListener {
      if (requireArguments().getString(SECONDARY_ACTION_URL) != null) {
        EventBus.getDefault().post(
          OpenDeepLinkEvent(deepLink = requireArguments().getString(
          SECONDARY_ACTION_URL
        )!!)
        )
      }
      dismiss()
    }

    binding.tvPrimary.setOnClickListener {
      if (requireArguments().getString(ACTION_URL) != null) {
        EventBus.getDefault().post(OpenDeepLinkEvent(deepLink = requireArguments().getString(
          ACTION_URL
        )!!))
      }
      dismiss()
    }
  }

  fun setOnActionSelectedListener(listener: OnActionSelectedListener) {
    this.listener = listener
  }

  interface OnActionSelectedListener {
    fun onDismiss()
  }

  override fun onDestroy() {
    super.onDestroy()
    listener.onDismiss()
  }

  companion object {
    private const val TITLE = "TITLE"
    private const val BODY = "BODY"
    private const val PRIMARY_ACTION_TEXT = "PRIMARY_ACTION_TEXT"
    private const val SECONDARY_ACTION_TEXT = "SECONDARY_ACTION_TEXT"
    private const val BODY_COLOR = "BODY_COLOR"
    private const val TEXT_COLOR = "TEXT_COLOR"
    private const val PRIMARY_BTN_TEXT_COLOR = "PRIMARY_BTN_TEXT_COLOR"
    private const val SECONDARY_BTN_TEXT_COLOR = "SECONDARY_BTN_TEXT_COLOR"
    private const val IMAGE = "IMAGE"
    private const val ACTION_URL = "ACTION_URL"
    private const val SECONDARY_ACTION_URL = "SECONDARY_ACTION_URL"
    fun newInstance(title: String,body: String? = null,primaryActionText: String? = null,secondaryActionText: String? = null,bodyColor: String,textColor: String,primaryBtnTextColor: String? = null,secondaryBtnTextColor: String? = null,image: String? = null,actionUrl: String? = null,secondryActionUrl: String? = null): InAppMessagingCardDialog {
      val dialog = InAppMessagingCardDialog()
      val args = Bundle()
      args.putString(TITLE,title)
      args.putString(BODY_COLOR,bodyColor)
      args.putString(TEXT_COLOR,textColor)
      body?.let { args.putString(BODY,it) }
      primaryActionText?.let { args.putString(PRIMARY_ACTION_TEXT,it) }
      secondaryActionText?.let { args.putString(SECONDARY_ACTION_TEXT,it) }
      primaryBtnTextColor?.let { args.putString(PRIMARY_BTN_TEXT_COLOR,it) }
      secondaryBtnTextColor?.let { args.putString(SECONDARY_BTN_TEXT_COLOR,it) }
      image?.let { args.putString(IMAGE,it) }
      actionUrl?.let { args.putString(ACTION_URL,it) }
      secondryActionUrl?.let { args.putString(SECONDARY_ACTION_URL,it) }
      dialog.arguments = args
      return dialog
    }
  }
}
