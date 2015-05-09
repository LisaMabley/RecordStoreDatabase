package com.Lisa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by lisa on 4/21/15.
 */

public class DataModel {

    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DB_NAME = "recordStoreDB";
    private static final String USER = "user";
    private static final String PASS = "password";

    private static LinkedList<Statement> allStatements = new LinkedList<Statement>();
    private static Statement statement = null;
    private static Connection connection = null;
    private static ResultSet resultSet = null;

    PreparedStatement preparedStatement = null;

    // Constructor
    public DataModel() {

        openDatabaseConnections();
        createTableSQL();
        createTestConsignorDataSQL();
        createTestAlbumDataSQL();
    }

    protected void createTableSQL() {

        String createAlbumsTableSQL = "CREATE TABLE albums (" +
                "albumId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "consignorId INT, " +
                "artist VARCHAR(45), " +
                "title VARCHAR(45), " +
                "size INT, " +
                "condition INT, " +
                "price FLOAT, " +
                "date_consigned DATE, " +
                "status INT, " +
                "date_sold DATE)";
        String createAlbumTableAction = "Create album table";
        executeSqlUpdate(createAlbumsTableSQL, createAlbumTableAction);

        String createConsignorsTableSQL = "CREATE TABLE consignors (" +
                "consignorId int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "name varchar(30), " +
                "phone varchar(30), " +
                "email varchar(30), " +
                "amount_owed FLOAT)";
        String createConsignorsTableAction = "Create consignors table";
        executeSqlUpdate(createConsignorsTableSQL, createConsignorsTableAction);

        String createPaymentsTableSQL = "CREATE TABLE payments (" +
                "paymentId int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "consignerId int, " +
                "date_paid DATE, " +
                "amount_paid FLOAT)";
        String createPaymentsTableAction = "Create payments table";
        executeSqlUpdate(createPaymentsTableSQL, createPaymentsTableAction);
    }

    protected static void openDatabaseConnections() {
        try {
            connection = DriverManager.getConnection(PROTOCOL + DB_NAME + ";create=true", USER, PASS);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            allStatements.add(statement);

        } catch (SQLException sqlException) {
            System.out.println("Could not establish connection.");
            System.out.println(sqlException);
        }
    }

    protected static void executeSqlUpdate(String sql, String sqlAction) {
        try {
            statement.executeUpdate(sql);
            System.out.println(sqlAction + " succeeded.");

        } catch (SQLException sqlException) {
            System.out.println(sqlAction + " failed. Could not execute SQL statement.");
            System.out.println(sqlException);
        }
    }

    protected static void executeSqlUpdate(PreparedStatement ps, String sqlAction) {
        try {
            ps.executeUpdate();
            System.out.println(sqlAction + " succeeded.");

        } catch (SQLException sqlException) {
            System.out.println(sqlAction + " failed. Could not execute SQL statement.");
            System.out.println(sqlException);
        }
    }

    protected static ResultSet executeSqlQuery(String sql, String sqlAction) {

        try {
            resultSet = statement.executeQuery(sql);
            System.out.println(sqlAction + " succeeded.");

        } catch (SQLException sqlException) {
            System.out.println(sqlAction + " failed. Could not execute SQL query.");
            System.out.println(sqlException);
        }

        return resultSet;
    }

    private static void createTestConsignorDataSQL() {
        // TODO remove when final

        String sqlAction = "Insert consigner";

        // Test data SQL
        String addConsignor1 = "INSERT INTO consignors (name, email, phone, amount_owed) VALUES ('Eric Makela', 'sweetiecutie@gmail.com', '612-518-2421', 4.23)" ;
        executeSqlUpdate(addConsignor1, sqlAction);

        String addConsignor2 = "INSERT INTO consignors (name, email, phone, amount_owed) VALUES ('Anitra Budd', 'sweetiecutie@gmail.com', '612-618-3421', 18.79)" ;
        executeSqlUpdate(addConsignor2, sqlAction);

        String addConsignor3 = "INSERT INTO consignors (name, email, phone, amount_owed) VALUES ('Neil Taylor', 'sweetiecutie@gmail.com', '612-718-4421', 27.45)" ;
        executeSqlUpdate(addConsignor3, sqlAction);

        String addConsignor4 = "INSERT INTO consignors (name, email, phone, amount_owed) VALUES ('Anj Ronay', 'sweetiecutie@gmail.com', '612-818-5421', 43.21)" ;
        executeSqlUpdate(addConsignor4, sqlAction);

        String addConsignor5 = "INSERT INTO consignors (name, email, phone, amount_owed) VALUES ('Shauna JeMai', 'sweetiecutie@gmail.com', '612-918-6421', 19.27)" ;
        executeSqlUpdate(addConsignor5, sqlAction);

        String addConsignor6 = "INSERT INTO consignors (name, email, phone, amount_owed) VALUES ('Garret Ferderber', 'sweetiecutie@gmail.com', '612-018-7421', 14.75)" ;
        executeSqlUpdate(addConsignor6, sqlAction);
    }

    private static void createTestAlbumDataSQL() {
        // TODO remove when final

        try {
            FileReader reader = new FileReader("Albums.txt");
            BufferedReader buffReader = new BufferedReader(reader);
            String line;
            String[] splitLine;
            String artist;
            String title;
            Random randomNumberGenerator = new Random();
            int size;
            int condition;
            int consignorId;
            int status;
            int intPrice;
            float price;

            int month;
            int day;
            int year;
            String stringMonth;
            String stringDay;
            String stringYear;

            String stringDate;
            java.sql.Date dateConsigned;

            while (true) {
                // Iterate through lines until there are none left

                try {
                    line = buffReader.readLine();
                    splitLine = line.split("%");
                    artist = splitLine[0];
                    title = splitLine[1];

                } catch (IOException ioe) {
                    System.out.println("Could not open or read Albums.txt");
                    System.out.println(ioe.toString());
                    buffReader.close();
                    break;

                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    System.out.println("Index out of bounds exception.");
                    System.out.println(aiobe.toString());
                    buffReader.close();
                    break;
                }

                size = randomNumberGenerator.nextInt(6) + 1;
                condition = randomNumberGenerator.nextInt(6) + 1;
                consignorId = randomNumberGenerator.nextInt(6) + 1;
                status = 1;
                intPrice = randomNumberGenerator.nextInt(40) + 1;
                price = intPrice;
                price -= .01;

                month = randomNumberGenerator.nextInt(12) + 1;
                day = randomNumberGenerator.nextInt(28) + 1;
                year = randomNumberGenerator.nextInt(3) + 13;

                if (month < 10) {
                    stringMonth = "0" + String.valueOf(month);
                } else {
                    stringMonth = String.valueOf(month);
                }

                if (day < 10) {
                    stringDay = "0" + String.valueOf(day);
                } else {
                    stringDay = String.valueOf(day);
                }

                if (year < 10) {
                    stringYear = "200" + String.valueOf(year);
                } else {
                    stringYear = "20" + String.valueOf(year);
                }

                stringDate = stringYear + "-" + stringMonth + "-" + stringDay;
                System.out.println(stringDate);
                dateConsigned = java.sql.Date.valueOf(stringDate);
                System.out.println(dateConsigned);

                // Add each line to database
                executeAddAlbumSql(consignorId, artist, title, size, condition, dateConsigned, status, price);
            }
            buffReader.close();

        } catch (IOException ioe) {
            System.out.println("Could not open or read Albums.txt");
            System.out.println(ioe.toString());

        }
    }

    protected static void executeAddAlbumSql(int consignorId, String artist, String title, int size, int condition, java.sql.Date date_consigned, int status, float price) {
        // TODO remove when final

        try {
            String psInsertSql = "INSERT INTO albums (consignorId, artist, title, size, condition, date_consigned, status, price) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement psAlbum = connection.prepareStatement(psInsertSql);
            allStatements.add(psAlbum);
            psAlbum.setInt(1, consignorId);
            psAlbum.setString(2, artist);
            psAlbum.setString(3, title);
            psAlbum.setInt(4, size);
            psAlbum.setInt(5, condition);
            psAlbum.setDate(6, date_consigned);
            psAlbum.setInt(7, status);
            psAlbum.setFloat(8, price);
            executeSqlUpdate(psAlbum, "Add album");

        } catch (SQLException sqlException) {
            System.out.println("Could not add album.");
            System.out.println(sqlException);
        }
    }

    public static ArrayList<Consignor> getConsignors() {
        ArrayList<Consignor> consignorList = new ArrayList<Consignor>();
        String getConsignorsAction = "Get consignors";
        String getConsignorsSql = "SELECT * FROM consignors";

        try {
            ResultSet consignorRS = executeSqlQuery(getConsignorsSql, getConsignorsAction);
            while (consignorRS.next()) {
                String consignorName = consignorRS.getString("name");
                String consignorEmail = consignorRS.getString("email");
                String consignorPhone = consignorRS.getString("phone");
                int id = consignorRS.getInt("consignorId");
                Consignor newConsignor = new Consignor(consignorName, consignorEmail, consignorPhone, id);
                consignorList.add(newConsignor);
            }

        } catch (SQLException sqle) {
            System.out.println("Failed to read result set.");
            System.out.println(sqle);
        }

        return consignorList;
    }

    public static int getNumCopiesInInventory(String artist, String title, int status) {
        // Returns number of copies in store
        // TODO restrict SOLD and DONATED status to past 60 days

        int copiesInStore = 0;

        try {
            String invCountSql = "SELECT COUNT(*) AS numCopies FROM albums WHERE artist = ? AND title = ? AND status = ?";

            PreparedStatement psCheckInventory = connection.prepareStatement(invCountSql);
            allStatements.add(psCheckInventory);
            psCheckInventory.setString(1, artist);
            psCheckInventory.setString(2, title);
            psCheckInventory.setInt(3, status);
            resultSet = psCheckInventory.executeQuery();
            resultSet.next();
            copiesInStore = resultSet.getInt("numCopies");

        } catch (SQLException sqle) {
            System.out.println("Failed to count number of copies.");
            System.out.println(sqle);
        }

        return copiesInStore;
    }

    public static ArrayList<Album> searchInventoryForAlbums(String searchString, int fieldToSearch) {
        // Return ResultSet of albums with matching title or artist

        String searchSql = "";
        ArrayList<Album> searchResults = new ArrayList<Album>();

        if (fieldToSearch == SellGUI.ARTIST_FIELD) {
            searchSql = "SELECT * FROM albums WHERE (status = 1 OR status = 2) AND artist LIKE ?";

        } else if (fieldToSearch == SellGUI.TITLE_FIELD) {
            searchSql = "SELECT * FROM albums WHERE (status = 1 OR status = 2) AND title LIKE ?";

        } else {
            return null;
        }

        try {
            PreparedStatement psSearch = connection.prepareStatement(searchSql);
            allStatements.add(psSearch);
            String searchStringPlusPercentSigns = "%" + searchString + "%";
            psSearch.setString(1, searchStringPlusPercentSigns);
            resultSet = psSearch.executeQuery();
            searchResults = resultSetToArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        return searchResults;
    }

    public static void addAlbum(Album album) {

        try {
            String psInsertSql = "INSERT INTO albums (consignorId, artist, title, size, condition, price, date_consigned, status) " +
                    "VALUES ( ?, ?, ? , ?, ?, ?, ?, ? )";
            PreparedStatement psAlbum = connection.prepareStatement(psInsertSql);
            allStatements.add(psAlbum);
            psAlbum.setInt(1, album.consignorId);
            psAlbum.setString(2, album.artist);
            psAlbum.setString(3, album.title);
            psAlbum.setInt(4, album.size);
            psAlbum.setInt(5, album.condition);
            psAlbum.setFloat(6, album.price);
            psAlbum.setDate(7, album.dateConsigned);
            psAlbum.setInt(8, album.status);
            psAlbum.executeUpdate();
            System.out.println("Added album: " + album);

        } catch (SQLException sqlException) {
            System.out.println("Could not add album.");
            System.out.println(sqlException);
        }
    }

    public static void addConsignor(String name, String email, String phone) {
        // TODO Do not allow duplicates to be entered

        try {
            String psInsertConsignorSql = "INSERT INTO consignors (name, email, phone) " +
                    "VALUES ( ?, ?, ? )";
            PreparedStatement psConsignor = connection.prepareStatement(psInsertConsignorSql);
            allStatements.add(psConsignor);
            psConsignor.setString(1, name);
            psConsignor.setString(2, email);
            psConsignor.setString(3, phone);
            psConsignor.executeUpdate();
            System.out.println("Added consignor: " + name);

        } catch (SQLException sqlException) {
            System.out.println("Could not add consignor.");
            System.out.println(sqlException);
        }
    }

    public static void removeConsignor(Consignor consignorToRemove) {

        try {
            String psRemoveConsignorSql = "DELETE FROM consignors WHERE consignorId = ?";
            PreparedStatement psRemoveConsignor = connection.prepareStatement(psRemoveConsignorSql);
            allStatements.add(psRemoveConsignor);
            psRemoveConsignor.setInt(1, consignorToRemove.consignorId);
            psRemoveConsignor.executeUpdate();
            System.out.println("Deleted consignor: " + consignorToRemove.name);

        } catch (SQLException sqlException) {
            System.out.println("Could not remove consignor.");
            System.out.println(sqlException);
        }
    }

    public static void updateAlbumStatus(int albumId, int newStatus) {
        updateAlbumStatus(albumId, newStatus, null);
    }


    public static void updateAlbumStatus(int albumId, int newStatus, java.sql.Date dateSold) {

        try {
            String updateStatusSql = "";

            if (newStatus == Album.SOLD) {
                updateStatusSql = "UPDATE albums SET status = ?, date_sold = ? WHERE albumId = ?";

            }  else if (newStatus == Album.BARGAIN_BIN) {
                updateStatusSql = "UPDATE albums SET status = ?, price = 1 WHERE albumId = ?";
            }

            else if (newStatus == Album.DONATED || newStatus == Album.RETURNED_TO_CONSIGNOR){
                updateStatusSql = "UPDATE albums SET status = ?, price = 0 WHERE albumId = ?";
            }

            PreparedStatement psUdateAlbumStatus = connection.prepareStatement(updateStatusSql);
            allStatements.add(psUdateAlbumStatus);

            if (newStatus == Album.SOLD) {
                psUdateAlbumStatus.setInt(1, newStatus);
                psUdateAlbumStatus.setDate(2, dateSold);
                psUdateAlbumStatus.setInt(3, albumId);

            } else {
                psUdateAlbumStatus.setInt(1, newStatus);
                psUdateAlbumStatus.setInt(2, albumId);
            }

            executeSqlUpdate(psUdateAlbumStatus, "Update album status");

        } catch (SQLException sqlException) {
            System.out.println("Could not update album status.");
            System.out.println(sqlException);
        }
    }

//    public static void updateAlbumPrice(Album albumToUpdate, int newPrice) {
//
//        int albumId = albumToUpdate.albumId;
//
//        try {
//            String updatePriceSql = "UPDATE albums SET price = ? WHERE albumId = ?";
//
//            PreparedStatement psUdateAlbumPrice = connection.prepareStatement(updatePriceSql);
//            allStatements.add(psUdateAlbumPrice);
//
//            psUdateAlbumPrice.setInt(1, newPrice);
//            psUdateAlbumPrice.setInt(2, albumId);
//
//            executeSqlUpdate(psUdateAlbumPrice, "Update album price");
//
//        } catch (SQLException sqlException) {
//            System.out.println("Could not update album price.");
//            System.out.println(sqlException);
//        }
//    }

    public static ArrayList<Album> findAlbumsOfAge(java.sql.Date consignedBefore, int status) {

        ArrayList<Album> albumsConsignedBeforeDate = new ArrayList<Album>();
        String agingSql = "";

        if (status == Album.STORE) {
            agingSql = "SELECT * FROM albums WHERE status = 1 AND date_consigned < ?";

        } else if (status == Album.BARGAIN_BIN) {
            agingSql = "SELECT * FROM albums WHERE status = 2 AND date_consigned < ?";

        } else {
            return null;
        }

        try {
            PreparedStatement psAging = connection.prepareStatement(agingSql);
            allStatements.add(psAging);
            psAging.setDate(1, consignedBefore);
            resultSet = psAging.executeQuery();
            albumsConsignedBeforeDate = resultSetToArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        return albumsConsignedBeforeDate;
    }

    public static ArrayList<Album> findAlbumsFromConsignor(int consignorId) {

        ArrayList<Album> albumsFromConsignor = new ArrayList<Album>();
        String consignorAlbumsSql = "SELECT * FROM albums WHERE consignorId = ? ORDER BY date_consigned DESC";

        try {
            PreparedStatement psConsignorAlbums = connection.prepareStatement(consignorAlbumsSql);
            allStatements.add(psConsignorAlbums);
            psConsignorAlbums.setInt(1, consignorId);
            resultSet = psConsignorAlbums.executeQuery();
            albumsFromConsignor = resultSetToArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        return albumsFromConsignor;
    }

    private static ArrayList<Album> resultSetToArrayList(ResultSet resultSet) {
        ArrayList<Album> arraylist = new ArrayList<Album>();

        try {
            while (resultSet.next()) {
                int albumId = resultSet.getInt("albumId");
                int consignor = resultSet.getInt("consignorId");
                String artist = resultSet.getString("artist");
                String title = resultSet.getString("title");
                int size = resultSet.getInt("size");
                int condition = resultSet.getInt("condition");
                float price = resultSet.getFloat("price");
                java.sql.Date dateConsigned = resultSet.getDate("date_consigned");
                int status = resultSet.getInt("status");
                java.sql.Date dateSold = resultSet.getDate("date_sold");

                Album newAlbum = new Album(albumId, consignor, artist, title, size, condition, price, dateConsigned, status, dateSold);
                arraylist.add(newAlbum);
            }
        } catch (SQLException sqle) {
            System.out.println("Could not create album.");
            System.out.println(sqle);
        }

        return arraylist;
    }

    public static void closeDbConnections() {

        try {
            if (resultSet != null) {
                resultSet.close();
                System.out.println("Result set closed.");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        // Close all of the statements. Stored a reference to each statement in
        // allStatements so we can loop over all of them and close them all.
        for (Statement statement : allStatements) {

            if (statement != null) {
                try {
                    statement.close();
                    System.out.println("Statement closed.");

                } catch (SQLException sqle) {
                    System.out.println("Error closing statement.");
                    sqle.printStackTrace();
                }
            }
        }

        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
