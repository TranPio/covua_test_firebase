package com.example.covua_test_firebase;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Get_Data extends AppCompatActivity {
    private Button btnGet;
    private TextView textviewGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_data);

        // Xử lý giao diện tránh che bởi status bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ẩn status bar và navigation bar nếu Android >= R
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                controller.setSystemBarsBehavior(
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                );
            }
        }

        btnGet = findViewById(R.id.btn_get);
        textviewGet = findViewById(R.id.textview_get);

        // Đọc dữ liệu khi nhấn nút "Get"
        btnGet.setOnClickListener(v -> readFromDatabase());
    }
    private void readFromDatabase() {
        // Kết nối tới Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Tham chiếu tới node "Text"
        DatabaseReference myRef = database.getReference("User");

        // Đọc dữ liệu một lần (single event)
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Lấy dữ liệu từ snapshot (dạng chuỗi)
                String value = snapshot.getValue(String.class);

                textviewGet.setText(value); // Hiển thị giá trị trong TextView
                Toast.makeText(Get_Data.this,
                        "Đọc dữ liệu thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Get_Data.this,
                        "Đọc dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}