package sk.akademiasovy.tipos.server.db;

import sk.akademiasovy.tipos.server.Signup;
import sk.akademiasovy.tipos.server.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {

    private Connection conn;
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/tipos";
    private String userName = "root";
    private String password = "";

    public User getUser(String username, String password){
        try{
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.userName, this.password);

            String query = "SELECT * from users where login like ? and  password like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User user=new User(rs.getString("firstName"),rs.getString("lastName"),rs.getString("login"),rs.getString("email"));
                query = "UPDATE tokens SET token=? WHERE idu=?";
                ps = conn.prepareStatement(query);
                ps.setInt(2,rs.getInt("id"));
                ps.setString(1, user.getToken());
                ps.executeUpdate();
                System.out.println(ps);
                return user;
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void logout( String token) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.userName, this.password);

            String query = "UPDATE tokens SET token=\"\" where token like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,token);
            System.out.println(ps);
            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public boolean checkIfEmailOrLoginExist(String login, String email) {

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.userName, this.password);

            String query = "SELECT count(*) as num FROM users WHERE login like ? or email like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,login);
            ps.setString(2,email);
            ResultSet rs=ps.executeQuery();
            System.out.println(ps);

            rs.next();
            if(rs.getInt("num") == 0){
                return false;
            }
            else{
                return true;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return true;

    }

    public void insertNewUser(Signup signup) {

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.userName, this.password);

            String query = "Insert into users(firstname, lastname, email, login, password) values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,signup.firstname);
            ps.setString(2,signup.lastname);
            ps.setString(3,signup.email);
            ps.setString(4,signup.login);
            ps.setString(5,signup.password);
            ps.executeUpdate();


        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean checkLogin(String login) {


        return true;
    }
}

