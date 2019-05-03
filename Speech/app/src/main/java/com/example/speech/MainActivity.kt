package com.example.speech

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import java.util.*
import android.content.ComponentName



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestStt()

    }

    fun requestStt() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    handleText(result[0])
                }
            }
        }
    }

    fun handleText(text: String) {
        if (text.toLowerCase().contains("facebook")) {
            openAppWithPackageName("com.facebook.katana")
        }
        else if (text.toLowerCase().contains("nhắn tin")){
            openAppWithPackageName("com.facebook.orca")
        }
        else if (text.toLowerCase().contains("zalo")){
            openAppWithPackageName("com.zing.zalo")
        }
        else if (text.toLowerCase().contains("điểm danh")){
            openAppWithPackageName("vn.facenet.faceplus.uet")
        }
        else if (text.toLowerCase().contains("google chrome")){
            openAppWithPackageName("com.android.chrome")
        }
        else if (text.toLowerCase().contains("gọi điện")){
             openAppWithPackageName("com.android.phone")
        }
        else {
            openAppWithPackageName("com.android.vending")
        }
    }

    fun openAppWithPackageName(name: String) {
        if (isPackageInstalled(name))
            startActivity(packageManager.getLaunchIntentForPackage(name))
        else {
            openChPlay(packageName)
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        var found = true
        try {
            packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {

            found = false
        }
        return found
    }

    fun openChPlay(packageName: String) {
        val uri = Uri.parse("market://details?id=" + packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)
                )
            )
        }

    }

    fun showContacts() {
        val i = Intent()
        i.component = ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity")
        i.action = "android.intent.action.MAIN"
        i.addCategory("android.intent.category.LAUNCHER")
        i.addCategory("android.intent.category.DEFAULT")
        startActivity(i)
    }

}
