package frontend;

import base.AccountService;
import base.DBService;
import base.UserProfile;
import dbService.DBException;

/**
 * @author modkomi
 * @since 21.01.2016
 */
public class AccountServiceImpl implements AccountService {
    private final DBService dbService;

    public AccountServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void signUp(String login, String password) {
        try {
            dbService.addUser(new UserProfile(login, password));
        } catch (DBException e) {
            System.out.println("Can't sing in: " + e.getMessage());
        }
    }

    @Override
    public boolean signIn(String login, String password) {
        try {
            UserProfile profile = dbService.getUser(login);
            return profile != null && profile.getPassword().equals(password);
        } catch (DBException e) {
            System.out.println("Can't sing in: " + e.getMessage());
            return false;
        }
    }
}
