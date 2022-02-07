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
import com.example.inappmessaging.databinding.DialogInAppMessagingModalBinding
import com.example.inappmessaging.util.OpenDeepLinkEvent
import org.greenrobot.eventbus.EventBus

class InAppMessagingModalDialog : DialogFragment() {
  private lateinit var binding: DialogInAppMessagingModalBinding
  private lateinit var listener: OnActionSelectedListener

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    super.onCreateView(inflater, container, savedInstanceState)
    setWindowSetting()
    binding = DataBindingUtil.inflate(inflater, R.layout.dialog_in_app_messaging_modal, container, false)
    binding.title = requireArguments().getString(TITLE)
    binding.body = requireArguments().getString(BODY,null)
    binding.actionText = requireArguments().getString(ACTION_TEXT,null)
    binding.bodyColor = requireArguments().getString(BODY_COLOR)
    binding.textColor = requireArguments().getString(TEXT_COLOR)
    binding.btnBodyColor = requireArguments().getString(BTN_BODY_COLOR)
    binding.btnTextColor = requireArguments().getString(BTN_TEXT_COLOR)
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
    binding.tvCancel.setOnClickListener {
      dismiss()
    }

    binding.tvConfirm.setOnClickListener {
      EventBus.getDefault().post(
        OpenDeepLinkEvent(deepLink = requireArguments().getString(
        ACTION_URL
      )!!)
      )
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
    private const val ACTION_TEXT = "ACTION_TEXT"
    private const val BODY_COLOR = "BODY_COLOR"
    private const val TEXT_COLOR = "TEXT_COLOR"
    private const val BTN_BODY_COLOR = "BTN_BODY_COLOR"
    private const val BTN_TEXT_COLOR = "BTN_TEXT_COLOR"
    private const val IMAGE = "IMAGE"
    private const val ACTION_URL = "ACTION_URL"
    fun newInstance(title: String,body: String? = null,actionText: String? = null,bodyColor: String,textColor: String,btnBodyColor: String? = null,btnTextColor: String? = null,image: String? = null,actionUrl: String? = null): InAppMessagingModalDialog {
      val dialog = InAppMessagingModalDialog()
      val args = Bundle()
      args.putString(TITLE,title)
      args.putString(BODY_COLOR,bodyColor)
      args.putString(TEXT_COLOR,textColor)
      body?.let { args.putString(BODY,it) }
      actionText?.let { args.putString(ACTION_TEXT,it) }
      btnBodyColor?.let { args.putString(BTN_BODY_COLOR,it) }
      btnTextColor?.let { args.putString(BTN_TEXT_COLOR,it) }
      image?.let { args.putString(IMAGE,it) }
      actionUrl?.let { args.putString(ACTION_URL,it) }
      dialog.arguments = args
      return dialog
    }
  }
}
