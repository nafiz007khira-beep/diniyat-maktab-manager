package com.diniyat.maktab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.Toast;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    private WebView webView;
    private static final int CREATE_BACKUP = 4101, OPEN_BACKUP = 4102;
    private String pendingJson = "";
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        webView.addJavascriptInterface(new BackupBridge(), "MaktabAndroid");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/index.html");
    }
    public class BackupBridge {
        @JavascriptInterface public void saveBackup(String json) {
            runOnUiThread(() -> {
                pendingJson = json == null ? "{}" : json;
                Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("application/json");
                i.putExtra(Intent.EXTRA_TITLE, "diniyat-maktab-backup-" + new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(new java.util.Date()) + ".json");
                startActivityForResult(i, CREATE_BACKUP);
            });
        }
        @JavascriptInterface public void restoreBackup() {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("application/json");
                startActivityForResult(i, OPEN_BACKUP);
            });
        }
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null || data.getData() == null) return;
        Uri uri = data.getData();
        try {
            if (requestCode == CREATE_BACKUP) {
                try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                    out.write(pendingJson.getBytes(StandardCharsets.UTF_8));
                }
                Toast.makeText(this, "ব্যাকআপ সংরক্ষণ হয়েছে", Toast.LENGTH_LONG).show();
                webView.evaluateJavascript("window.onNativeBackupSaved && window.onNativeBackupSaved()", null);
            } else if (requestCode == OPEN_BACKUP) {
                StringBuilder sb = new StringBuilder();
                try (InputStream in = getContentResolver().openInputStream(uri);
                     BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                    String line; while ((line = r.readLine()) != null) sb.append(line);
                }
                String encoded = android.util.Base64.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8), android.util.Base64.NO_WRAP);
                webView.evaluateJavascript("window.onNativeBackupLoaded && window.onNativeBackupLoaded('" + encoded + "')", null);
            }
        } catch (Exception e) {
            Toast.makeText(this, "ব্যাকআপ প্রক্রিয়া সম্পন্ন হয়নি: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
