package com.example.newspaper;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnv;

    //Now I am inflating menu inside AllNewsFragment
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.category_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        String category = null;
//        int id = item.getItemId();
//        if(id == R.id.business){
//            category = "business";
//        }else if(id == R.id.politics){
//            category = "politics";
//        }else if(id == R.id.technology){
//            category = "technology";
//        }else if(id == R.id.sports){
//            category = "sports";
//        }
//        if(category != null){   //if category is null, chup chap general se assign kr do in AllNewsFragment
//            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);  //read knowledge 8
//            if(currentFragment instanceof AllNewsFragment){
//                ((AllNewsFragment) currentFragment).getNewsByCategory(category);
//            }
//            return true;
//        }
//        return super.onOptionsItemSelected(item);   //a safety measure. calls AppCompatActivity with menu that has default behaviour defined if nothing of if else matched.
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        loadFragment(new AllNewsFragment());    //by default this frag will be loaded
        bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {  //for loading fragments on selecting bnv items.
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.allNews){
                    loadFragment(new AllNewsFragment());
                }else if(id == R.id.saved){
                    loadFragment(new SavedFragment());
                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
}