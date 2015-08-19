package com.zhongkeyun.daoGenerate;

import java.io.Serializable;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/*
   Green Dao 生成参考
 */
public class MyClass {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "green.dao.htj");// 数据库版本，生成的类的命名空间

        schema.enableKeepSectionsByDefault();//保留生存类中自己的代码
        schema.enableActiveEntitiesByDefault();

        addDeliveryOrder(schema);
        addStore(schema);
        new DaoGenerator().generateAll(schema, "..\\zkyUtilsDemo\\demo\\src-dao"); // 生成的类文件对应的位置
    }

    private static void addDeliveryOrder(Schema schema) {
        Entity glucoseRecord = schema.addEntity("DeliveryOrder");

        glucoseRecord.addStringProperty("order_id").primaryKey();
        glucoseRecord.addStringProperty("customer_name");
        glucoseRecord.addStringProperty("customer_mobile");
        glucoseRecord.addIntProperty("customer_sex").notNull();
        glucoseRecord.addStringProperty("customer_address").notNull();
        glucoseRecord.addDoubleProperty("customer_lng").notNull();
        glucoseRecord.addDoubleProperty("customer_lat").notNull();
        glucoseRecord.addIntProperty("status");
        glucoseRecord.addIntProperty("reminderNumber");
        glucoseRecord.addStringProperty("deliverPhone").notNull();
        glucoseRecord.addLongProperty("lastReminderTime");//上次催单时间

        glucoseRecord.addLongProperty("addTime");//添加记录的时间

        glucoseRecord.addStringProperty("queryBatch");//查询的批次

        glucoseRecord.implementsInterface(Serializable.class.getName());
    }

    private static void addStore(Schema schema) {
        Entity glucoseRecord = schema.addEntity("StoreInfo");

        glucoseRecord.addStringProperty("phone").primaryKey();
        glucoseRecord.addLongProperty("id");
        glucoseRecord.addStringProperty("name");
        glucoseRecord.addDoubleProperty("lng");
        glucoseRecord.addDoubleProperty("lat").notNull();
        glucoseRecord.addStringProperty("code").notNull();

        glucoseRecord.implementsInterface(Serializable.class.getName());
    }
}
