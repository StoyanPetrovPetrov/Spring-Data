package JavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintAllMinionNames {
    private static final String GET_MINIONS_NAMES = "SELECT m.name FROM `minions` AS m";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();
        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAMES,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        final ResultSet minions = minionsStatement.executeQuery();

        int minionsCount = 0;

        while (minions.next()) {
            minionsCount++;
        }

        minions.beforeFirst();

        int firstIndex = 1;
        int lastIndex = minionsCount;

        for (int i = 1; i < minionsCount + 1; i++) {
            if (i % 2 != 0) {
                minions.absolute(firstIndex);
                firstIndex++;
            } else {
                minions.absolute(lastIndex);
                lastIndex--;
            }

            System.out.println(minions.getString(Constants.COLUMN_LABEL_NAME));

            minions.next();
        }
    }
}
