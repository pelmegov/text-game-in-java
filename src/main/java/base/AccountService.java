package base;

/**
 * @author modkomi
 * @since 21.01.2016
 */
public interface AccountService {
    void signUp(String login, String password);

    boolean signIn(String login, String password);
}
