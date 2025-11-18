package com.example.experiment02;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        
        // 初始化TextView
        textView = findViewById(R.id.textView);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * 创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 处理菜单项点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_font_small:
                // 设置字体为小号(10号)
                textView.setTextSize(10);
                return true;
            case R.id.menu_font_medium:
                // 设置字体为中号(16号)
                textView.setTextSize(16);
                return true;
            case R.id.menu_font_large:
                // 设置字体为大号(20号)
                textView.setTextSize(20);
                return true;
            case R.id.menu_about:
                // 显示关于Toast提示
                Toast.makeText(this, "这是关于信息", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_color_red:
                // 设置字体为红色
                textView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                return true;
            case R.id.menu_color_black:
                // 设置字体为黑色
                textView.setTextColor(getResources().getColor(android.R.color.black));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}