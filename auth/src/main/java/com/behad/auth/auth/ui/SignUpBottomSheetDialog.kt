package com.behad.auth.auth.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.behad.auth.BehadUserManager
import com.behad.auth.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val modelKey = "sign_up_model"

class SignUpBottomSheetDialog private constructor() : BottomSheetDialogFragment() {

    private var container: LinearLayout? = null
    private var tvTitle: TextView? = null
    private var btnSignUp: Button? = null
    private var tvAnyQuestion: TextView? = null
    private var tvContact: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.sign_up_layout,
            container,
            false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        with(arguments?.getParcelable(modelKey) as Builder.BuilderModel?) {
            this?.let {
                setUpContents(it)
                initListeners(it)
            }
        }
    }

    private fun setUpContents(model: Builder.BuilderModel) {
        with(model) {
            tvTitle?.text = title
            tvTitle?.setTextColor(Color.parseColor(titleColor))
            btnSignUp?.text = buttonText
            btnSignUp?.setTextColor(Color.parseColor(buttonTextColor))
            btnSignUp?.backgroundTintList = ColorStateList.valueOf(Color.parseColor(buttonColor))
            tvAnyQuestion?.text = anyQuestionText
            tvAnyQuestion?.setTextColor(Color.parseColor(anyQuestionColor))
            tvContact?.text = contactText
            tvContact?.setTextColor(Color.parseColor(contactColor))
            tvContact?.setPaintFlags(tvContact?.paintFlags?.or(Paint.UNDERLINE_TEXT_FLAG) ?: 0)
        }
    }

    private fun initListeners(model: Builder.BuilderModel) {
        tvContact?.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(model.contactUrl),
                ),
            )
        }
        btnSignUp?.setOnClickListener {
            BehadUserManager.directToLoginPage(requireContext())
        }
    }

    private fun initViews(view: View?) {
        container = view?.findViewById(R.id.container)
        tvTitle = view?.findViewById(R.id.tv_title)
        btnSignUp = view?.findViewById(R.id.btn_sign_up)
        tvAnyQuestion = view?.findViewById(R.id.tv_any_question)
        tvContact = view?.findViewById(R.id.tv_contact)
    }

    fun showDialog(fragmentManager: FragmentManager) {
        show(fragmentManager, "sign_up_tag")
    }

    companion object {
        private fun newInstance(bundle: Bundle): SignUpBottomSheetDialog {
            val fragment = SignUpBottomSheetDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    class Builder {

        private val builderModel = BuilderModel()

        fun setTitle(title: String): Builder {
            builderModel.title = title
            return this
        }

        fun setTitleColor(color: String): Builder {
            builderModel.titleColor = color
            return this
        }

        fun setButtonColor(color: String): Builder {
            builderModel.buttonColor = color
            return this
        }

        fun setButtonText(text: String): Builder {
            builderModel.buttonText = text
            return this
        }

        fun setButtonTextColor(color: String): Builder {
            builderModel.buttonTextColor = color
            return this
        }

        fun setAnyQuestionText(text: String): Builder {
            builderModel.anyQuestionText = text
            return this
        }

        fun setAnyQuestionColor(color: String): Builder {
            builderModel.anyQuestionColor = color
            return this
        }

        fun setContactText(text: String): Builder {
            builderModel.contactText = text
            return this
        }

        fun setContactColor(color: String): Builder {
            builderModel.contactColor = color
            return this
        }

        fun setContactUrl(url: String): Builder {
            builderModel.contactUrl = url
            return this
        }

        fun build(): SignUpBottomSheetDialog {
            return with(Bundle()) {
                putParcelable(modelKey, builderModel)
                return newInstance(this)
            }
        }

        data class BuilderModel(
            var title: String? = "Behad ilovalaridan to'liq foydalanish uchun ro'yxatdan o'ting:",
            var titleColor: String? = "#000000",
            var buttonText: String? = "Sign Up",
            var buttonColor: String? = "#a6abb3",
            var buttonTextColor: String? = "#ffffff",
            var anyQuestionText: String? = "Savollaringiz bormi?",
            var anyQuestionColor: String? = "#a6abb3",
            var contactText: String? = "Bog'lanish",
            var contactColor: String? = "#000000",
            var contactUrl: String? = "https://bosing.uz/tgbehadbot",
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(title)
                parcel.writeString(titleColor)
                parcel.writeString(buttonText)
                parcel.writeString(buttonColor)
                parcel.writeString(buttonTextColor)
                parcel.writeString(anyQuestionText)
                parcel.writeString(anyQuestionColor)
                parcel.writeString(contactText)
                parcel.writeString(contactColor)
                parcel.writeString(contactUrl)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<BuilderModel> {
                override fun createFromParcel(parcel: Parcel): BuilderModel {
                    return BuilderModel(parcel)
                }

                override fun newArray(size: Int): Array<BuilderModel?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
