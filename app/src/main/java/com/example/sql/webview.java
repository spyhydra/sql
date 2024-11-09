package com.example.sql;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class webview extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);

        // Enable JavaScript (optional)


        // Set WebViewClient to handle page navigation within the WebView
        webView.setWebViewClient(new WebViewClient());

        // Load a URL
        String url = "https://www.google.com"; // Replace with your desired URL
        webView.loadUrl(url);
    }
}
