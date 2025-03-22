package com.viettel.mocha.rmlivestream
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class RmUtils private constructor() {
    companion object {
        @Volatile
        private var instance: RmUtils? = null

        fun getInstance(): RmUtils {
            return instance ?: synchronized(this) {
                instance ?: RmUtils().also { instance = it }
            }
        }
    }


    fun truncateText(input: String, maxLength: Int): String {
        return if (input.length > maxLength) {
            input.take(maxLength) + "..."
        } else {
            input
        }
    }

    fun setupExpandableTextView(context: Context, textView: TextView, fullText: String, maxLength: Int, seeMore: String, seeLess: String, color: Int, fontResId: Int) {
        if (fullText.length <= maxLength) {
            textView.text = fullText
            return
        }

        var isExpanded = false
        val truncatedText = fullText.take(maxLength) + "..."
        val typeface: Typeface? = ResourcesCompat.getFont(context, fontResId)





        fun updateText() {
            val displayText = if (isExpanded) fullText + seeLess else truncatedText + seeMore
            val spannableString = SpannableString(displayText)

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    isExpanded = !isExpanded
                    updateText()
                }
            }

            val startIndex = displayText.indexOf(if (isExpanded) seeLess else seeMore)
            spannableString.setSpan(clickableSpan, startIndex, displayText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            spannableString.setSpan(
                ForegroundColorSpan(color),
                startIndex, displayText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            typeface?.let {
                spannableString.setSpan(
                    TypefaceSpan(it),
                    startIndex, displayText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            textView.text = spannableString
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.isClickable = true
            textView.isFocusable = true
        }

        updateText()
    }

}
