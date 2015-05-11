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
//        createTableSQL();
//        createTestConsignorDataSQL();
//        createTestAlbumDataSQL();
//        createTestPaymentDataSQL();
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
                "consignorId int, " +
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

    // TODO refactor so these are not each repeated twice
    protected static void executeSqlUpdate(String sql, String sqlAction) {
        try {
            statement.executeUpdate(sql);
            System.out.println(sqlAction + " succeeded.");

        } catch (SQLException sqlException) {
            System.out.println(sqlAction + " failed. Could not execute SQL statement.");
            System.out.println(sqlException);
        }
    }

    protected static void executePsUpdate(PreparedStatement ps, String sqlAction) {
        // TODO Only used when adding test data. Remove when final
        try {
            ps.executeUpdate();
            System.out.println(sqlAction + " succeeded.");

        } catch (SQLException sqlException) {
            System.out.println(sqlAction + " failed. Could not execute SQL statement.");
            System.out.println(sqlException);
        }
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

    private static void createTestPaymentDataSQL() {
        // TODO remove when final

        Random randomNumberGenerator = new Random();
        int month;
        int day;
        int year;
        String stringMonth;
        String stringDay;
        String stringYear;

        String stringDate;
        java.sql.Date paymentDate;

        int beforeDecimal;
        int afterDecimal;

        int consignorId;

        for (int x = 1; x < 60; x ++) {
            month = randomNumberGenerator.nextInt(12) + 1;
            day = randomNumberGenerator.nextInt(28) + 1;
            year = randomNumberGenerator.nextInt(4) + 12;

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
            paymentDate = java.sql.Date.valueOf(stringDate);

            beforeDecimal = randomNumberGenerator.nextInt(98) + 1;
            afterDecimal = randomNumberGenerator.nextInt(98) + 1;
            String paymentAmountString = beforeDecimal + "." + afterDecimal;
            Float paymentAmount = Float.parseFloat(paymentAmountString);

            consignorId = randomNumberGenerator.nextInt(6) + 1;

            String psPaymentSql = "INSERT INTO payments (consignorId, date_paid, amount_paid) VALUES ( ?, ?, ? )";

            try {
                PreparedStatement psPayment = connection.prepareStatement(psPaymentSql);
                allStatements.add(psPayment);
                psPayment.setInt(1, consignorId);
                psPayment.setDate(2, paymentDate);
                psPayment.setFloat(3, paymentAmount);
                executePsUpdate(psPayment, "Add payment");
                System.out.println("Adding payment: " + paymentAmount + ", " + paymentDate);

            } catch (SQLException sqle) {
                System.out.println("Could not execute search.");
                System.out.println(sqle);
            }
        }
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
                year = randomNumberGenerator.nextInt(4) + 12;

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
                dateConsigned = java.sql.Date.valueOf(stringDate);

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
            executePsUpdate(psAlbum, "Add album");

        } catch (SQLException sqlException) {
            System.out.println("Could not add album.");
            System.out.println(sqlException);
        }
    }

    public static ArrayList<Consignor> getConsignors(int findGroup) {
        ArrayList<Consignor> consignorArrayList = new ArrayList<Consignor>();
        String getConsignorsSql = "";
        java.sql.Date albumsConsignedBeforeDate = Album.albumsConsignedBeforeThisDateGoToBargainBinToday();

        switch (findGroup) {
            case 1:
                getConsignorsSql = "SELECT * FROM consignors ORDER BY name ASC";
                break;
            case 2:
                // Informed by this tutorial: https://howtoprogramwithjava.com/sql-subquery/
                getConsignorsSql = "SELECT * FROM consignors WHERE consignorId in (SELECT DISTINCT consignorId FROM albums WHERE status = 1 AND date_consigned < ?) ORDER BY name";
                break;
            case 3:
                getConsignorsSql = "SELECT * FROM consignors WHERE amount_owed > 10 ORDER BY name ASC";
                break;
        }
        try {
            if (findGroup == Consignor.FINDGROUP_TO_NOTIFY) {
                PreparedStatement psConsignorsToNotify = connection.prepareStatement(getConsignorsSql);
                allStatements.add(psConsignorsToNotify);
                psConsignorsToNotify.setDate(1, albumsConsignedBeforeDate);
                resultSet = psConsignorsToNotify.executeQuery();
            } else {
                resultSet = statement.executeQuery(getConsignorsSql);
            }

            consignorArrayList = resultSetToConsignorArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        return consignorArrayList;
    }

    public static int getNumCopiesInInventory(String artist, String title, int status) {
        // Returns number of copies in store
        // TODO restrict STATUS_SOLD and STATUS_DONATED status to past 60 days

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
        // TODO Ignore case

        String searchSql = "";
        ArrayList<Album> searchResults = new ArrayList<Album>();

        if (fieldToSearch == SellGUI.ARTIST_FIELD) {
            searchSql = "SELECT * FROM albums WHERE (status = 1 OR status = 2) AND artist LIKE ? ORDER BY artist";

        } else if (fieldToSearch == SellGUI.TITLE_FIELD) {
            searchSql = "SELECT * FROM albums WHERE (status = 1 OR status = 2) AND title LIKE ? ORDER BY title";

        } else {
            return null;
        }

        try {
            PreparedStatement psSearch = connection.prepareStatement(searchSql);
            allStatements.add(psSearch);
            String searchStringPlusPercentSigns = "%" + searchString + "%";
            psSearch.setString(1, searchStringPlusPercentSigns);
            resultSet = psSearch.executeQuery();
            searchResults = resultSetToAlbumArrayList(resultSet);

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

//    public static void updateAlbumStatus(Album albumToUpdate, int newStatus) {
//        updateAlbumStatus(albumToUpdate, newStatus, null);
//    }

    public static void updateAlbumStatus(Album albumToUpdate, int newStatus, java.sql.Date dateSold) {

        try {
            String updateStatusSql = "";

            if (newStatus == Album.STATUS_SOLD) {
                updateStatusSql = "UPDATE albums SET status = ?, date_sold = ? WHERE albumId = ?";
                updateConsignorBalance(albumToUpdate);

            }  else if (newStatus == Album.STATUS_BARGAIN_BIN) {
                updateStatusSql = "UPDATE albums SET status = ?, price = 1 WHERE albumId = ?";
            }

            else if (newStatus == Album.STATUS_DONATED || newStatus == Album.STATUS_RETURNED_TO_CONSIGNOR){
                updateStatusSql = "UPDATE albums SET status = ?, price = 0 WHERE albumId = ?";
            }

            PreparedStatement psUdateAlbumStatus = connection.prepareStatement(updateStatusSql);
            allStatements.add(psUdateAlbumStatus);

            if (newStatus == Album.STATUS_SOLD) {
                psUdateAlbumStatus.setInt(1, newStatus);
                psUdateAlbumStatus.setDate(2, dateSold);
                psUdateAlbumStatus.setInt(3, albumToUpdate.albumId);

            } else {
                psUdateAlbumStatus.setInt(1, newStatus);
                psUdateAlbumStatus.setInt(2, albumToUpdate.albumId);
            }

            executePsUpdate(psUdateAlbumStatus, "Update album status");

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
//            executePsUpdate(psUdateAlbumPrice, "Update album price");
//
//        } catch (SQLException sqlException) {
//            System.out.println("Could not update album price.");
//            System.out.println(sqlException);
//        }
//    }

    private static float getConsignorBalance(int consignorId) {
        String getConsignorBalanceSql = "SELECT amount_owed FROM consignors WHERE consignorId = ?";
        float consignorBalance = 0.0f;

        try {
            PreparedStatement psConsignorBalance = connection.prepareStatement(getConsignorBalanceSql);
            allStatements.add(psConsignorBalance);
            psConsignorBalance.setInt(1, consignorId);
            ResultSet consignorRS = psConsignorBalance.executeQuery();
            while (consignorRS.next()) {
                consignorBalance = consignorRS.getFloat("amount_owed");
            }

        } catch (SQLException sqle) {
            System.out.println("Failed to read result set.");
            System.out.println(sqle);
        }
        return consignorBalance;
    }

    private static void setConsignorBalance(int consignorId, float newBalance) {
        String psSetConsignorBalanceSql = "UPDATE consignors SET amount_owed = ? WHERE consignorId = ?";

        try {
            PreparedStatement psSetConsignorBalance = connection.prepareStatement(psSetConsignorBalanceSql);
            allStatements.add(psSetConsignorBalance);
            psSetConsignorBalance.setFloat(1, newBalance);
            psSetConsignorBalance.setInt(2, consignorId);
            psSetConsignorBalance.executeUpdate();

        } catch (SQLException sqle) {
            System.out.println("Failed to read result set.");
            System.out.println(sqle);
        }
    }

    private static void updateConsignorBalance(Album albumSold) {
        float amountConsignorEarned = albumSold.price * .4f;
        float consignorBalance = getConsignorBalance(albumSold.consignorId);
        consignorBalance += amountConsignorEarned;
        setConsignorBalance(albumSold.consignorId, consignorBalance);
    }

    private static void updateConsignorBalance(Payment paymentMade) {
        float consignorBalance = getConsignorBalance(paymentMade.consignorId);
        consignorBalance -= paymentMade.amount;
        setConsignorBalance(paymentMade.consignorId, consignorBalance);
    }

    public static ArrayList<Album> findAlbumsOfAge(java.sql.Date consignedBefore, int status) {

        ArrayList<Album> albumsConsignedBeforeDate = new ArrayList<Album>();
        String agingSql = "";

        if (status == Album.STATUS_STORE) {
            agingSql = "SELECT * FROM albums WHERE status = 1 AND date_consigned < ?";

        } else if (status == Album.STATUS_BARGAIN_BIN) {
            agingSql = "SELECT * FROM albums WHERE status = 2 AND date_consigned < ?";

        } else {
            return null;
        }

        try {
            PreparedStatement psAging = connection.prepareStatement(agingSql);
            allStatements.add(psAging);
            psAging.setDate(1, consignedBefore);
            resultSet = psAging.executeQuery();
            albumsConsignedBeforeDate = resultSetToAlbumArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        return albumsConsignedBeforeDate;
    }

    public static ArrayList<ConsignorAlbum> findAllAlbumsFromConsignor(int consignorId) {

        ArrayList<Album> albumsFromConsignor = new ArrayList<Album>();
        String consignorAlbumsSql = "SELECT * FROM albums WHERE consignorId = ? ORDER BY status ASC, date_consigned DESC";

        try {
            PreparedStatement psConsignorAlbums = connection.prepareStatement(consignorAlbumsSql);
            allStatements.add(psConsignorAlbums);
            psConsignorAlbums.setInt(1, consignorId);
            resultSet = psConsignorAlbums.executeQuery();
            albumsFromConsignor = resultSetToAlbumArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        ArrayList<ConsignorAlbum> consignorAlbums = new ArrayList<ConsignorAlbum>();
        for (Album album : albumsFromConsignor) {
            ConsignorAlbum newConsignorAlbum = new ConsignorAlbum(album);
            consignorAlbums.add(newConsignorAlbum);
        }

        return consignorAlbums;
    }

    public static ArrayList<ConsignorAlbum> findUnsoldAlbumsFromConsignor(int consignorId) {

        ArrayList<Album> unsoldAlbumsFromConsignor = new ArrayList<Album>();
        String consignorUnsoldAlbumsSql = "SELECT * FROM albums WHERE consignorId = ? AND status = 1 AND date_consigned < ? ORDER BY date_consigned DESC";

        try {
            PreparedStatement psConsignorUnsoldAlbums = connection.prepareStatement(consignorUnsoldAlbumsSql);
            allStatements.add(psConsignorUnsoldAlbums);
            psConsignorUnsoldAlbums.setInt(1, consignorId);
            psConsignorUnsoldAlbums.setDate(2, Album.albumsConsignedBeforeThisDateGoToBargainBinToday());
            resultSet = psConsignorUnsoldAlbums.executeQuery();
            unsoldAlbumsFromConsignor = resultSetToAlbumArrayList(resultSet);

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        ArrayList<ConsignorAlbum> consignorAlbums = new ArrayList<ConsignorAlbum>();
        for (Album album : unsoldAlbumsFromConsignor) {
            ConsignorAlbum newConsignorAlbum = new ConsignorAlbum(album);
            consignorAlbums.add(newConsignorAlbum);
        }

        return consignorAlbums;
    }

    public static ArrayList<Payment> findAllPaymentsToConsignor(int consignorId) {

        ArrayList<Payment> paymentsToConsignor = new ArrayList<Payment>();
        String consignorPaymentsSql = "SELECT * FROM payments WHERE consignorId = ? ORDER BY date_paid DESC";

        try {
            PreparedStatement psConsignorPayments = connection.prepareStatement(consignorPaymentsSql);
            allStatements.add(psConsignorPayments);
            psConsignorPayments.setInt(1, consignorId);
            resultSet = psConsignorPayments.executeQuery();

            while (resultSet.next()) {
                java.sql.Date date = resultSet.getDate("date_paid");
                float amount = resultSet.getFloat("amount_paid");
                Payment newPayment = new Payment(consignorId, date, amount);
                paymentsToConsignor.add(newPayment);
            }

        } catch (SQLException sqle) {
            System.out.println("Could not execute search.");
            System.out.println(sqle);
        }

        return paymentsToConsignor;
    }

    public static void recordPayment(Payment paymentMade) {

        updateConsignorBalance(paymentMade);

        try {
            String psInsertPaymentSql = "INSERT INTO payments (consignorId, date_paid, amount_paid) " +
                    "VALUES ( ?, ?, ? )";
            PreparedStatement psInsertPayment = connection.prepareStatement(psInsertPaymentSql);
            allStatements.add(psInsertPayment);
            psInsertPayment.setInt(1, paymentMade.consignorId);
            psInsertPayment.setDate(2, paymentMade.date);
            psInsertPayment.setFloat(3, paymentMade.amount);
            psInsertPayment.executeUpdate();
            System.out.println("Added payment: " + paymentMade.date + ", " + paymentMade.amount);

        } catch (SQLException sqlException) {
            System.out.println("Could not add payment.");
            System.out.println(sqlException);
        }
    }

    private static ArrayList<Album> resultSetToAlbumArrayList(ResultSet resultSet) {
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

    private static ArrayList<Consignor> resultSetToConsignorArrayList(ResultSet resultSet) {
        ArrayList<Consignor> arraylist = new ArrayList<Consignor>();

        try {
            while (resultSet.next()) {
                String consignorName = resultSet.getString("name");
                String consignorEmail = resultSet.getString("email");
                String consignorPhone = resultSet.getString("phone");
                int id = resultSet.getInt("consignorId");
                float amountOwed = resultSet.getFloat("amount_owed");
                Consignor newConsignor = new Consignor(id, consignorName, consignorEmail, consignorPhone, amountOwed);
                arraylist.add(newConsignor);
            }

        } catch (SQLException sqle) {
            System.out.println("Failed to read result set.");
            System.out.println(sqle);
        }

        return arraylist;
    }

//    private static int resultSetToStatusInt(ResultSet resultSet) {
//        int status = 0;
//
//        try {
//            while (resultSet.next()) {
//                status = resultSet.getInt("status");
//            }
//
//        } catch (SQLException sqle) {
//            System.out.println("Could not get album status.");
//            System.out.println(sqle);
//        }
//
//        return status;
//    }

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
