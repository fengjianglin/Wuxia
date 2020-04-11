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
import androidx.room.Room;

import com.inkeast.wuxiaworld.AppDatabase;
import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.ui.bookmark.Bookmark;
import com.inkeast.wuxiaworld.ui.history.History;

public class HomeFragment extends Fragment {

    private String url = "https://inkeast.com";

    private WebView mWebView;
    private AppDatabase mAppDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                mAppDatabase.getHistoryDao().insertHistories(history);

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

        mAppDatabase = Room.databaseBuilder(this.getContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries() //数据库中的操作一般不在主线程 这里强行在主线层中进行操作
                .build();

        return root;
    }

    public void actionBookmark() {
        Bookmark bookmark = new Bookmark();
        bookmark.title = mWebView.getTitle();
        bookmark.url = mWebView.getUrl();
        mAppDatabase.getBookmarkDao().insertBookmarks(bookmark);
        Toast.makeText(this.getContext(), "添加书签成功", Toast.LENGTH_SHORT).show();
    }

}
