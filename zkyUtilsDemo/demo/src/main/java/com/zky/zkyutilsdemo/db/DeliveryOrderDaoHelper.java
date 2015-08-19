package com.zky.zkyutilsdemo.db;


import com.zky.zkyutilsdemo.MyApplication;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import green.dao.htj.DeliveryOrder;
import green.dao.htj.DeliveryOrderDao;

public class DeliveryOrderDaoHelper {
    private static DeliveryOrderDao getGlucoseRecordDao() {
        return MyApplication.instance.getDaoSession().getDeliveryOrderDao();
    }

    public synchronized static boolean insertOrUpdate(DeliveryOrder record, String queryBatch) {
        DeliveryOrder deliveryOrder = getGlucoseRecordDao().queryBuilder()
                .where(DeliveryOrderDao.Properties.Order_id.eq(record.getOrder_id())).
                        build().forCurrentThread().unique();
        if (deliveryOrder == null)
            return getGlucoseRecordDao().insert(record) > 0;
        else {
            deliveryOrder.setQueryBatch(queryBatch);
            getGlucoseRecordDao().update(deliveryOrder);
            return true;
        }
    }

    public synchronized static void update(DeliveryOrder record) {
        getGlucoseRecordDao().update(record);
    }

    public synchronized static void addReminderNumber(String orderId, long time) {
        DeliveryOrder deliveryOrder = getGlucoseRecordDao().queryBuilder()
                .where(DeliveryOrderDao.Properties.Order_id.eq(orderId)).
                        build().forCurrentThread().unique();

        if (deliveryOrder != null && (deliveryOrder.getLastReminderTime() == null || deliveryOrder.getLastReminderTime() != time)) {
            deliveryOrder.setReminderNumber(deliveryOrder.getReminderNumber() + 1);
            deliveryOrder.setLastReminderTime(time);
            getGlucoseRecordDao().update(deliveryOrder);
        }
    }

    public synchronized static void deleteAllByDeliverPhone(String deliverPhone) {
        getGlucoseRecordDao().queryBuilder()
                .where(DeliveryOrderDao.Properties.DeliverPhone.eq(deliverPhone)).
                buildDelete().forCurrentThread().executeDeleteWithoutDetachingEntities();
    }

    public synchronized static void deleteByOrderId(String orderId) {
        getGlucoseRecordDao().queryBuilder()
                .where(DeliveryOrderDao.Properties.Order_id.eq(orderId)).
                buildDelete().forCurrentThread().executeDeleteWithoutDetachingEntities();
    }


    public synchronized static void deleteOrderNotInBatch(String phone, String queryBatch) {
        QueryBuilder<DeliveryOrder> qb = getGlucoseRecordDao().queryBuilder();
        qb.where(DeliveryOrderDao.Properties.QueryBatch.notEq(queryBatch),
                DeliveryOrderDao.Properties.DeliverPhone.eq(phone));
        qb.buildDelete().forCurrentThread().executeDeleteWithoutDetachingEntities();
    }

    public synchronized static List<DeliveryOrder> queryAllOrdersByDeliverPhone(String phone) {
        return getGlucoseRecordDao().queryBuilder()
                .where(DeliveryOrderDao.Properties.DeliverPhone.eq(phone)).
                        build().forCurrentThread().list();
    }
}
