package com.inkeast.wuxiaworld.ui.home;

import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

public class HomeFragment extends Fragment {

    private WebView mWebView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String url = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
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
                HomeFragment.this.shouldOverrideUrlLoading(view, request);
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        mWebView.loadUrl(url);
        measureBookmarks(url);

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

    private void shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.e("url", request.getUrl().toString());
        History history = new History();
        history.url = request.getUrl().toString();
        MainApplication.getDatabase().getHistoryDao().insertHistories(history);
        measureBookmarks(history.url);
    }

    private void measureBookmarks(String url) {
        List<Bookmark> bookmarks = MainApplication.getDatabase().getBookmarkDao().loadBookmarksByUrl(url);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (bookmarks != null && bookmarks.size() > 0) {  // 存在书签
            mainActivity.setMenuIcon(R.id.action_bookmark, R.drawable.ic_bookmark_black_24dp);
        } else { //不存在书签
            mainActivity.setMenuIcon(R.id.action_bookmark, R.drawable.ic_bookmark_border_black_24dp);
        }
    }

    public void actionBookmark() {
        Bookmark bookmark = new Bookmark();
        bookmark.title = mWebView.getTitle();
        bookmark.url = mWebView.getUrl();
        MainApplication.getDatabase().getBookmarkDao().insertBookmarks(bookmark);
        Toast.makeText(this.getContext(), "添加书签成功", Toast.LENGTH_SHORT).show();
    }
}
