package myin.phone.list;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NoScrollLinearLayout extends LinearLayoutManager {
    public NoScrollLinearLayout(Context context) {
        this(context, LinearLayoutManager.HORIZONTAL);
    }

    public NoScrollLinearLayout(Context context, int orientation) {
        super(context, orientation, false);
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
