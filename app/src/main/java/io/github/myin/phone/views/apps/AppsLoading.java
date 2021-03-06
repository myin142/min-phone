package io.github.myin.phone.views.apps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AppsLoading extends Observable<List<ResolveInfo>> {
    private final Context context;

    private final Subject<String> reload = BehaviorSubject.create();

    private Runnable onLoadStart = () -> {};
    private Runnable onLoadFinish = () -> {};

    @Override
    protected void subscribeActual(Observer<? super List<ResolveInfo>> observer) {
        Disposable listDisposable = reload
                .map(x -> {
                    onLoadStart.run();
                    return getAllApps();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    observer.onNext(list);
                    onLoadFinish.run();
                });

        observer.onSubscribe(listDisposable);
    }

    public void setOnLoadFinish(Runnable onLoadFinish) {
        this.onLoadFinish = onLoadFinish;
    }

    public void setOnLoadStart(Runnable onLoadStart) {
        this.onLoadStart = onLoadStart;
    }

    public void reload() {
        reload.onNext("");
    }

    private List<ResolveInfo> getAllApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(getPackageManager()));

        return apps;
    }

    private PackageManager getPackageManager() {
        return context.getPackageManager();
    }

}
