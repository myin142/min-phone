package myin.phone.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myin.phone.R;
import myin.phone.apps.AppItem;
import myin.phone.list.ListItemAdapter;
import myin.phone.settings.EditApps;
import myin.phone.shared.LoadAppsActivity;

public class HomeActivity extends LoadAppsActivity {

    public static final int REQ_APPS_CHANGED = 1;

    private ListItemAdapter<AppItem> appAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        appAdapter = new ListItemAdapter<>(appList);

        RecyclerView appsView = findViewById(R.id.apps_list);
        appsView.setHasFixedSize(true);
        appsView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        appsView.setAdapter(appAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_APPS_CHANGED && resultCode == RESULT_OK &&
                data != null && data.getBooleanExtra(EditApps.APPS_CHANGED, false)) {

            reloadApps();
            appAdapter.notifyDataSetChanged();
        }
    }
}