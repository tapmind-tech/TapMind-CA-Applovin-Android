package com.tapminds.tampind.ads.banner

import android.content.Context
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class AdView(context: Context) : FrameLayout(context) {

    public fun createView() {
        removeAllViews()

        val htmlData = """
    <html>
        <body>
            <h1>Hello WebView</h1>
            <p>This is HTML content loaded as a string.</p>
        </body>
    </html>
""".trimIndent()

        val webView = WebView(context)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadsImagesAutomatically = true

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)

        addView(webView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}