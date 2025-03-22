package com.example.stargallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stargallery.adapter.StarAdapter;
import com.example.stargallery.beans.Star;
import com.example.stargallery.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Vérifiez ces lignes pour vous assurer qu'elles ne causent pas d'erreur
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();  // Le problème pourrait être ici, dans les ressources référencées

        recyclerView = findViewById(R.id.recycle_view);
        // Assurez-vous que recyclerView n'est pas null
        if (recyclerView != null) {
            starAdapter = new StarAdapter(this, service.findAll());
            recyclerView.setAdapter(starAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void init() {
        // Utilisation des images locales du dossier drawable

        service.create(new Star("nadal raphael", "android.resource://" + getPackageName() + "/" + R.drawable.nadal_raphael, 4.3f));
        service.create(new Star("djokovic", "android.resource://" + getPackageName() + "/" + R.drawable.djokovic, 4.3f));
        service.create(new Star("messi", "android.resource://" + getPackageName() + "/" + R.drawable.messi, 4.3f));
        service.create(new Star("ranaldo", "android.resource://" + getPackageName() + "/" + R.drawable.renaldo, 4.3f));






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Configuration de la recherche
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Nom de star...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            // Créer un intent de partage avec un message descriptif
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Stars");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Découvrez ma galerie de stars de cinéma ! Une application géniale pour noter vos acteurs préférés.");

            // Ouvrir le sélecteur de partage (WhatsApp, Bluetooth, etc.)
            startActivity(Intent.createChooser(shareIntent, "Partager via"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}