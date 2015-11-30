package com.zky.zkyutils.manager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Manager<T> {
    //    private static Manager<?> instance;
//
//    @SuppressWarnings("unchecked")
//    public synchronized static <T> Manager<T> getInstance() {
//        if (instance == null)
//            instance = new Manager<T>();
//
//        return (Manager<T>) instance;
//    }
    public List<WeakReference<T>> callBack = new ArrayList<>();

    public void registerMessageListener(T listener) {
        T item;
        for (WeakReference<T> weakReference : callBack) {
            item = weakReference.get();
            if (item == listener) {
                return;
            }
        }
        WeakReference<T> wf = new WeakReference<T>(listener);
        callBack.add(wf);
    }

    public void unRegisterMessageListener(T listener) {
        T item;
        for (int i = 0; i < callBack.size(); i++) {
            item = callBack.get(i).get();
            if (item == listener) {
                callBack.remove(i);
                return;
            }
        }
    }
}
