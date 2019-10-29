package com.newsdata.module


import android.annotation.TargetApi
import android.content.Context
import android.databinding.ObservableField
import android.os.Build
import com.newsdomain.diinterfaces.ProjectScope


import java.util.*
import javax.inject.Inject


@ProjectScope
class LanguageModule @Inject
constructor(val preferenceModule: PreferenceModule) {
    var isArabic = ObservableField(true)
    var isEnglish = ObservableField(false)
    var font = ObservableField<String>()
    private var locality = ObservableField("ar")

    val isLanguageArabic: Boolean?
        get() = isArabic.get()

    val language: Int
        get() = if (isLanguageArabic!!) 1 else 2

    init {
        val language = preferenceModule.language
//        if (language == "ar") {
//            locality.set("ar")
//            isArabic.set(true)
//            isEnglish.set(false)
//        } else {
            locality.set("en")
            isArabic.set(false)
            isEnglish.set(true)
//        }
    }

    fun getLocality(): String? {
        return locality.get()
    }



    fun setLocality(locality: String) {
        this.locality.set(locality)
        if (locality == "ar") {
            isArabic.set(true)
            isEnglish.set(false)
        } else {
            isArabic.set(false)
            isEnglish.set(true)
        }
    }

    fun invert(){
        if (isArabic.get()!!)setLocality("en")
        else setLocality("ar")
    }
    fun persist(language:String ) {
        preferenceModule.language = language
    }

    fun setLocale(context: Context, language: String): Context {
        persist(language)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)

    }

    fun attach(context: Context): Context {
        return setLocale(context,getLocality()!!)
    }
    fun getPersistedData():String? {
        return getLocality()
    }



    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context:Context, language:String ):Context  {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    @SuppressWarnings("deprecation")
    private fun updateResourcesLegacy(context:Context, language:String ):Context  {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val  configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}
