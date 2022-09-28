package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Click, Complete {

    private Service service;
    private MainAdapter mainAdapter;

    List<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.test.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        strings = new ArrayList<>();

        mainAdapter = new MainAdapter(strings);
        mainAdapter.setClick(this);
        service = new Service();
        service.setComplete(this);

        binding.recyclerviewListFile.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewListFile.setAdapter(mainAdapter);

        service.getAddItem(strings, "");

        binding.buttonAdd.setOnClickListener(v -> {
            Dialog dialogCreate = new Dialog(MainActivity.this);
            dialogCreate.setCancelable(true);
            dialogCreate.setContentView(R.layout.dialog_create_folder);
            Button buttonCancel = dialogCreate.findViewById(R.id.button_cancel);
            Button buttonOk = dialogCreate.findViewById(R.id.button_ok);
            EditText editName = dialogCreate.findViewById(R.id.edittext_name);

            buttonOk.setOnClickListener(v1 -> {
                String name = editName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter folder name", Toast.LENGTH_SHORT).show();
                } else if (name.contains("/") || name.contains("\\")) {
                    Toast.makeText(MainActivity.this, "Folder name can't contain '/' and '\\' characters", Toast.LENGTH_SHORT).show();
                } else {
                    service.createFolder(name, "");
                    dialogCreate.cancel();
                }
            });
            buttonCancel.setOnClickListener(v1 -> dialogCreate.cancel());
            dialogCreate.show();
        });
        binding.buttonOut.setOnClickListener(v -> {
            Collections.sort(strings, String::compareTo);
            for (String s : strings) {
                Log.d("AAA", "Sort: " + s);
            }
            int a = strings.indexOf("/F");
            Log.d("AAA", a + "");
        });
    }

    @Override
    public void event(List<String> strings, String item, int position) {
        strings.remove(position);
        mainAdapter.notifyItemRemoved(position);
        mainAdapter.notifyItemRangeChanged(position, strings.size());
    }

    @Override
    public void transfer(List<String> strings, String item) {
        strings.clear();
        service.getAddItem(strings, item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void list(int size) {
        mainAdapter.notifyDataSetChanged();
        mainAdapter.notifyItemRangeChanged(0, size);
    }

    @Override
    public void add(String name, int mode, int size) {
        if (mode == 1) {
            Toast.makeText(this, "Create successfully", Toast.LENGTH_SHORT).show();

            strings.add("/" + name);
            Collections.sort(strings, String::compareTo);
            for (String s : strings) {
                Log.d("AAA", "Sort: " + s);
            }
            int a = strings.indexOf("/" + name);
            Log.d("AAA", a + "");

            mainAdapter.notifyItemInserted(a);
            mainAdapter.notifyItemRangeChanged(a, strings.size());
        } else {
            Toast.makeText(this, "Create failed", Toast.LENGTH_SHORT).show();
        }
    }
}