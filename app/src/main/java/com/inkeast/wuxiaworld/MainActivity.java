package com.inkeast.wuxiaworld;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.inkeast.wuxiaworld.ui.bookmark.BookmarkFragment;
import com.inkeast.wuxiaworld.ui.history.HistoryFragment;
import com.inkeast.wuxiaworld.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String HOME_URL = "https://inkeast.com";

    private NavController mNavController;
    private Menu mMenu;

    private Map<Integer, Integer> mNavTopMap = new HashMap<>();

    {
        mNavTopMap.put(R.id.navigation_home, R.id.action_bookmark);
        mNavTopMap.put(R.id.navigation_bookmark, R.id.action_clear_bookmarks);
        mNavTopMap.put(R.id.navigation_history, R.id.action_clear_histories);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_bookmark, R.id.navigation_history).build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, mNavController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showMenu(item.getItemId());
                return NavigationUI.onNavDestinationSelected(item, mNavController);
            }
        });
    }

    public void navigateToHome() {
        mNavController.navigate(R.id.navigation_home);
    }

    public void navigateToHome(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        mNavController.navigate(R.id.navigation_home, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        showMenu(R.id.navigation_home);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Fragment> fragments = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            switch (item.getItemId()) {
                case R.id.action_bookmark:
                    if (fragment != null && fragment instanceof HomeFragment) {
                        ((HomeFragment) fragment).actionBookmark();
                    }
                    break;
                case R.id.action_clear_bookmarks:
                    if (fragment != null && fragment instanceof BookmarkFragment) {
                        ((BookmarkFragment) fragment).removeAllBookmarks();
                    }
                    break;
                case R.id.action_clear_histories:
                    if (fragment != null && fragment instanceof HistoryFragment) {
                        ((HistoryFragment) fragment).removeAllHistories();
                    }
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMenu(int navigationId) {
        int menuItemId = mNavTopMap.get(navigationId);
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                MenuItem item = mMenu.getItem(i);
                if (item.getItemId() == menuItemId) {
                    item.setVisible(true);
                } else {
                    item.setVisible(false);
                }
            }
        }
    }
}
