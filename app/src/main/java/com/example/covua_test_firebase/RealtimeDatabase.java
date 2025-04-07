package com.example.covua_test_firebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabase extends AppCompatActivity {
    private EditText edtData;
    private Button btnPush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_realtime_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    // Lấy tham chiếu đến ô nhập dữ liệu (EditText) để người dùng nhập nội dung
        EditText edtData = findViewById(R.id.edt_data);

    // Lấy tham chiếu đến nút "Push" (Button) để xử lý sự kiện khi nhấn nút
        Button btnPush = findViewById(R.id.btn_push);

    // Gán sự kiện click cho nút "Push" - khi bấm nút thì gọi hàm xử lý ghi dữ liệu
        btnPush.setOnClickListener(v -> onClickPushData());

    }
    private void onClickPushData() {
        // Lấy chuỗi dữ liệu người dùng vừa nhập từ EditText
        String value = edtData.getText().toString();

        // Lấy instance của Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Tạo đường dẫn tham chiếu đến node "data" trong Firebase
        DatabaseReference myRef = database.getReference("data");

        // Ghi dữ liệu lên Firebase tại node "data"
        myRef.setValue(value)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Ghi dữ liệu thành công",
                                Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi khi ghi dữ liệu",
                                Toast.LENGTH_SHORT).show());
    }

}