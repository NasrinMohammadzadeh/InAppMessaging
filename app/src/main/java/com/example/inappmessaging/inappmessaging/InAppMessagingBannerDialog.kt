package com.example.inappmessaging.inappmessaging

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import android.view.Gravity
import androidx.databinding.DataBindingUtil
import com.example.inappmessaging.R
import com.example.inappmessaging.databinding.DialogInAppMessagingBannerBinding
import com.example.inappmessaging.util.OpenDeepLinkEvent
import com.example.inappmessaging.util.delayJobOnLifeCycle
import org.greenrobot.eventbus.EventBus


class InAppMessagingBannerDialog : DialogFragment() {
  private lateinit var binding: DialogInAppMessagingBannerBinding
  private lateinit var listener: OnActionSelectedListener

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    super.onCreateView(inflater, container, savedInstanceState)
    setWindowSetting()
    binding = DataBindingUtil.inflate(inflater, R.layout.dialog_in_app_messaging_banner, container, false)
    binding.title = requireArguments().getString(TITLE)
    binding.body = requireArguments().getString(BODY,null)
    binding.bodyColor = requireArguments().getString(BODY_COLOR)
    binding.textColor = requireArguments().getString(TEXT_COLOR)
    binding.image = requireArguments().getString(IMAGE,null)
    bindView()
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.DialogFragmentNormal)
  }

  private fun setWindowSetting() {
    isCancelable = true
    dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog?.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
  }

  override fun onStart() {
    super.onStart()
    val width = ViewGroup.LayoutParams.MATCH_PARENT
    val height = ViewGroup.LayoutParams.WRAP_CONTENT
    dialog?.window!!.setLayout(width, height)
  }

  private fun bindView() {
    binding.holder.setOnClickListener {
      if (requireArguments().getString(ACTION_URL) != null) {
        EventBus.getDefault().post(
          OpenDeepLinkEvent(deepLink = requireArguments().getString(
          ACTION_URL
        )!!)
        )
      }
      dismiss()
    }
    delayJobOnLifeCycle(6000){
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
    private const val BODY_COLOR = "BODY_COLOR"
    private const val TEXT_COLOR = "TEXT_COLOR"
    private const val IMAGE = "IMAGE"
    private const val ACTION_URL = "ACTION_URL"
    fun newInstance(title: String,body: String? = null,bodyColor: String,textColor: String,image: String? = null,actionUrl: String? = null): InAppMessagingBannerDialog {
      val dialog = InAppMessagingBannerDialog()
      val args = Bundle()
      args.putString(TITLE,title)
      args.putString(BODY_COLOR,bodyColor)
      args.putString(TEXT_COLOR,textColor)
      body?.let { args.putString(BODY,it) }
      image?.let { args.putString(IMAGE,it) }
      actionUrl?.let { args.putString(ACTION_URL,it) }
      dialog.arguments = args
      return dialog
    }
  }
}
