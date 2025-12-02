package com.example.experiment03;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    private ListView listView;
    private CustomArrayAdapter adapter;
    private ArrayList<String> dataList;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化数据
        initData();

        // 初始化视图
        initViews();

        // 设置监听器
        setListeners();
    }

    /**
     * 初始化数据列表
     */
    private void initData() {
        dataList = new ArrayList<>();
        dataList.add("One");
        dataList.add("Two");
        dataList.add("Three");
        dataList.add("Four");
        dataList.add("Five");
    }

    /**
     * 初始化视图组件
     */
    private void initViews() {
        listView = findViewById(R.id.list_view);
        adapter = new CustomArrayAdapter(this, R.layout.list_item_for_four, dataList);
        listView.setAdapter(adapter);
        // 初始状态不启用多选模式
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        // 将ListView引用传递给适配器
        adapter.setListView(listView);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        // 设置长按监听器，用于启动ActionMode
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }

                // 启动 ActionMode
                mActionMode = startActionMode(new ActionModeCallback());

                // 设置选中状态
                listView.setItemChecked(position, true);
                adapter.notifyDataSetChanged(); // 通知适配器更新UI
                updateActionModeTitle();

                return true;
            }
        });

        // 设置点击监听器，用于处理ActionMode状态下的项目选择
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 如果ActionMode已经启动，则处理多项选择
                if (mActionMode != null) {
                    toggleSelection(position);
                } else {
                    // ActionMode未激活时的正常点击处理
                    Toast.makeText(MainActivity4.this, "点击了: " + dataList.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 切换指定位置项目的选中状态
     *
     * @param position 项目位置
     */
    private void toggleSelection(int position) {
        // 切换选中状态
        boolean currentlyChecked = listView.isItemChecked(position);
        listView.setItemChecked(position, !currentlyChecked);
        
        // 通知适配器更新UI
        adapter.notifyDataSetChanged();
        
        // 更新ActionMode标题
        updateActionModeTitle();
        
        // 注意：不再在没有选中项目时自动关闭ActionMode
        // 用户需要通过操作菜单中的"取消"或"删除"按钮手动退出ActionMode
    }

    /**
     * 更新ActionMode标题，显示选中项数量
     */
    private void updateActionModeTitle() {
        if (mActionMode != null) {
            int selectedCount = getSelectedCount();
            mActionMode.setTitle("✓ " + selectedCount + " selected");
        }
    }

    /**
     * 获取当前选中的项目数量
     *
     * @return 选中项数量
     */
    private int getSelectedCount() {
        int count = 0;
        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 删除所有选中的项目
     */
    private void deleteSelectedItems() {
        // 从后往前遍历，避免删除过程中索引变化的问题
        for (int i = dataList.size() - 1; i >= 0; i--) {
            if (listView.isItemChecked(i)) {
                dataList.remove(i);
            }
        }
        // 通知适配器数据已更改
        adapter.notifyDataSetChanged();
    }

    /**
     * 重置所有选择状态
     */
    private void resetSelections() {
        for (int i = 0; i < listView.getCount(); i++) {
            listView.setItemChecked(i, false);
        }
        adapter.notifyDataSetChanged(); // 通知适配器更新UI
    }

    /**
     * ActionMode回调类，处理ActionMode的各种事件
     */
    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // 加载上下文菜单
            getMenuInflater().inflate(R.menu.context_menu, menu);
            // 启用多选模式
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            // 设置ActionMode标题
            updateActionModeTitle();
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // 更新选中数量
            updateActionModeTitle();
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                deleteSelectedItems();
                mode.finish(); // 关闭 ActionMode
                return true;
            } else if (item.getItemId() == R.id.action_cancel) {
                mode.finish(); // 关闭 ActionMode
                return true;
            }
            // 对于未知的菜单项，应该返回false
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            resetSelections();
            // 退出ActionMode时禁用多选模式
            listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        }
    }
}