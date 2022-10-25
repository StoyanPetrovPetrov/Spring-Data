package JavaDBAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddMinions {
    private static final String GET_TOWN_BY_NAME = "select t.id  from towns t where t.name = ?";
    private static final String GET_VILLAIN_BY_NAME = "select v.id  from villains v where v.name = ?";
    private static final String GET_VILLAIN_NAME = "insert into villains(name,evilness_factor) values(?,?) ";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String INSERT_INTO_TOWN = "insert into town(name) values(?)";

    private static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.";
    private static final String COLUMN_LABEL_ID = "id";
    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSqlConnection();
        final Scanner scanner = new Scanner(System.in);
        final String[] minionInfo = scanner.nextLine().split(" ");
        final String minionName = minionInfo[1];
        final int minionAge = Integer.parseInt(minionInfo[2]);
        final String minionTown = minionInfo[3];

        final String villainName = scanner.nextLine().split(" ")[1];
        final int townId = getId(connection,List.of(minionTown),GET_TOWN_BY_NAME,INSERT_INTO_TOWN,TOWN_ADDED_FORMAT);
        final int villainId = getId(connection, List.of(villainName,EVILNESS_FACTOR),GET_VILLAIN_BY_NAME,INSERT_INTO_TOWN,TOWN_ADDED_FORMAT);



    }
    private static int getId(Connection connection,
                            List<String>arguments,
                             String selectQuery,
                             String insertQuery,
                             String printFormat) throws SQLException {
        final String name = arguments.get(0);

        final PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1,name);
        final ResultSet resultSet = selectStatement.executeQuery();

        if (!resultSet.next()){
            final PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1,name);
            statement.executeUpdate();
            System.out.printf(printFormat,name);
        }
        ResultSet newTownSet = selectStatement.executeQuery();
       return newTownSet.getInt(COLUMN_LABEL_ID);
    }
}
