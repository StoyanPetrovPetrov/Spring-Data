package JavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static String GET_MINIONS_NAME_AGE_BY_VILLAIN_ID =
            "select m.name,m.age" +
                    " from minions as m" +
                    " join minions_villains mv on m.id = mv.minion_id" +
                    " where mv.villain_id = ?";
    private static final String COLUMN_LABEL_AGE = "age";
    private static final String GET_VILLAIN_NAME_BY_ID =
            "select v.name from villains v where v.id = ?";
    private static String VILLAIN_FORMAT = "Villain: %s%n";
    private static String MINION_FORMAT = "%d. %s %d%n";
    private static String NO_VILLAIN_FORMAT = "No villain with ID %d exists in the database.";


    public static void main(String[] args) throws SQLException {
        final Connection connection = Util.getSqlConnection();
        final int villainId = new Scanner(System.in).nextInt();

      final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        villainStatement.setInt(1,villainId);
      final ResultSet villainSet = villainStatement.executeQuery();

      if (!villainSet.next()){
          System.out.printf(NO_VILLAIN_FORMAT,villainId);
          connection.close();
          return;
      }
      final String villainName =  villainSet.getString(Constans.COLUMN_LABEL_NAME);
        System.out.printf(VILLAIN_FORMAT,villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAME_AGE_BY_VILLAIN_ID);
        minionsStatement.setInt(1,villainId);
        final ResultSet minionSet = minionsStatement.executeQuery();
        for (int index = 1; minionSet.next(); index++) {
            final String minionName = minionSet.getString(Constans.COLUMN_LABEL_NAME);
            final int minionAge = minionSet.getInt(COLUMN_LABEL_AGE);
            System.out.printf(MINION_FORMAT,index,minionName,minionAge);
        }
        connection.close();



    }
}
