package com.example.demo.database;

import org.apache.iotdb.session.Session;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.apache.iotdb.tsfile.write.record.Tablet;
import org.apache.iotdb.tsfile.write.schema.MeasurementSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Measurement  物理量    实际场景中检测装置所记录的测量信息
 * Entity       实体      实际场景中拥有物理量的设备或者装置
 * Database     数据库    一个 database 中的所有数据会存储在同一批文件夹下
 * Timeseries   时间序列  一个物理实体的某个物理量在时间轴上的记录
 *
 *
 *
 */
public class ApacheIotDB {

    public static void main(String[] args) throws Exception {
        Session session = new Session.Builder().
                host("10.40.152.182").
                port(6667).build();

        session.open();

        try {

            // 设置设备名字，设备下面的传感器名字，各个传感器的类型
            List<MeasurementSchema> schemaList = new ArrayList<>();
            schemaList.add(new MeasurementSchema("status", TSDataType.INT32));



            Tablet tablet = new Tablet("root.test.yuupl", schemaList);
            // 以当前时间戳作为插入的起始时间戳
            long timestamp = System.currentTimeMillis();

            for (long row = 0; row < 10000000; row++) {
                int rowIndex = tablet.rowSize++;
                tablet.addTimestamp(rowIndex, timestamp + row * 10 * 1000);
                // 随机生成数据
                int value = 0;
                if (row > 800 && row < 1000) {
                    value = 1;
                }
                if (row > 300 && row < 500) {
                    value = 1;
                }
                tablet.addValue("status", rowIndex, value);

                session.insertTablet(tablet);
                tablet.reset();
                System.out.println("已经插入了：" + (row + 1) + "行数据");

                timestamp++;
            }

//            SessionDataSet result = session.executeQueryStatement("select * from root.test.yuupl;");
//            System.out.println(result.getColumnNames());
//            System.out.println(result.getColumnTypes());
//            System.out.println(JSON.toJSONString(result.next()));
//            System.out.println(JSON.toJSONString(result.next()));
//            System.out.println(JSON.toJSONString(result.next()));

        } catch (Exception e) {
            throw e;
        }

    }


}
