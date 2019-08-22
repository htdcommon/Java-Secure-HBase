package com.sensetime;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.security.UserGroupInformation;
import java.io.IOException;

/**
 * Java连接Secure HBase
 * 必须将hbase-site.xml放到resources目录下
 */
public class HBaseTest {

    public static void main(String[] args) throws IOException{

        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");

        Configuration conf = null;
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "node-01");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hadoop.security.authentication" , "Kerberos" );

        UserGroupInformation.setConfiguration(conf);

        UserGroupInformation.loginUserFromKeytab("hatieda@HADOOP-VIDEO.DATA.SENSETIME.COM", "/home/hatieda/hatieda.hadoop-video.keytab");

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("TEST:PERSON"));

        System.out.println(table.getName());

        Scan scan = new Scan();

        ResultScanner rs = table.getScanner(scan);

        for (Result r : rs) {

            System.out.println(r.toString());
        }

    }

}
