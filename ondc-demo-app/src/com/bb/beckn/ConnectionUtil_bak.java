package com.bb.beckn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
public class ConnectionUtil_bak {
	
	
    private static BasicDataSource ds = null;
    public synchronized static Connection getConnection() {
        Connection conn = null;
        
        try {
            if (ds == null) {
                Properties props = new Properties();
            
                InputStream is = ConnectionUtil_bak.class.getClassLoader().getResourceAsStream("db.properties");
                props.load(is);
                ds = new BasicDataSource();
                ds.setDriverClassName("spring.datasource.DRIVER.CLASS");
                ds.setUrl("spring.datasource.url");
                ds.setUsername("spring.datasource.username");
                ds.setPassword("spring.datasource.password");
                ds.setInitialSize(9); // The initial number of connections that
                                        // are created when the pool is started.
                ds.setMaxTotal(20); // The maximum number of active connections
                                        // that can be allocated from this pool
            }
            conn = ds.getConnection();
           
            /*PreparedStatement pstmt = conn.prepareStatement("update bornbhukkad.bb_admin_panel_vendors_kirana set symbol=? where id=?");
            
            InputStream in = new FileInputStream("E:\\Software\\wget-1.21.2-win64\\download\\Files\\Sri Laxmi Kirana & General Store.jpg");
            pstmt.setBlob(1, in);
            
            pstmt.setString(2, "32");
            //Inserting Blob type
            
            //Executing the statement
            pstmt.execute();
            System.out.println("Record inserted......");
            
            
             PreparedStatement pstmt2 = conn.prepareStatement("update bornbhukkad.bb_admin_panel_vendors_item_details_kirana set Item_image_1=? where id=?");
            
            InputStream in2 = new FileInputStream("E:\\Software\\wget-1.21.2-win64\\download\\Files\\40159884-6_1-super-saver-chana-dal.jpg");
            pstmt2.setBlob(1, in2);
            
            pstmt2.setString(2, "39");
            //Inserting Blob type
            
            //Executing the statement
            pstmt2.execute();
            System.out.println("Record inserted...2...");
            
            
            PreparedStatement pstmt3 = conn.prepareStatement("update bornbhukkad.bb_admin_panel_vendors_item_details_kirana set Item_image_2=? where id=?");
            
            InputStream in3 = new FileInputStream("E:\\Software\\wget-1.21.2-win64\\download\\Files\\40159884-3_3-super-saver-chana-dal.jpg");
            pstmt3.setBlob(1, in3);
            
            pstmt3.setString(2, "39");
            //Inserting Blob type
            
            //Executing the statement
            pstmt3.execute();
            System.out.println("Record inserted...3...");
            
            
            PreparedStatement pstmt4 = conn.prepareStatement("update bornbhukkad.bb_admin_panel_vendors_item_details_kirana set Item_image_3=? where id=?");
            
            InputStream in4 = new FileInputStream("E:\\Software\\wget-1.21.2-win64\\download\\Files\\40159884-4_1-super-saver-chana-dal.jpg");
            pstmt4.setBlob(1, in4);
            
            pstmt4.setString(2, "39");
            //Inserting Blob type
            
            //Executing the statement
            pstmt4.execute();
            System.out.println("Record inserted...4...");
            
            
            PreparedStatement pstmt5 = conn.prepareStatement("update bornbhukkad.bb_admin_panel_vendors_item_details_kirana set Item_image_4=? where id=?");
            
            InputStream in5 = new FileInputStream("E:\\Software\\wget-1.21.2-win64\\download\\Files\\40159884-5_1-super-saver-chana-dal.jpg");
            pstmt5.setBlob(1, in5);
            
            pstmt5.setString(2, "39");
            //Inserting Blob type
            
            //Executing the statement
            pstmt5.execute();
            System.out.println("Record inserted....5..");*/
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
