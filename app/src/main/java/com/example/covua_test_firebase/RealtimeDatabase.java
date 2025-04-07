package com.example.covua_test_firebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        // Tạo đường dẫn tham chiếu đến node "Text" trong Firebase
        DatabaseReference myRef = database.getReference("Text");

        // Ghi dữ liệu lên Firebase tại node "Text"
        myRef.setValue(value)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Ghi dữ liệu thành công",
                                Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi khi ghi dữ liệu",
                                Toast.LENGTH_SHORT).show());
    }

    // Hàm đọc dữ liệu từ Firebase tại node "Text"
    private void readFromDatabase() {
        // Lấy instance của Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Tham chiếu đến node "Text"
        DatabaseReference myRef = database.getReference("Text");

        // Đọc dữ liệu một lần từ node "Text"
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            // Khi dữ liệu thay đổi (hoặc đọc thành công)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Lấy giá trị dạng chuỗi từ snapshot
                String value = snapshot.getValue(String.class);

                // Hiển thị giá trị bằng Toast
                Toast.makeText(RealtimeDatabase.this, "Giá trị: " + value,
                        Toast.LENGTH_SHORT).show();
            }

            // Nếu có lỗi xảy ra khi đọc
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Hiển thị thông báo lỗi
                Toast.makeText(RealtimeDatabase.this, "Đọc thất bại!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}