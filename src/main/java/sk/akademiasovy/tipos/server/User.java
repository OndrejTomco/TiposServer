package sk.akademiasovy.tipos.server;

import java.util.Random;

public class User {
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String token;

    public User(String firstName, String lastName, String login,  String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        generateToken();
    }

    private void generateToken(){
        char [] text = new char[40];
        Random random = new Random();

        for(int i =0; i<40;i++){
            text[i] = (char) (random.nextInt(25)+65);
        }
        token = String.valueOf(text);
        System.out.println(token);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
