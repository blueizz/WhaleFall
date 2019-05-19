package com.blueizz.collection;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        /**
         * 遍历过程中不可以操作List
         */
        //        for (int value : list) {
        //            if (value % 2 != 0) {
        //                list.remove(value);
        //            }
        //        }

        /**
         * 遍历过程中删除元素的正确姿势
         */
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() % 2 != 0) {
                iterator.remove();
            }
        }

        Toast.makeText(this, "偶数有" + list.size() + "个", Toast.LENGTH_SHORT).show();
    }
}
