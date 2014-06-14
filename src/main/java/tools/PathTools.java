package tools;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class PathTools {

    public static final String getUserAccountActivationPath(Long id) {
        StringBuilder builder = new StringBuilder(ConfigurationTools.MAIN_PAGE);
        builder.append("/users/").append(id);
        builder.append("/activation");
        return builder.toString();
    }

}
