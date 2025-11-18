package com.example.experiment02;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主活动类，实现动物列表展示、点击事件处理和通知发送功能
 */
public class MainActivity extends AppCompatActivity {

    private List<Map<String, Object>> dataList;
    private final String[] from = {"name", "image"};
    private final int[] to = {R.id.textViewName, R.id.imageViewIcon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据
        initData();

        // 创建SimpleAdapter适配器
        SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.list_item, from, to);
        
        // 获取ListView并设置适配器
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // 设置列表项点击事件监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取选中的项目名称
                String selectedItem = (String) dataList.get(position).get("name");

                // 保存原始背景
                Drawable originalBackground = view.getBackground();
                
                // 改变被点击项的背景色为红色
                view.setBackgroundColor(Color.RED);
                
                // 延迟恢复原始背景色
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackground(originalBackground);
                    }
                }, 1000); // 200毫秒后恢复原始背景色

                // 显示Toast消息
                Toast.makeText(MainActivity.this, "选中: " + selectedItem, Toast.LENGTH_SHORT).show();

                // 发送通知
                sendNotification(selectedItem);
            }
        });
    }

    /**
     * 初始化列表数据
     */
    private void initData() {
        dataList = new ArrayList<>();

        // 动物名称数组
        String[] names = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
        
        // 对应的动物图片资源数组
        int[] images = {
                R.drawable.lion,
                R.drawable.tiger,
                R.drawable.monkey,
                R.drawable.dog,
                R.drawable.cat,
                R.drawable.elephant
        };

        // 将数据添加到列表中
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("image", images[i]);
            dataList.add(map);
        }
    }

    /**
     * 发送通知
     * @param title 通知标题
     */
    private void sendNotification(String title) {
        // 获取通知管理器
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 创建通知渠道（Android 8.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "animal_channel",
                    "Animal Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("动物通知渠道");
            notificationManager.createNotificationChannel(channel);
        }

        // 构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "animal_channel")
                .setSmallIcon(R.mipmap.ic_launcher)  // 使用应用图标作为通知图标
                .setContentTitle(title)               // 设置通知标题为选中的动物名称
                .setContentText("您选中了 " + title + "，这是来自应用的通知。")  // 设置通知内容
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // 设置通知优先级
                .setAutoCancel(true);                 // 点击后自动取消通知

        // 发送通知
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}