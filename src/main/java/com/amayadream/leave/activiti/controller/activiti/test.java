package com.amayadream.leave.activiti.controller.activiti;

import com.amayadream.leave.util.LogUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

/**
 * NAME   :  LeaveSystem/com.amayadream.leave.activiti.controller.activiti
 * Author :  Amayadream
 * Date   :  2015.12.29 10:22
 * TODO   :
 */
public class test {

    private String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private String USERNAME = "activitidemo";
    private String PASSSWORD = "activitidemo";

    @Test
    public void getTableInfo(){
        try {
            Class.forName(DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSSWORD);
            DatabaseMetaData metadata = conn.getMetaData();
            System.out.println("数据库已知的用户: "+ metadata.getUserName());
            System.out.println("数据库的系统函数的逗号分隔列表: "+ metadata.getSystemFunctions());
            System.out.println("数据库的时间和日期函数的逗号分隔列表: "+ metadata.getTimeDateFunctions());
            System.out.println("数据库的字符串函数的逗号分隔列表: "+ metadata.getStringFunctions());
            System.out.println("数据库供应商用于 'schema' 的首选术语: "+ metadata.getSchemaTerm());
            System.out.println("数据库URL: " + metadata.getURL());
            System.out.println("是否允许只读:" + metadata.isReadOnly());
            System.out.println("数据库的产品名称:" + metadata.getDatabaseProductName());
            System.out.println("数据库的版本:" + metadata.getDatabaseProductVersion());
            System.out.println("驱动程序的名称:" + metadata.getDriverName());
            System.out.println("驱动程序的版本:" + metadata.getDriverVersion());
            System.out.println();
            System.out.println("数据库中使用的表类型");
            ResultSet rs = metadata.getTableTypes();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();

            System.out.println();
            /**
             * 获取指定的数据库的所有表的类型,getTables()的第一个参数就是数据库名
             * 因为与MySQL连接时没有指定,这里加上,剩下的参数就可以为null了
             * 第二个参数是模式名称的模式,但是输出也是什么都没有。谁知道告诉我一声
             */
            System.out.println("获取指定的数据库的所有表的类型");
            ResultSet rs1 = metadata.getTables("ssi2bbs", null, null, null);
            while (rs1.next()) {
                System.out.println();
                System.out.println("数据库名:"+ rs1.getString(1));
                System.out.println("表名: "+rs1.getString(3));
                System.out.println("类型: "+rs1.getString(4));
            }
            rs1.close();

            System.out.println();
            System.out.println("获取指定的数据库的表的主键");
            //获取指定的数据库的表的主键，第二个参数也是模式名称的模式,使用null了
            ResultSet rs2 = metadata.getPrimaryKeys("mysql", null, "db");
            while (rs2.next()) {
                System.out.println("主键名称: "+ rs2.getString(4));
            }
            rs2.close();

            System.out.println();
            System.out.println("DatabaseMetaData.getIndexInfo()方法返回信息:");
            ResultSet rs3 = metadata.getIndexInfo("ssi2bbs", null, "user", false, true);
            while (rs3.next()) {
                System.out.println("数据库名: "+ rs3.getString(1));
                System.out.println("表模式: "+ rs3.getString(2));
                System.out.println("表名称: "+ rs3.getString(3));
                System.out.println("索引值是否可以不唯一: "+ rs3.getString(4));
                System.out.println("索引类别: "+ rs3.getString(5));
                System.out.println("索引名称: "+ rs3.getString(6));
                System.out.println("索引类型: "+ rs3.getString(7));
                System.out.println("索引中的列序列号: "+ rs3.getString(8));
                System.out.println("列名称: "+ rs3.getString(9));
                System.out.println("列排序序列: "+ rs3.getString(10));
                System.out.println("TYPE为 tableIndexStatistic时它是表中的行数否则它是索引中唯一值的数量: "+ rs3.getString(11));
                System.out.println("TYPE为 tableIndexStatisic时它是用于表的页数否则它是用于当前索引的页数: "+ rs3.getString(12));
                System.out.println("过滤器条件: "+ rs3.getString(13));
            }
            rs3.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void asd(HttpServletRequest request){
        LogUtil logUtil = new LogUtil();
        try {
            Class.forName(DRIVER).newInstance();
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSSWORD);
            PreparedStatement ps = conn.prepareStatement("select * from EXPERIMENT");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsme = rs.getMetaData();

            int columnCount = rsme.getColumnCount();
            System.out.println("ResultSet对象中的列数"+ columnCount);
            for (int i = 1; i <= columnCount ; i++) {
                System.out.println();
                System.out.println("列名称: "+ rsme.getColumnName(i));
                System.out.println("列类型(DB): " + rsme.getColumnTypeName(i));
                System.out.println("长度: "+ rsme.getPrecision(i) );
                System.out.println("是否自动编号: "+ rsme.isAutoIncrement(i));
                System.out.println("是否可以为空: "+ rsme.isNullable(i));
                System.out.println("是否可以写入: "+ rsme.isReadOnly(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
