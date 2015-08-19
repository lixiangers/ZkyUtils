package com.zky.zkyutilsdemo.db;


import com.zky.zkyutilsdemo.MyApplication;

import green.dao.htj.StoreInfo;
import green.dao.htj.StoreInfoDao;

public class StoreDaoHelper {
    private static StoreInfoDao getStoreInfoDao() {
        return MyApplication.instance.getDaoSession().getStoreInfoDao();
    }

    public synchronized static boolean insert(StoreInfo record) {
        return getStoreInfoDao().insert(record) > 0;
    }

    public synchronized static void update(StoreInfo record) {
        getStoreInfoDao().update(record);
    }

    public synchronized static void delete(String phone) {
        getStoreInfoDao().queryBuilder()
                .where(StoreInfoDao.Properties.Phone.eq(phone)).
                buildDelete().forCurrentThread().executeDeleteWithoutDetachingEntities();
    }

    public synchronized static StoreInfo queryStoreInfoByDeliverPhone(String phone) {
        return getStoreInfoDao().queryBuilder()
                .where(StoreInfoDao.Properties.Phone.eq(phone)).
                        build().forCurrentThread().unique();
    }
}
