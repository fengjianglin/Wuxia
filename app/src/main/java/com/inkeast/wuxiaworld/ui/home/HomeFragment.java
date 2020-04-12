package com.inkeast.wuxiaworld.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.inkeast.wuxiaworld.MainActivity;
import com.inkeast.wuxiaworld.MainApplication;
import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.database.Bookmark;
import com.inkeast.wuxiaworld.database.History;

public class HomeFragment extends Fragment {

    private WebView mWebView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String url = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
        }
        if (url == null) {
            url = MainActivity.HOME_URL;
        }

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mWebView = root.findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                History history = new History();
                history.url = view.getUrl();
                MainApplication.getDatabase().getHistoryDao().insertHistories(history);

                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        mWebView.loadUrl(url);

        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return view.onKeyDown(keyCode, keyEvent);
            }
        });
        return root;
    }

    public void actionBookmark() {
        Bookmark bookmark = new Bookmark();
        bookmark.title = mWebView.getTitle();
        bookmark.url = mWebView.getUrl();
        MainApplication.getDatabase().getBookmarkDao().insertBookmarks(bookmark);
        Toast.makeText(this.getContext(), "添加书签成功", Toast.LENGTH_SHORT).show();
    }
}
