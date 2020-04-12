package com.inkeast.wuxiaworld.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Menu mMenu;

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

        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return view.onKeyDown(keyCode, keyEvent);
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_home_menu, menu);
        mMenu = menu;
        measureBookmarkIcon(mWebView.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_bookmark == item.getItemId()) {
            actionBookmark();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        History history = new History();
        history.url = request.getUrl().toString();
        MainApplication.getDatabase().getHistoryDao().insertHistories(history);
        measureBookmarkIcon(history.url);
    }

    private void measureBookmarkIcon(String url) {
        List<Bookmark> bookmarks = MainApplication.getDatabase().getBookmarkDao().loadBookmarksByUrl(url);
        if (bookmarks != null && bookmarks.size() > 0) {  // 存在书签
            setMenuIcon(R.id.action_bookmark, R.drawable.ic_bookmark_black_24dp);
        } else { // 不存在书签
            setMenuIcon(R.id.action_bookmark, R.drawable.ic_bookmark_border_black_24dp);
        }
    }

    private void actionBookmark() {
        String url = mWebView.getUrl();
        List<Bookmark> bookmarks = MainApplication.getDatabase().getBookmarkDao().loadBookmarksByUrl(url);
        if (bookmarks != null && bookmarks.size() > 0) { // 删除书签
            MainApplication.getDatabase().getBookmarkDao().deleteBookmarks(bookmarks.toArray(new Bookmark[bookmarks.size()]));
            Toast.makeText(this.getContext(), "删除书签成功", Toast.LENGTH_SHORT).show();
            measureBookmarkIcon(url);
        } else { // 添加书签
            Bookmark bookmark = new Bookmark();
            bookmark.title = mWebView.getTitle();
            bookmark.url = url;
            MainApplication.getDatabase().getBookmarkDao().insertBookmarks(bookmark);
            Toast.makeText(this.getContext(), "添加书签成功", Toast.LENGTH_SHORT).show();
            measureBookmarkIcon(url);
        }
    }

    private void setMenuIcon(int menuItemId, int iconId) {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                MenuItem item = mMenu.getItem(i);
                if (item.getItemId() == menuItemId) {
                    item.setIcon(iconId);
                }
            }
        }
    }
}
