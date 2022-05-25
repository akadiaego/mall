package com.hxr.seckill.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxr.seckill.pojo.User;
import com.hxr.seckill.vo.RespBean;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(13000000000L+i);
            user.setNickname("user"+i);
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123456",user.getSalt()));
            user.setLoginCount(1);
            user.setRegisterDate(new Date());
            users.add(user);
        }
        System.out.println("create user");
        //插入数据库
        Connection conn = getConn();
        String sql = "insert ignore into user(login_count,nickname,register_date,salt,password,id) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            preparedStatement.setInt(1,user.getLoginCount());
            preparedStatement.setString(2,user.getNickname());
            preparedStatement.setTimestamp(3,new Timestamp(user.getRegisterDate().getTime()));
            preparedStatement.setString(4,user.getSalt());
            preparedStatement.setString(5,user.getPassword());
            preparedStatement.setLong(6,user.getId());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.clearParameters();
        conn.close();
        System.out.println("insert to db");
        //生成cookie
        String urlString = "http://localhost:8080/login/toLogin";
        File file = new File("../config.txt");
        if (file.exists()){
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" +MD5Util.inputPassToFromPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream in = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while((len = in.read(buff))>=0){
                bout.write(buff,0,len);
            }
            in.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = (String) respBean.getObj();
            System.out.println("create userTicket");
            String row = user.getId()+","+userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file " + user.getId());
        }
        raf.close();
        System.out.println("over");
    }

    private static Connection getConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
