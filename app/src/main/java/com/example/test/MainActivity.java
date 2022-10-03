package com.example.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.test.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "Original.zip"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.test.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAdd.setOnClickListener(v -> {
            try {
                File file = new File(getApplicationContext().getExternalCacheDir(), "Encrypt.zip");
                Log.d("AAA", "Prepare encrypt file");
                InputStream inputStream = getContentResolver().openInputStream(uri);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[65536];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    Log.d("AAA", "Write to file: " + read + "bytes");
                    fileOutputStream.write(Shift.encrypt(buffer, "Modeus".getBytes(StandardCharsets.UTF_8)), 0, read);
                }
                inputStream.close();
                fileOutputStream.close();
                Log.d("AAA", "Write file successfully");
            } catch (Exception e) {
                Log.d("AAA", "Error: " + e);
            }

        });
        binding.buttonOut.setOnClickListener(v -> {
            try {
                File file = new File(getApplicationContext().getExternalCacheDir(), "Encrypt.zip");
                File fileDownload = new File(Environment.getExternalStorageDirectory(), "Download/Decrypt.zip");
                Log.d("AAA", "Prepare decrypt file");
                FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(fileDownload);
                byte[] buffer = new byte[65536];
                int read;
                while ((read = fileInputStream.read(buffer)) != -1) {
                    Log.d("AAA", "Write to file: " + read + "bytes");
                    fileOutputStream.write(Shift.decrypt(buffer, "Modeus".getBytes(StandardCharsets.UTF_8)), 0, read);
                }
                fileInputStream.close();
                fileOutputStream.close();
                Log.d("AAA", "Write file successfully");
            } catch (Exception e) {
                Log.d("AAA", "Error: " + e);
            }
        });
    }

    public static class Shift {

        private static byte[] shift(byte[] buffer, byte[] key, String type) {
            try {
                byte[] data = new byte[buffer.length];
                if (type.equals("Encrypt")) {
                    for (int i = 0; i < buffer.length; i++) {
                        int times = 8 - (key[i % key.length] % 8);
                        if (times != 8) {
                            data[i] = (byte) ((((buffer[i] < 0) ? (256 + buffer[i]) : buffer[i]) >> times) | (buffer[i] << (8 - times)));
                        } else {
                            data[i] = buffer[i];
                        }
                    }
                } else {
                    for (int i = 0; i < buffer.length; i++) {
                        int times = key[i % key.length] % 8;
                        if (times != 0) {
                            data[i] = (byte) ((((buffer[i] < 0) ? (256 + buffer[i]) : buffer[i]) >> times) | (buffer[i] << (8 - times)));
                        } else {
                            data[i] = buffer[i];
                        }
                    }
                }
                return data;
            } catch (Exception e) {
                return null;
            }
        }

        public static byte[] encrypt(byte[] buffer, byte[] key) {
            return shift(buffer, key, "Encrypt");
        }

        public static byte[] decrypt(byte[] buffer, byte[] key) {
            return shift(buffer, key, "Decrypt");
        }
    }
}