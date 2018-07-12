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

    private void createTablesIfNotExists(Connection conn) throws SQLException {

        // DELETE THIS
//        dropAllTables();

        // Creates table "alert"
        String query = "CREATE TABLE IF NOT EXISTS alert (" +
                "id serial PRIMARY KEY," +
                "umbox_id varchar(255)," +
                "info varchar(255)," +
                "stamp bigint" +
                ");";

        // Creates table "umbox"
        query += "CREATE TABLE IF NOT EXISTS umbox (" +
                "id serial PRIMARY KEY," +
                "umbox_id varchar(255)," +
                "umbox_name varchar(255)," +
                "device varchar(255)," +
                "started_at bigint" +
                ");";

        // Creates table "umbox_image"
        query += "CREATE TABLE IF NOT EXISTS umbox_image (" +
                "id serial PRIMARY KEY," +
                "name varchar(255)," +
                "path varchar(255)" +
                ");";

        // Creates table "device"
        query += "CREATE TABLE IF NOT EXISTS device (" +
                "id serial PRIMARY KEY," +
                "name varchar(255)," +
                "description varchar(255)," +
                "type varchar(255)," +
                "group_id varchar(255)," +
                "ip_address varchar(255)," +
                "history_size int," +
                "sampling_rate int," +
                "policy_file bytea" +
                ");";

        // Creates table "device_history"
        query+= "CREATE TABLE IF NOT EXISTS device_history (" +
                "id serial PRIMARY KEY," +
                "attributes json," +
                "device_id int," +
                "timestamp timestamp" +
                ");";

        // Creates table "group_id"
        query += "CREATE TABLE IF NOT EXISTS group_id (" +
                "id serial PRIMARY KEY," +
                "name varchar(255)" +
                ");";

        // Creates table "type"
        query += "CREATE TABLE IF NOT EXISTS type (" +
                "id serial PRIMARY KEY," +
                "name varchar(255)" +
                ");";

        // Creates table "tag"
        query += "CREATE TABLE IF NOT EXISTS tag (" +
                "id serial PRIMARY KEY," +
                "name varchar(255)" +
                ")";

        conn.prepareStatement(query).executeUpdate();

    }

    // Unused
    public CompletionStage<Integer> addUmboxContainer(UmboxContainer umbox) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement("INSERT INTO umbox (umbox_id, umbox_name, device, started_at) VALUES (?, ?, ?, ?)");
                    st.setString(1, umbox.getUmboxId());
                    st.setString(2, umbox.getUmboxName());
                    st.setString(3, umbox.getDevice());
                    st.setInt(4, umbox.getStartedAt());
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> addUmboxImage(UmboxImage u) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement("INSERT INTO umbox_image (name, path) VALUES (?, ?)");
                    st.setString(1, u.getName());
                    st.setString(2, u.getPath());
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> editUmboxImage(UmboxImage u, String id) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement("UPDATE umbox_image " +
                            "SET name = ?, path = ? " +
                            "WHERE id = ?");
                    st.setString(1, u.getName());
                    st.setString(2, u.getPath());
                    st.setInt(3, Integer.parseInt(id));
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                catch (NumberFormatException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> addDevice(Device device) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    // Adds new device based on form values
                    st = conn.prepareStatement("INSERT INTO device (name, description, group_id, type, ip_address, history_size, sampling_rate, policy_file) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    st.setString(1, device.getName());
                    st.setString(2, device.getDescription());
                    st.setString(3, device.getGroupId());
                    st.setString(4, device.getType());
                    st.setString(5, device.getIpAddress());
                    st.setInt(6, device.getHistorySize());
                    st.setInt(7, device.getSamplingRate());
                    st.setBytes(8, device.getPolicyFile());
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> editDevice(Device device, String id) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement("UPDATE device " +
                            "SET name = ?, description = ?, group_id = ?, type = ?, ip_address = ?, history_size = ?, sampling_rate = ?, policy_file = ? " +
                            "WHERE id = ?");
                    st.setString(1, device.getName());
                    st.setString(2, device.getDescription());
                    st.setString(3, device.getGroupId());
                    st.setString(4, device.getType());
                    st.setString(5, device.getIpAddress());
                    st.setInt(6, device.getHistorySize());
                    st.setInt(7, device.getSamplingRate());
                    st.setBytes(8, device.getPolicyFile());
                    st.setInt(9, Integer.parseInt(id));
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                catch (NumberFormatException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> deleteById(String table, String id) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement(String.format("DELETE FROM %s WHERE id = ?", table));
                    st.setInt(1, Integer.parseInt(id));
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                catch (NumberFormatException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> dropAllTables() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    st = conn.prepareStatement("DROP TABLE IF EXISTS alert, umbox, umbox_image device, device_history, group_id, type, tag");
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getById(String table, String id) throws Exception {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                ResultSet rs = null;
                JsonNode jn = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement(String.format("SELECT * FROM %s WHERE id = ?", table));
                    st.setInt(1, Integer.parseInt(id));
                    rs = st.executeQuery();
                    jn = Convertor.convertToJson(rs).get(0);
                }
                catch (SQLException e) {}
                catch (NumberFormatException e) {}
                catch (Exception e) {}
                finally {
                    try { if(rs != null) rs.close(); } catch (Exception e) {}
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return jn;
            });
        }, ec);
    }

    public CompletionStage<JsonNode> getAllFromTable(String table) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                ResultSet rs = null;
                JsonNode jn = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement(String.format("SELECT * FROM %s", table));
                    rs = st.executeQuery();
                    jn = Convertor.convertToJson(rs);
                }
                catch (SQLException e) {}
                catch (Exception e) {}
                finally {
                    try { if(rs != null) rs.close(); } catch (Exception e) {}
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return jn;
            });
        }, ec);
    }

    public CompletionStage<Integer> addRowToTable(String table, String name) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(conn -> {
                PreparedStatement st = null;
                try {
                    createTablesIfNotExists(conn);
                    st = conn.prepareStatement(String.format("INSERT INTO %s (name) VALUES (?)", table));
                    st.setString(1, name);
                    st.executeUpdate();
                }
                catch (SQLException e) {}
                finally {
                    try { if(st != null) st.close(); } catch (Exception e) {}
                    try { if(conn != null) conn.close(); } catch (Exception e) {}
                }
                return 1;
            });
        }, ec);
    }

}