package models;

/**
 *
 * @author godim
 */
public class AccountService {

    public AccountService() {
    }
    
    

    public User login(String username, String password) {

        if ((username.equals("abe") || username.equals("barb")) && password.equals("password")) {
            return new User(username, password);
        } else {
            return null;
        }

    }
}
