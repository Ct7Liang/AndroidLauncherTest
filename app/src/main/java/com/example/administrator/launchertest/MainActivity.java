package com.example.administrator.launchertest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<ResolveInfo> resolveInfos;
    private PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveInfos = packageManager.queryIntentActivities(i, 0);

        listView.setAdapter(new AppAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo resolveInfo = resolveInfos.get(position);
                String packageName = resolveInfo.activityInfo.packageName;
                String name = resolveInfo.activityInfo.name;
                ComponentName componentName = new ComponentName(packageName, name);
                Intent i = new Intent();
                i.setComponent(componentName);
                startActivity(i);

                Log.e("ct7liang123", packageName + "  -  " + name);
            }
        });

    }


    private class AppAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return resolveInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(MainActivity.this, R.layout.item_app, null);
            }else{
                view = convertView;
            }
            ResolveInfo resolveInfo = resolveInfos.get(position);
            ((ImageView)view.findViewById(R.id.logo)).setImageDrawable(resolveInfo.activityInfo.loadIcon(packageManager));
            ((TextView)view.findViewById(R.id.label)).setText(resolveInfo.loadLabel(packageManager));
            return view;
        }
    }
}
