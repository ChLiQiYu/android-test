package com.example.experiment03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取按钮并设置点击事件
        Button showDialogButton = findViewById(R.id.btn_show_dialog);
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
    }

    /**
     * 显示自定义登录对话框
     */
    private void showLoginDialog() {
        // 使用 AlertDialog.Builder 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 通过 setView() 方法设置自定义布局
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);

        // 创建并显示对话框
        AlertDialog dialog = builder.create();

        // 获取对话框中的控件
        EditText etUsername = dialogView.findViewById(R.id.et_username);
        EditText etPassword = dialogView.findViewById(R.id.et_password);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSignIn = dialogView.findViewById(R.id.btn_signin);

        // 设置取消按钮的点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 关闭对话框
            }
        });

        // 设置登录按钮的点击事件
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 简单验证
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                } else {
                    // 显示登录信息
                    Toast.makeText(MainActivity2.this, "用户名: " + username + ", 密码: " + password, Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // 关闭对话框
                }
            }
        });

        dialog.show();
    }
}