package com.mul_alexautoprogramm.clientlist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.mul_alexautoprogramm.clientlist.database.AppDataBase;
import com.mul_alexautoprogramm.clientlist.database.AppExecutor;
import com.mul_alexautoprogramm.clientlist.database.Client;
import com.mul_alexautoprogramm.clientlist.database.DataAdapter;
import com.mul_alexautoprogramm.clientlist.settings.SettingsActivity;
import com.mul_alexautoprogramm.clientlist.utils.Constance;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private AppDataBase mayDataBase;
    private DataAdapter dataAdapter;
    private List<Client> listClient;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private DataAdapter.AdapterOnItemClick adapterOnItemClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapterOnItemClick = new DataAdapter.AdapterOnItemClick() {
            @Override
            public void onAdapterItemClick(int position) {

                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(Constance.NAME_KEY, listClient.get(position).getName());
                i.putExtra(Constance.SEC_NAME_KEY, listClient.get(position).getSur_name());
                i.putExtra(Constance.TEL_KEY, listClient.get(position).getTel_number());
                i.putExtra(Constance.DESC_KEY, listClient.get(position).getDescription());
                i.putExtra(Constance.IMPORTANCE_KEY, listClient.get(position).getImportance());
                i.putExtra(Constance.SPECIAL_KEY, listClient.get(position).getSpecial());
                i.putExtra(Constance.ID_KEY, listClient.get(position).getId());
                startActivity(i);

            }
        };
        NavigationView nav_view = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {

                listClient = mayDataBase.clientDAO().getClientList();

                //next way in MainIO
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(dataAdapter != null){

                            dataAdapter.updateAdapter(listClient);

                        }

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.id_search).getActionView();

        assert searchManager != null;

        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        listClient = mayDataBase.clientDAO().getClientListName(newText);

                        //next way in MainIO
                        AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                            @Override
                            public void run() {

                                if(dataAdapter != null){

                                    dataAdapter.updateAdapter(listClient);

                                }

                            }
                        });
                    }
                });

                return true;
            }
        });
        return true;
    }

    private void init(){
        recyclerView = findViewById(R.id.rcView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Vertical RCView
        mayDataBase = AppDataBase.getInstanceDB(getApplicationContext());

        listClient = new ArrayList<>();
        dataAdapter = new DataAdapter(listClient, adapterOnItemClick, this);
        recyclerView.setAdapter(dataAdapter);

      /*  AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {
                listClient = mayDataBase.clientDAO().getClientList();
                //next way in MainIO
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {

                            dataAdapter.updateAdapter(listClient);

                    }
                });
            }
        });*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.id_client){

            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = mayDataBase.clientDAO().getClientList();
                    //next way in MainIO
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            dataAdapter.updateAdapter(listClient);

                        }
                    });
                }
            });

        }else if(id == R.id.id_web){

            goTo("https://google.com");

        }else if(id == R.id.id_settings){

            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);

        }else if(id == R.id.id_special){
            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {

                    listClient = mayDataBase.clientDAO().getClientListSpecial();

                    //next way in MainIO
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            if(dataAdapter != null){

                                dataAdapter.updateAdapter(listClient);

                            }

                        }
                    });
                }
            });
        }else if(id == R.id.id_important){
        AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {

                listClient = mayDataBase.clientDAO().getClientListImportant(0);

                //next way in MainIO
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(dataAdapter != null){

                            dataAdapter.updateAdapter(listClient);

                        }

                    }
                });
            }
        });
    }else if(id == R.id.id_normal){
        AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {

                listClient = mayDataBase.clientDAO().getClientListImportant(1);

                //next way in MainIO
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(dataAdapter != null){

                            dataAdapter.updateAdapter(listClient);

                        }

                    }
                });
            }
        });
    }else if(id == R.id.id_not_important){
        AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {

                listClient = mayDataBase.clientDAO().getClientListImportant(2);

                //next way in MainIO
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(dataAdapter != null){

                            dataAdapter.updateAdapter(listClient);

                        }

                    }
                });
            }
        });
    }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goTo(String url){

        Intent brouserIntent, chooser;
        brouserIntent = new Intent(Intent.ACTION_VIEW);
        brouserIntent.setData(Uri.parse(url));

        chooser = Intent.createChooser(brouserIntent, "Open with");
        if(brouserIntent.resolveActivity(getPackageManager()) != null){

            startActivity(chooser);

        }

    }

}