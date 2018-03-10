package com.project.jeremyg.myinstagram.views.dialogs

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Typeface
import android.widget.TextView
import android.os.Build
import android.widget.LinearLayout
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.webkit.*
import android.widget.FrameLayout
import com.project.jeremyg.myinstagram.instagram.InstagramData

class InstagramDialog(context: Context, private val mUrl: String) : Dialog(context) {

    companion object {

        internal val DIMENSIONS_LANDSCAPE = floatArrayOf(460f, 260f)
        internal val DIMENSIONS_PORTRAIT = floatArrayOf(280f, 420f)
        internal val FILL = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        internal val MARGIN = 4
        internal val PADDING = 2
        private val TAG = "INSTAGRAM DIALOG"
    }

    private var mSpinner: ProgressDialog? = null
    private var mWebView: WebView? = null
    private var mContent: LinearLayout? = null
    private var mTitle: TextView? = null

    override protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSpinner = ProgressDialog(getContext())
        mSpinner!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mSpinner!!.setMessage("Loading...")
        mContent = LinearLayout(getContext())
        mContent!!.orientation = LinearLayout.VERTICAL
        setUpTitle()
        setUpWebView()

        val display = getWindow().getWindowManager().getDefaultDisplay()
        val scale = getContext().getResources().getDisplayMetrics().density
        val dimensions = if (display.getWidth() < display.getHeight())
            DIMENSIONS_PORTRAIT
        else
            DIMENSIONS_LANDSCAPE

        addContentView(mContent, FrameLayout.LayoutParams(
                (dimensions[0] * scale + 0.5f).toInt(), (dimensions[1] * scale + 0.5f).toInt()))


        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG, "REMOVING COOKIES")
            cookieManager.removeAllCookies(null)
            CookieManager.getInstance().flush()
        }

    }

    private fun setUpTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mTitle = TextView(getContext())
        mTitle!!.text = "Instagram"
        mTitle!!.setTextColor(Color.WHITE)
        mTitle!!.typeface = Typeface.DEFAULT_BOLD
        mTitle!!.setBackgroundColor(Color.BLACK)
        mTitle!!.setPadding(MARGIN + PADDING, MARGIN, MARGIN, MARGIN)
        mContent!!.addView(mTitle)
    }

    private fun setUpWebView() {
        mWebView = WebView(getContext())
        mWebView!!.isVerticalScrollBarEnabled = false
        mWebView!!.isHorizontalScrollBarEnabled = false
        mWebView!!.webViewClient = OAuthWebViewClient()
        mWebView!!.settings.javaScriptEnabled = true
        mWebView!!.loadUrl(mUrl)
        mWebView!!.layoutParams = FILL
        mContent!!.addView(mWebView)
    }

    interface OAuthDialogListener {
        fun onComplete(accessToken: String)

        fun onError(error: String)
    }

    private inner class OAuthWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.d(TAG, "Redirecting URL " + url)

            if (url.startsWith(InstagramData.CALLBACK_URL)) {
                val urls = url.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                //mOAuthDialogListener.onComplete(urls[1])
                this@InstagramDialog.dismiss()
                return true
            } else {
                Log.d(TAG, "URL didn't started with the callback url")
            }
            return false

        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
            Log.d(TAG, "Page error: " + error.toString())
            //mOAuthDialogListener.onError(error.toString())
            this@InstagramDialog.dismiss()
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            Log.d(TAG, "Loading URL: " + url)

            super.onPageStarted(view, url, favicon)
            mSpinner!!.show()
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            val title = mWebView!!.title
            if (title != null && title.length > 0) {
                mTitle!!.text = title
            }
            Log.d(TAG, "onPageFinished URL: " + url)
            mSpinner!!.dismiss()
        }
    }
}