package com.example.lab4;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Date;

public class InventoryActivity extends AppCompatActivity {
    Inventory inventory;

    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventory = new Inventory();

        if (getIntent().getIntExtra("index", -1) != -1) {
            Intent intent = getIntent();
            inventory.setmTitle(intent.getStringExtra("title"));
            inventory.setmDate((Date) intent.getSerializableExtra("date"));
            inventory.setmSolved(intent.getBooleanExtra("status",false));
            index = intent.getIntExtra("index", -1);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        InventoryFragment fragment = (InventoryFragment) fragmentManager.findFragmentById(R.id.inventory_fragment_container);
        if(fragment == null) {
            fragment = new InventoryFragment();
            fragment.setmInventory(inventory);
            fragmentManager.beginTransaction().add(R.id.inventory_fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_save) {
            InventoryFragment fragment = (InventoryFragment) getSupportFragmentManager().findFragmentById(R.id.inventory_fragment_container);
            inventory = fragment.getmInventory();

            Intent intent = new Intent();
            intent.putExtra("title", inventory.getmTitle());
            intent.putExtra("id", inventory.getmId());
            intent.putExtra("date", inventory.getmDate());
            intent.putExtra("status", inventory.ismSolved());
            intent.putExtra("index", index);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}