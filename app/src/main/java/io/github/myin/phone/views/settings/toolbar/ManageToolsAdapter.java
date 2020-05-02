package io.github.myin.phone.views.settings.toolbar;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.Setter;
import io.github.myin.phone.R;
import io.github.myin.phone.data.BaseAppDiffCallback;
import io.github.myin.phone.data.BaseAppListAdapter;
import io.github.myin.phone.data.tool.HomeTool;

@Setter
public class ManageToolsAdapter extends BaseAppListAdapter<HomeTool, ManageToolsAdapter.ManageToolView> {

    private PackageManager packageManager;

    public ManageToolsAdapter(PackageManager pm) {
        super(new BaseAppDiffCallback<>());
        this.packageManager = pm;
    }

    @NonNull
    @Override
    public ManageToolView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_tool_item, parent, false);

        return new ManageToolView(view, view.findViewById(R.id.icon));
    }

    @Override
    public void onBindViewHolder(@NonNull ManageToolView holder, int position) {
        HomeTool app = getItem(position);

        ResolveInfo info = packageManager.resolveActivity(app.getActivityIntent(), PackageManager.MATCH_DEFAULT_ONLY);
        Drawable icon = info.loadIcon(packageManager);
        holder.setImage(icon);

        holder.setOnImageClick(v -> {
            if (onItemClick != null) {
                onItemClick.accept(app);
            }
        });
    }

    public static class ManageToolView extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ManageToolView(View view, ImageView imageView) {
            super(view);
            this.imageView = imageView;
        }

        public void setOnImageClick(View.OnClickListener clickListener) {
            this.imageView.setOnClickListener(clickListener);
        }

        public void setImage(Drawable drawable) {
            this.imageView.setImageDrawable(drawable);
        }
    }
}