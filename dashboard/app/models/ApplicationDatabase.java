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

    public CompletionStage<Integer> addUmbox(Umbox umbox) {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {

                Statement st = connection.createStatement();

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

                // Adds new umbox based on form values
                st.executeUpdate("INSERT INTO umbox(umbox_id,umbox_name,device,started_at)" +
                        "VALUES ('" +
                        umbox.umboxId + "','" +
                        umbox.umboxName + "','" +
                        umbox.device + "','" +
                        umbox.startedAt + "');"
                );

                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> dropAllTables() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                connection.prepareStatement("DROP TABLE IF EXISTS alert,umbox").execute();
                return 1;
            });
        }, ec);
    }

    public CompletionStage<Integer> logUmboxes() {
        return CompletableFuture.supplyAsync(() -> {
            return db.withConnection(connection -> {
                Statement st = connection.createStatement();

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