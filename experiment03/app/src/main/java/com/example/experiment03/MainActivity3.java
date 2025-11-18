package com.example.experiment03;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
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
        
        // 获取布局中的TextView
        textView = findViewById(R.id.textView);
        
        // 获取按钮并设置点击事件
        android.widget.Button btnOpenMenu = findViewById(R.id.btn_open_menu);
        // 替换原来的按钮点击事件（删掉原来的openOptionsMenu()）
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 创建PopupMenu（关键！）
                PopupMenu popup = new PopupMenu(MainActivity3.this, v);

                // 2. 加载你的XML菜单（和原来一样）
                popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

                // 3. 处理菜单项点击（复制你原来的逻辑）
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item); // 直接复用你已有的处理逻辑！
                    }
                });

                // 4. 显示菜单（和系统菜单一样丝滑）
                popup.show();
            }
        });
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单资源文件
        getMenuInflater().inflate(R.menu.main_menu, menu);
        
        // 确保菜单图标始终显示
        try {
            java.lang.reflect.Method m = menu.getClass().getDeclaredMethod(
                "setOptionalIconsVisible", java.lang.Boolean.TYPE);
            m.setAccessible(true);
            m.invoke(menu, true);
        } catch (Exception e) {
            // 如果反射调用失败，则忽略
        }
        
        return true;
    }
    
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_font_small) {
            if (textView != null) {
                textView.setTextSize(10);
            }
            Toast.makeText(this, "已设置为小号字体", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_font_medium) {
            if (textView != null) {
                textView.setTextSize(16);
            }
            Toast.makeText(this, "已设置为中号字体", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_font_large) {
            if (textView != null) {
                textView.setTextSize(20);
            }
            Toast.makeText(this, "已设置为大号字体", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_normal_option) {
            Toast.makeText(this, "点击了普通选项", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_color_red) {
            if (textView != null) {
                textView.setTextColor(android.graphics.Color.RED);
            }
            Toast.makeText(this, "已设置为红色", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_color_black) {
            if (textView != null) {
                textView.setTextColor(android.graphics.Color.BLACK);
            }
            Toast.makeText(this, "已设置为黑色", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}