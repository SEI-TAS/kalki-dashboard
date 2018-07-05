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
                ")"
        );

        // Creates table "umbox"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS umbox (" +
                "id serial PRIMARY KEY," +
                "umbox_id varchar(255)," +
                "umbox_name varchar(255)," +
                "device varchar(255)," +
                "started_at bigint" +
                ")"

        );

        // Creates table "device"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS device (" +
                "id serial PRIMARY KEY," +
                "device_id varchar(255)," +
                "name varchar(255)," +
                "type varchar(255)," +
                "group_id varchar(255)," +
                "ip_address varchar(255)," +
                "history_size int," +
                "sampling_rate int" +
                // Tags and policy file ommitted
                ")"
        );

        // Creates table "group_id"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS group_id (" +
                "id serial PRIMARY KEY," +
                "info varchar(255)" +
                ")"
        );

        // Creates table "type"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS type (" +
                "id serial PRIMARY KEY," +
                "info varchar(255)" +
                ")"
        );

        // Creates table "tag"
        st.executeUpdate("CREATE TABLE IF NOT EXISTS tag (" +
                "id serial PRIMARY KEY," +
                "info varchar(255)" +
                ")"
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
                        umbox.getStartedAt() + "')"
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
                                device.getSamplingRate() + "')"
                );

                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> deleteDevice(String id) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {

                Statement st = connection.createStatement();
                createTablesIfNotExists(st);
                st.executeUpdate("DELETE FROM device WHERE id = " + id);

                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> dropAllTables() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                connection.prepareStatement("DROP TABLE IF EXISTS alert,umbox,device,group_id,type,tag").execute();
                return 1;
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getDevice(int id) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                ResultSet rs = st.executeQuery("SELECT * FROM device WHERE id=" + id);
                try {
                    return Convertor.convertToJson(rs);
                } catch (Exception e) {
                    return null;
                }
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getDevices() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                ResultSet rs = st.executeQuery("SELECT * FROM device");
                try {
                    return Convertor.convertToJson(rs);
                } catch (Exception e) {
                    return null;
                }
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getGroupIds() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                ResultSet rs = st.executeQuery("SELECT * FROM group_id");
                try {
                    return Convertor.convertToJson(rs);
                } catch (Exception e) {
                    return null;
                }
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getTypes() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                ResultSet rs = st.executeQuery("SELECT * FROM type;");
                try {
                    return Convertor.convertToJson(rs);
                } catch (Exception e) {
                    return null;
                }
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getTags() throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                ResultSet rs = st.executeQuery("SELECT * FROM tag");
                try {
                    return Convertor.convertToJson(rs);
                } catch (Exception e) {
                    return null;
                }
            });
        }, ec);
    }

    public CompletionStage<Integer> addGroupId(String group_id) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                st.executeUpdate("INSERT INTO group_id(info) values ('" + group_id + "')");
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> addType(String type) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                st.executeUpdate("INSERT INTO type(info) values ('" + type + "')");
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> addTag(String tag) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                st.executeUpdate("INSERT INTO tag(info) values ('" + tag + "');");
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> logDevices() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

                createTablesIfNotExists(st);

                // https://coderwall.com/p/609ppa/printing-the-result-of-resultset
                ResultSet rs = st.executeQuery("SELECT * FROM device");
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
                ResultSet rs = st.executeQuery("SELECT * FROM umbox");
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