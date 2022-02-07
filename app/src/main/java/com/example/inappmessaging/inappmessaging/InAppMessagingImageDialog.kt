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
import com.example.inappmessaging.databinding.DialogInAppMessagingImageBinding
import com.example.inappmessaging.util.OpenDeepLinkEvent
import org.greenrobot.eventbus.EventBus

class InAppMessagingImageDialog : DialogFragment() {
  private lateinit var binding: DialogInAppMessagingImageBinding
  private lateinit var listener: OnActionSelectedListener

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    super.onCreateView(inflater, container, savedInstanceState)
    setWindowSetting()
    binding = DataBindingUtil.inflate(inflater, R.layout.dialog_in_app_messaging_image, container, false)
    binding.imageUrl = requireArguments().getString(IMAGE,null)
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
  }

  private fun bindView() {
    binding.image.setOnClickListener {
      if (requireArguments().getString(ACTION_URL) != null) {
        EventBus.getDefault().post(
          OpenDeepLinkEvent(deepLink = requireArguments().getString(
          ACTION_URL
        )!!)
        )
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
    private const val IMAGE = "IMAGE"
    private const val ACTION_URL = "ACTION_URL"
    fun newInstance(image: String? = null,actionUrl: String? = null): InAppMessagingImageDialog {
      val dialog = InAppMessagingImageDialog()
      val args = Bundle()
      image?.let { args.putString(IMAGE,it) }
      actionUrl?.let { args.putString(ACTION_URL,it) }
      dialog.arguments = args
      return dialog
    }
  }
}
