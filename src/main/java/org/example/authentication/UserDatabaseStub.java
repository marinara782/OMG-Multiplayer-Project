package org.example.authentication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UserDatabaseStub {

    public String[] getUserIds(){
         String [] temp = null;
         return temp;
    }

    // First is userID
    // Second is password
    public Map<String, String> getProfiles(){
        Map<String, String> temp = null;
        return temp;
    }
    public String getPassword(String user){
        Map<String, String> temp = getProfiles();
        try{
            return temp.get(user);
        } catch (Exception e){
            return null;
        }
    }

    public boolean Authenticate_user(){
        return false;
    }

    //return a list of registered users
    public List<User> registered_users_list() throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        File file = new File("temp.txt");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",\\s*");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean verify_account(){
        return false;
    }

    public boolean verify_password(){
        return false;
    }

    public boolean passwords_match(){
        return false;
    }

    public boolean verify_username(){
        return false;
    }

    public void update_username(){

    }

    public void update_password(){

    }

    public void verify_email(){

    }

    public void verify_phone_number(){

    }

    public void linked_phone_number(){

    }


}
