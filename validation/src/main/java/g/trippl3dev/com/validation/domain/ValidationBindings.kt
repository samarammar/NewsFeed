package g.trippl3dev.com.validation.domain

import android.databinding.BindingAdapter
import android.view.View
import g.trippl3dev.com.validation.data.ValidationTag

@BindingAdapter("validation:error")
fun View.setError(error: String) {
    val validationTag = tag ?: ValidationTag()
    if (validationTag is ValidationTag) {
        validationTag.errorText = error
    }
    this.tag = validationTag
}

@BindingAdapter("validation:pattern")
fun View.setPattern(pattern: String) {
    val validationTag = tag ?: ValidationTag()
    if (validationTag is ValidationTag) {
        validationTag.pattern = pattern
    }
    this.tag = validationTag
}

@BindingAdapter("validation:type")
fun View.setType(type: Int) {
    val validationTag = tag ?: ValidationTag()
    if (validationTag is ValidationTag) {
        validationTag.viewType = type
    }
    this.tag = validationTag
}

@BindingAdapter("validation:empty")
fun View.setEmpty(empty: String) {
    val validationTag = tag ?: ValidationTag()
    if (validationTag is ValidationTag) {
        validationTag.emptyError = empty
    }
    this.tag = validationTag
}

@BindingAdapter("validation:isIgnore")
fun View.isIgnore(isIgnore: Boolean) {
    val validationTag = tag ?: ValidationTag()
    if (validationTag is ValidationTag) {
        validationTag.isIgnore = isIgnore
    }
    this.tag = validationTag
}

@BindingAdapter("validation:watch")
fun View.enableWathcing(watch: Boolean) {
    val validationTag = tag ?: ValidationTag()
    if (validationTag is ValidationTag) {
        validationTag.isWatch = watch
    }
    this.tag = validationTag
}
