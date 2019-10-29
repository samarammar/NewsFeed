package com.samar.newsfeeds.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.*
import android.net.Uri
import android.os.Build
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.*

import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.*
import android.webkit.URLUtil
import android.widget.*
import com.blankj.utilcode.util.StringUtils
import com.samar.newsfeeds.R
import com.samar.newsfeeds.dagger.module.DOMAIN


import com.squareup.picasso.Picasso
import java.io.File
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import com.stfalcon.frescoimageviewer.ImageViewer
import com.trippl3dev.listlibrary.customview.ArchitectureListView

//private var target: Target? = null


@BindingAdapter("align")
fun View.align(arabic: Boolean) {
    val layoutParams = layoutParams as RelativeLayout.LayoutParams
    if (arabic) {
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
    } else {
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
    }
    setLayoutParams(layoutParams)
}


@BindingAdapter(
    "bind:compoundDrawableSize", "android:drawableLeft", "android:drawableStart",
    "android:drawableRight", "android:drawableEnd", requireAll = false
)
fun TextView.setCompoundDrawableSize(
    px: Float, @IntegerRes drawableLeft: Drawable?, @IntegerRes drawableStart: Drawable?,
    @IntegerRes drawableRight: Drawable?, @IntegerRes drawableEnd: Drawable?
) {
    val compoundDrawableLeft = drawableLeft ?: drawableStart
    val compoundDrawableRight = drawableRight ?: drawableEnd
    compoundDrawableLeft?.setBounds(0, 0, px.toInt(), px.toInt())
    compoundDrawableRight?.setBounds(0, 0, px.toInt(), px.toInt())
    this.setCompoundDrawables(compoundDrawableLeft, null, compoundDrawableRight, null)
}

//@BindingAdapter("showActionsAccorddingOfferStatus", "isCustomer", "setHandler")
//fun LinearLayout.showActionsAccordingOfferStatus(offer: OfferModel?, isCustomer: Boolean, handler: OfferActionHandler) {
//    if (offer == null) return
//    OfferStatusHandler().getListActions(this.context!!, offer.offerModel, isCustomer, this, handler)
//}

@BindingAdapter("disableUntilHaveDataIn")
fun EditText.disableUntilHaveDataIn(view: View) {
    val anotherEditText = view as EditText
    this@disableUntilHaveDataIn.isEnabled = !StringUtils.isTrimEmpty(anotherEditText.text.toString())
    anotherEditText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            this@disableUntilHaveDataIn.isEnabled = !StringUtils.isTrimEmpty(s?.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
//
//@BindingAdapter("showActionsAccorddingOfferStatuschat", "isCustomer", "setHandler")
//fun LinearLayout.showActionsAccordingOfferStatusChat(offer: OfferModel?, isCustomer: Boolean, handler: OfferActionHandler) {
//    if (offer == null) return
//    OfferStatusHandler().getChatListActions(this.context!!, offer.offerModel, isCustomer, this, handler)
//}

@BindingAdapter("rotattoDirection")
fun View.alignusingRotation(arabic: Boolean) {
    rotationY = if (arabic) {
        180f
    } else {
        0f
    }
}

@BindingAdapter("direction")
fun View.direction(arabic: Boolean) {
    if (arabic) {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_RTL)
    } else {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR)
    }
}

@BindingAdapter("gravity")
fun View.gravity(arabic: Boolean) {
    if (this is EditText) {
        gravity = if (arabic) {
            Gravity.RIGHT or Gravity.CENTER_VERTICAL
        } else {
            Gravity.LEFT or Gravity.CENTER_VERTICAL
        }
    } else if (this is TextView) {
        gravity = if (arabic) {
            Gravity.RIGHT or Gravity.CENTER_VERTICAL
        } else {
            Gravity.LEFT or Gravity.CENTER_VERTICAL
        }
    } else if (this is LinearLayout) {
        gravity = if (arabic) {
            Gravity.RIGHT or Gravity.CENTER_VERTICAL
        } else {
            Gravity.LEFT or Gravity.CENTER_VERTICAL
        }
    } else if (this is NavigationView) {
        val params = getLayoutParams() as DrawerLayout.LayoutParams
        if (arabic) {
            params.gravity = (Gravity.RIGHT or Gravity.CENTER_VERTICAL)
        } else {
            params.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        }
    }
}


@BindingAdapter("drawableLeftDirection")
fun View.drawable(arabic: Boolean?) {
    var left: Drawable? = null
    if (this is TextView) {
        left = compoundDrawables[0]
        if (arabic!!) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, left, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(left, null, null, null)
        }
    } else if (this is Button) {
        left = compoundDrawables[2]
        if (arabic!!) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, left, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(left, null, null, null)
        }
    }

}


@SuppressLint("ResourceType")
@BindingAdapter("addCounterBelowView", "minLimit", "maxLimit", requireAll = false)
fun EditText.addCounter(below: Int, min: Int = 15, max: Int = 500) {

    val minimum = if (min == 0) 15 else min
    val limit = if (max == 0) 500 else max

    val parent = this.parent
    val addCounterBelowView = (parent as ViewGroup).findViewById<View>(below)
    val textView = TextView(this.context)
    textView.id = 0X552

    (this as EditText).addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            textView.text = "${s?.length} / $limit"
            when {
                s.toString().length < minimum -> textView.setTextColor(
                    ContextCompat.getColor(
                        this@addCounter.context,
                        R.color.colorAccent
                    )
                )
                s.toString().length > limit -> textView.setTextColor(
                    ContextCompat.getColor(
                        this@addCounter.context,
                        R.color.colorAccent
                    )
                )
                else -> textView.setTextColor(ContextCompat.getColor(this@addCounter.context, R.color.colorAccent))
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })

    when (parent) {
        is ConstraintLayout -> {
            parent.addView(textView)
            val set = ConstraintSet()
            set.clone(parent)
            set.connect(textView.id, ConstraintSet.LEFT, addCounterBelowView.id, ConstraintSet.LEFT, 20)
            set.connect(textView.id, ConstraintSet.TOP, addCounterBelowView.id, ConstraintSet.BOTTOM, 10)
            set.applyTo(parent)
        }
    }
    textView.text = "${this.text?.toString()?.length} / $limit"
    when {
        this.text?.toString()?.length!! < minimum -> textView.setTextColor(
            ContextCompat.getColor(
                this@addCounter.context,
                R.color.colorAccent
            )
        )
        this.text?.toString()?.length!! > limit -> textView.setTextColor(
            ContextCompat.getColor(
                this@addCounter.context,
                R.color.colorAccent
            )
        )
        else -> textView.setTextColor(ContextCompat.getColor(this@addCounter.context, R.color.colorAccent))
    }
}

@BindingAdapter("openBrowser")
fun View.openBrowser(uri: String?) {
    this.setOnClickListener {
        if (uri != null && !uri.isEmpty() && URLUtil.isValidUrl(uri))
            this.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))

    }
}

//if (url != null){
//    var progressBar: ProgressBar? = null
//    if(parent is ViewGroup) {
//        if (   (parent as ViewGroup).findViewById<ProgressBar>(0X022221) == null) {
//            progressBar = ProgressBar(this.context!!,null, android.R.attr.progressBarStyleHorizontal)
//            progressBar.id = 0X022221
//            (parent as ViewGroup).addView(progressBar)
//            progressBar.layoutParams = layoutParams
////
////                if((parent as ViewGroup) is ConstraintLayout){
////                    progressBar.layoutParams = ConstraintLayout.LayoutParams(layoutParams)
////                }else if((parent as ViewGroup) is RelativeLayout){
////                    progressBar.layoutParams = RelativeLayout.LayoutParams(layoutParams)
////                }else if((parent as ViewGroup) is LinearLayout){
////                    progressBar.layoutParams = LinearLayout.LayoutParams(layoutParams)
////                }else if((parent as ViewGroup) is FrameLayout){
////                    progressBar.layoutParams = FrameLayout.LayoutParams(layoutParams)
////                }
//
//        }
//    }
//
//    val progressListener = object : ProgressListener {
//
//        override fun update(bytesRead: Long, contentLength: Long, thanks: Boolean) {
//            val progress = (100 * bytesRead / contentLength).toInt()
//
//            // Enable if you want to see the progress with logcat
//            Log.v("Picasso Looooooading -- > ", "Progress: $progress%");
//            progressBar?.progress = progress
////                if (thanks) {
////                    Log.i(LOG_TAG, "Done loading")
////                }
//
//        }
//    }
//    val mOkHttpClient = OkHttpClient.Builder().addInterceptor(object: Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val originalResponse = chain.proceed(chain.request())
//            return originalResponse.newBuilder()
//                    .body(ProgressResponseBody(originalResponse.body(), progressListener))
//                    .header("Cache-Control", "max-age=" + (60 * 60 * 24 * 365))
//                    .build()
//        }
//    }).cache(Cache(this.context!!.cacheDir,Long.MAX_VALUE)).build()
//
//    val builder = Picasso.Builder(this.context!!)
//    builder.downloader(OkHttp3Downloader(mOkHttpClient))
//    val built = builder.build()
//    built.setIndicatorsEnabled(true)
//    built.isLoggingEnabled = true
//    built.cancelRequest(this)
//    built.load(if (URLUtil.isValidUrl(url) || URLUtil.isFileUrl(url)) url else "http://104.248.188.131/$url")
//            .resize(400,150)
//            .into(this,object:Callback{
//                override fun onSuccess() {
//                    if (parent is ViewGroup) {
//                        if ((parent as ViewGroup).findViewById<ProgressBar>(0X022221) != null) {
//                            (parent as ViewGroup).removeView((parent as ViewGroup).findViewById<ProgressBar>(0X022221))
//                        }
//                    }
//                }
//
//                override fun onError(e: Exception?) {
//                    if (parent is ViewGroup) {
//                        if ((parent as ViewGroup).findViewById<ProgressBar>(0X022221) != null) {
//                            (parent as ViewGroup).removeView((parent as ViewGroup).findViewById<ProgressBar>(0X022221))
//                        }
//
//                    }
//                }
//
//            })
//}
@BindingAdapter("setImage")
fun ImageView.setImage(url: String?) {
    if (!url.equals("") && url != null)
        Picasso.get()
            .load(if (URLUtil.isValidUrl(url) || URLUtil.isFileUrl(url)) url else "$url")
//            .placeholder(R.drawable.close)
                .fit()
            .into(this)


}




@BindingAdapter("setImage2")
fun ImageView.setImage2(url: String?) {
        Picasso.get()
            .load(url)
//            .placeholder(R.drawable.close)
//                .fit()
            .into(this)


}
@BindingAdapter("android:drawableLeft")
fun setDrawableLeft(view: TextView, resourceId: Int) {
    val drawable = ContextCompat.getDrawable(view.context, resourceId)
    setIntrinsicBounds(drawable)
    val drawables = view.compoundDrawables
    view.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3])
}

private fun setIntrinsicBounds(drawable: Drawable?) {
    drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
}



//
//}

@BindingAdapter("setAlphaAccording")
fun ImageView.setAlphaAccording(view: EditText) {
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isEmpty()) {
                this@setAlphaAccording.alpha = 0.3f
            } else {
                this@setAlphaAccording.alpha = 1.0f
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })
}

@BindingAdapter("setColor")
fun TextView.setTheTextColor(color: String?) {
    if (color == null) return
    this.setTextColor(Color.parseColor(color))
}

@BindingAdapter("setBackColor")
fun View.setTheBackColor(color: String?) {
    if (color == null) return
    this.setBackgroundColor(Color.parseColor(color))
}

//
//@BindingAdapter("setImage","widthImg","heightImg")
//fun ImageView.setImage(url: String?,width: Int,height:Int) {
//    if (url != null)
//        Picasso.get()
//                .load(if (URLUtil.isValidUrl(url) || URLUtil.isFileUrl(url)) url else "http://104.248.188.131/$url")
//                .resize(width,height)
//                .into(this@setImage)
//
//}

@BindingAdapter("layout_height")
fun View.setLayoutHeight(height: Int) {
    val layoutParams = this.layoutParams
    layoutParams.height = height
    this.layoutParams = layoutParams
}




@BindingAdapter(value = ["accordignFill", "images"], requireAll = true)
fun ImageView.accordignFill(accordignFill: EditText, images: Pair<Int, Int>) {
    if (accordignFill.text.toString().trim().isEmpty()) {
        this@accordignFill.setImageResource(images.second)
    } else {
        this@accordignFill.setImageResource(images.first)
    }
    accordignFill.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().trim().isEmpty()) {
                this@accordignFill.setImageResource(images.second)
            } else {
                this@accordignFill.setImageResource(images.first)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })
}

@BindingAdapter("setLineInCenter")
fun TextView.putLineCenterTextView(isCenter: Boolean) {
    this.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
}

const val SELECTED: Int = 2
const val UNSELECTED: Int = 1
const val INDECATOR: Int = 0



@BindingAdapter("bigText", "smallText")
fun TextView.setTextWithBigAndSmallText(big: String, small: String) {
    val overallText = "$big $small"
    val spannable = SpannableStringBuilder()
    spannable.append(overallText)
    spannable.setSpan(
        RelativeSizeSpan(1.5f),
        overallText.indexOf(big),
        overallText.indexOf(big) + big.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        RelativeSizeSpan(0.6f),
        overallText.indexOf(small),
        overallText.indexOf(small) + small.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannable
}

//
//@BindingAdapter("smallText2", "bigText2", "smallText1")
//fun TextView.setTextWithBigAndSmallText2(small2: String, big: String, small: String) {
//    val overallText = "$small2 + \n + $big + \n + $small"
//    val spannable = SpannableStringBuilder()
//    spannable.append(overallText)
//    spannable.setSpan(
//        RelativeSizeSpan(1.5f),
//        overallText.indexOf(big),
//        overallText.indexOf(big) + big.length,
//        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//    )
//    spannable.setSpan(
//        RelativeSizeSpan(0.6f),
//        overallText.indexOf(small),
//        overallText.indexOf(small) + small.length,
//        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//    )
//
//    spannable.setSpan(
//        RelativeSizeSpan(0.6f),
//        overallText.indexOf(small2),
//        overallText.indexOf(small2) + small2.length,
//        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//    )
//    this.text = spannable
//}

//@BindingAdapter("setTextAndChangeCurrency")
//fun TextView.setTextBigSmall(big: String?) {
//    val sR = context.getString(R.string.SR)
//    val offer = context.getString(R.string.offerItem)
//    if (big == null) return
//    val overallText = "$big"
//    val spannable = SpannableStringBuilder()
//    spannable.append(overallText)
////    spannable.setSpan(AbsoluteSizeSpan(resources.getDimension(R.dimen._7ssp).toInt(), false), overallText.indexOf(big), overallText.indexOf(big)+big.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//    if (big.contains(sR))
//        spannable.setSpan(RelativeSizeSpan(0.7f), overallText.indexOf(sR), overallText.indexOf(sR) + sR.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//    if (big.contains(offer))
//        spannable.setSpan(RelativeSizeSpan(0.7f), overallText.indexOf(offer), overallText.indexOf(offer) + offer.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//    this.text = spannable
//}

@BindingAdapter("prefix")
fun TextView.prefix(@StringRes resourc: String) {
    this.text = "$resourc ${this.text}"
}

@BindingAdapter("suffix")
fun TextView.suffix(resourc: String) {
    this.text = "${this.text} $resourc"
}
//@BindingAdapter("setBackground")
//fun TextView.setBackground(selected: Boolean) {
//    if (selected)
//        this.background = context.resources.getDrawable(R.drawable.hospital_details_button)
//}

@BindingAdapter("setMandatory")
fun TextView.setMandatory(add: Boolean) {
    if (!add) return

    var textViewText: String = this.text.toString()

    var myText = "*  $textViewText"

    // textViewText= textViewText[0].toString()
    val wordSpan = SpannableString(myText)
    wordSpan.setSpan(ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = wordSpan
}

@BindingAdapter("percent", "color")
fun ProgressBar.setProgressColorForProfile(progress: Int, color: Int) {
    val colorPosition = (progress / 10)
    this.progress = colorPosition
    var progressBarDrawable: LayerDrawable = this.progressDrawable as LayerDrawable
    var progressDrawable: Drawable = progressBarDrawable.getDrawable(1)
    progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
}


//@BindingAdapter("setRoundBackGround")
//fun CheckBox.setRoundBackGround(radius: Float) {
//    var stringBuilder: SpannableStringBuilder = SpannableStringBuilder()
//    stringBuilder.append(" " + this.text + " ")
//    stringBuilder.setSpan(RoundedBackgroundSpan(this.context, radius), 0, stringBuilder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//
//    this.text = stringBuilder
//
//}


@BindingAdapter("setCurentdateAndTime")
fun TextView.setCurentdateAndTime(isDate: Boolean) {
    val calendar = Calendar.getInstance()
    val dateAndTime = SimpleDateFormat("yyyy - MM - dd h:mm a", Locale.ENGLISH)
    val date = SimpleDateFormat("yyyy - MM - dd ", Locale.ENGLISH)
    val time = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    val strDate = dateAndTime.format(calendar.time)
    this.text = strDate
}


@BindingAdapter("backgroundDrawableColor")
fun View.setBackgroundDrawableColor(color: Int) {
    val background = background
    when (background) {
        is ShapeDrawable -> background.paint.color = color
        is GradientDrawable -> background.setColor(color)
        is ColorDrawable -> background.color = color
    }
}

@BindingAdapter("convertToStringDate")
fun TextView.convertToStringDate(date: String?) {
    if (date != null) {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val toFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        try {
            val newDate = format.parse(date)
            this.text = toFormat.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    } else {
        this.text = ""
    }
}

@BindingAdapter("setImageResource")
fun ImageView.setImageResource(image: Int) {
    this.setImageResource(image)
}

@BindingAdapter("setImageResourceString")
fun ImageView.setImageResourceString(image: String) {
    val imageInt = this.context.resources.getIdentifier(image, "drawable", this.context.packageName)
    this.setImageResource(imageInt)
}

@BindingAdapter("setImageFile")
fun ImageView.setImageFile(image: File?) {
    if (image != null) {
        Picasso.get().load(image).into(this)
    }
}

@BindingAdapter("setName")
fun TextView.setName(name: Int) {
    this.text = this.context.getString(name)
}

@BindingAdapter("formatPriceEditText")
fun EditText.formatPriceEditText(formate: Boolean) {
    val cleanString = this.text.replace("[$,.]".toRegex(), "")
    val formattedText = cleanString?.formatPrice()
    this@formatPriceEditText.setText(formattedText)
    this@formatPriceEditText.setSelection(formattedText?.length!!)
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@formatPriceEditText.removeTextChangedListener(this)
            val cleanString = s.toString().replace("[$,.]".toRegex(), "")
            val formattedText = cleanString?.formatPrice()
            this@formatPriceEditText.setText(formattedText)
            this@formatPriceEditText.setSelection(formattedText?.length!!)
            this@formatPriceEditText.addTextChangedListener(this)
        }

    })
}

@BindingAdapter("setPrice")
fun TextView.setPrice(price: String) {
    val nf_us = NumberFormat.getInstance(Locale.ENGLISH)
    val number_us = nf_us.format(java.lang.Double.parseDouble(price))
    this.text = number_us
}

fun View.setViewState(xmlLayout: Int, colorRes: Int, hide: Boolean) {
    val view: View = LayoutInflater.from(context).inflate(xmlLayout, null)
    val loadingContainer = LinearLayout(context)
    loadingContainer.layoutParams = this.layoutParams
    loadingContainer.setBackgroundColor(ContextCompat.getColor(context, colorRes))
    loadingContainer.addView(view)
    loadingContainer.id = this.id - 1
    loadingContainer.gravity = Gravity.CENTER
    (this.parent as ViewGroup).addView(loadingContainer)
    this.visibility = if (hide) View.INVISIBLE else View.VISIBLE
}


fun View.setBorder(color: Int, width: Int) {
    val border = GradientDrawable()
    border.setColor(color)
    border.setStroke(width, color); //black border with full opacity
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        this.setBackgroundDrawable(border)
    } else {
        this.background = border
    }
}

fun NestedScrollView.setLister(listener: RecyclerView.OnScrollListener, recyclerView: RecyclerView) {
    this.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
    { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        listener.onScrolled(recyclerView, scrollX, scrollY)
    })



    fun ProgressBar.changeColor(color: Int) {
        var progressBarDrawable: LayerDrawable = this.progressDrawable as LayerDrawable
        var progressDrawable: Drawable = progressBarDrawable.getDrawable(1)
        progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)

    }


}

fun Context.getActivity(): AppCompatActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = (context as ContextWrapper).baseContext
    }
    return null
}

interface OnImageViewSizeChanged {
    operator fun invoke(v: ImageView, w: Int, h: Int)
}

fun Bitmap.resize(maxWidth: Int, maxHeight: Int): Bitmap {
    var image = this
    if (maxHeight > 0 && maxWidth > 0) {
        val width = image.width
        val height = image.height
        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight
        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }
        image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
        return image
    } else {
        return image
    }
}


fun String.formatPrice(): String? {
    if (StringUtils.isEmpty(this)) return ""
    val nf_us = NumberFormat.getInstance(Locale.ENGLISH)
    val number_us = nf_us.format(java.lang.Double.parseDouble(this))
    return number_us
}

fun formatPrice(price: Double): String? {
    val nf_us = NumberFormat.getInstance(Locale.ENGLISH)
    val number_us = nf_us.format(price)
    return number_us
}

fun formatPriceOrNum(price: Int): String? {
    return NumberFormat.getNumberInstance(Locale.ENGLISH).format(price)
}


fun convertToStringDate(time:String): String? {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val toFormat = SimpleDateFormat("EEEE dd MMMM", Locale("ar"))
    try {
        val newDate = format.parse(time)
        return toFormat.format(newDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}

fun String.convertToStringDateOnlyBaseFormat(): String? {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val toFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    try {
        val newDate = format.parse(this)
        return toFormat.format(newDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}

fun String.clearStringFromPrice(): String {
    return this.replace("[$,.]".toRegex(), "")
}

fun String.convertToStringDateOnly(): String? {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val toFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
    try {
        val newDate = format.parse(this)
        return toFormat.format(newDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null


}


@BindingAdapter("bigText", "prefix", "postfix", "bigColor")
fun TextView.setTextWithStle(big: String, prefix: String, postfix: String, bidColor: Int) {
    val overallText = "$prefix  $big  $postfix"
    val spannable = SpannableStringBuilder()
    spannable.append(overallText)
    spannable.setSpan(
        RelativeSizeSpan(1.4f),
        overallText.indexOf(big),
        overallText.indexOf(big) + big.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        ForegroundColorSpan(bidColor),
        overallText.indexOf(big),
        overallText.indexOf(big) + big.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        RelativeSizeSpan(.8f),
        overallText.indexOf(prefix),
        overallText.indexOf(prefix) + prefix.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        RelativeSizeSpan(.8f),
        overallText.indexOf(postfix),
        overallText.indexOf(postfix) + postfix.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannable
}

@BindingAdapter("hint", "star")
fun TextView.markRequired(hint: String, star: String) {
    val spannable = SpannableStringBuilder()
    val overallText = "$hint $star"
    spannable.append(overallText)
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        overallText.indexOf(star),
        overallText.indexOf(star) + star.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.hint = spannable
}

@BindingAdapter("coloredText", "prefix", "postfix", "coloredColor", requireAll = false)
fun TextView.setTextWithStyleColor(big: String, prefix: String, postfix: String, bidColor: Int) {
    val overallText = "$prefix $big $postfix"
    val spannable = SpannableStringBuilder()
    spannable.append(overallText)
//    spannable.setSpan(
//        RelativeSizeSpan(1.4f),
//        overallText.indexOf(big),
//        overallText.indexOf(big) + big.length,
//        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//    )
    spannable.setSpan(
        ForegroundColorSpan(bidColor),
        overallText.indexOf(big),
        overallText.indexOf(big) + big.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        RelativeSizeSpan(1f),
        overallText.indexOf(prefix),
        overallText.indexOf(prefix) + prefix.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.setSpan(
        RelativeSizeSpan(1f),
        overallText.indexOf(postfix),
        overallText.indexOf(postfix) + postfix.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannable
}





