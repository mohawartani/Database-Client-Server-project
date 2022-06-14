import java.sql.*;

public class Database {

    //This method executes a query and returns the number of albums for the artist with name artistName
    private static Connection con;

    public int getTitles(String artistName) {
        int titleNum = 0;
        //Implement this method
        //Prevent SQL injections!
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(Credentials.URL,
                    Credentials.USERNAME,
                    Credentials.PASSWORD);

            String query = "SELECT  COUNT(*) FROM album INNER JOIN artist ON album.artistID = artist.artistID WHERE artist.name = ?";


            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, artistName);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            titleNum = resultSet.getInt("count");


            statement.close();
            con.close();

        }

        catch (Exception e){
            e.getStackTrace();
        }

        return titleNum;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection() {
        //Implement this method
        //5 sec timeout
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(Credentials.URL,
                    Credentials.USERNAME,
                    Credentials.PASSWORD);
            return con.isValid(5);
        }

        catch (SQLException | ClassNotFoundException e){
            e.getStackTrace();
        }
        return true;
    }
}