/*
 * Copyright (C) 2009-2018 Lightbend Inc. <https://www.lightbend.com>
 */
//package javaguide.sql;
package models;

import javax.inject.*;

import play.db.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import java.sql.*;

import models.Convertor;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Scanner;

@Singleton
public class ApplicationDatabase {

    private Database db;
    private DatabaseExecutionContext ec;

    @Inject
    public ApplicationDatabase(Database db, DatabaseExecutionContext ec) {
        this.db = db;
        this.ec = ec;
    }

    private void createTablesIfNotExists(Statement st) throws SQLException {

        // Creates table "alert"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS alert (" +
                "id serial PRIMARY KEY," +
                "umbox_id varchar(255)," +
                "info varchar(255)," +
                "stamp bigint" +
                ");"
        );

        // Creates table "umbox"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS umbox (" +
                "id serial PRIMARY KEY," +
                "umbox_id varchar(255)," +
                "umbox_name varchar(255)," +
                "device varchar(255)," +
                "started_at bigint" +
                ");"

        );

        // Creates table "device"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS device (" +
                "id serial PRIMARY KEY," +
                "device_id varchar(255)," +
                "name varchar(255)," +
                "type varchar(255)," +
                "group_id varchar(255)," +
                "ip_address varchar(255)," +
                "history_size varchar(255)," +
                "sampling_rate varchar(255)" +
                // Tags and policy file ommitted
                ");"
        );
    }

    // Unused
    public CompletionStage<Integer> addUmbox(Umbox umbox) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {

                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                // Adds new umbox based on form values
                st.executeUpdate("INSERT INTO umbox(umbox_id,umbox_name,device,started_at)" +
                        "VALUES ('" +
                        umbox.getUmboxId() + "','" +
                        umbox.getUmboxName() + "','" +
                        umbox.getDevice() + "','" +
                        umbox.getStartedAt() + "');"
                );

                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> addDevice(Device device) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {

                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                // TODO: Figure out where to store the device
                // Adds new device based on form values
                st.executeUpdate(
                        "INSERT INTO device(device_id,name,group_id,type,ip_address,history_size,sampling_rate)" +
                        "VALUES ('" +
                        device.getDeviceId() + "','" +
                        device.getName() + "','" +
                        device.getGroupId() + "','" +
                        device.getType() + "','" +
                        device.getIpAddress() + "','" +
                        device.getHistorySize() + "','" +
                        device.getSamplingRate() + "');"
                );

                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> dropAllTables() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                connection.prepareStatement("DROP TABLE IF EXISTS alert,umbox,device").execute();
                return 1;
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getDevices() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                ResultSet rs = st.executeQuery("SELECT * from device");
                try {
                    return Convertor.convertToJson(rs);
                } catch (Exception e) {
                    return null;
                }
            });
        }, ec);
    }

    public CompletionStage<Integer> logDevices() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                // https://coderwall.com/p/609ppa/printing-the-result-of-resultset
                ResultSet rs = st.executeQuery("SELECT * from device");
                ResultSetMetaData rsmd = rs.getMetaData();
                System.out.println("querying SELECT * FROM device");
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }

                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> logUmboxes() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                // https://coderwall.com/p/609ppa/printing-the-result-of-resultset
                ResultSet rs = st.executeQuery("SELECT * from umbox");
                ResultSetMetaData rsmd = rs.getMetaData();
                System.out.println("querying SELECT * FROM umbox");
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }

                return 1;
            });
        }, ec);
    }
}