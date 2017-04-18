package com.agiletestingframework.toolbox.managers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.zatarox.squiggle.SelectQuery;

public class DatabaseAutomationManager {
    private static Logger log = LoggerFactory.getLogger(DatabaseAutomationManager.class);

    private Map<String, BasicDataSource> databaseSources;

    public static final String PRIMARY_DATASOURCE_KEY = "Primary";

    /**
     * DatabaseAutomationManager Creates primary DataSource and adds it to the
     * DataSource Collection
     */
    public DatabaseAutomationManager() {
        log.info("Initializing the DatabaseAutomationManager.");
        databaseSources = new HashMap<String, BasicDataSource>();
        databaseSources.put(PRIMARY_DATASOURCE_KEY, createPrimaryDataSource());
    }

    /**
     * AddDatabaseService Adds a Database Service to the collection with the key
     * provided If the key already exists, the Database Service will be replaced
     * within the collection
     *
     * @param key
     *            the Database Service in the collection
     * @param dataSource
     *            instance of DatabaseService
     */
    public void addDatabaseService(String key, BasicDataSource dataSource) {
        if (databaseSources.containsKey(key)) {
            log.info("Replaced database service for key :" + key);
            databaseSources.put(key, dataSource);
        } else {
            log.info("Added database service with key: " + key);
            databaseSources.put(key, dataSource);
        }
    }

    /**
     * RemoveDatabaseService
     *
     * @param key
     *            key to locate the Database Service to remove from the
     *            collection
     */
    public void removeDatabaseService(String key) {
        if (databaseSources.containsKey(key)) {
            try {
                databaseSources.remove(key);
                log.info("Successfully removed database service : " + key);
            } catch (Exception ex) {
                log.info("Unable to remove database service: " + key, ex);
            }
        } else {
            log.info("Unable to remove database service. No database service found with key : " + key);
        }
    }

    /**
     * getDatabaseSource
     *
     * @param key
     *            used to locate the database source
     * @return the BasicDataSource located for the key provided
     */
    public BasicDataSource getDatabaseSource(String key) {
        if (databaseSources.containsKey(key)) {
            return databaseSources.get(key);
        } else {
            log.warn("Unable to locate Database Service for key: " + key + " returning null.");
            return null;
        }
    }

    /**
     * getPrimaryDatabaseSource
     *
     * @return the Primary BasicDataSource
     */
    public BasicDataSource getPrimaryDatabaseSource() {
        return getDatabaseSource(PRIMARY_DATASOURCE_KEY);
    }

    /**
     * SelectStatementColumnBuilder
     *
     * @param columnsToReturn
     *            columns to return from a select statement
     * @return list of columns to return
     */
    public String selectStatementColumnBuilder(String[] columnsToReturn) {
        if (columnsToReturn != null) {
            StringBuilder columns = new StringBuilder();
            for (String columnName : columnsToReturn) {
                if (columns.toString() != "")
                    columns.append(", ");
                columns.append(columnName);
            }
            return columns.toString();
        }
        return "*";
    }

    /**
     * createPrimaryDataSource
     *
     * @return BasicDataSource based on the configuration
     */
    private BasicDataSource createPrimaryDataSource() {
        return createBasicDataSource(ConfigurationManager.getInstance().getDatabaseDriver(), ConfigurationManager.getInstance().getDatabaseUser(),
                ConfigurationManager.getInstance().getDatabasePassword(), ConfigurationManager.getInstance().getDatabaseUrl());
    }

    /**
     * createBasicDataSource
     *
     * @param dbDriver
     *            database driver
     * @param dbUser
     *            database user
     * @param dbPwd
     *            database password
     * @param dbURL
     *            database url
     * @return BasicDataSource based on parameter inputs
     */
    public BasicDataSource createBasicDataSource(String dbDriver, String dbUser, String dbPwd, String dbURL) {
        BasicDataSource d = new BasicDataSource();
        d.setDriverClassName(dbDriver);
        d.setUsername(dbUser);
        d.setPassword(dbPwd);
        d.setUrl(dbURL);

        try {
            Class.forName(ConfigurationManager.getInstance().getDatabaseDriver());
        } catch (ClassNotFoundException e) {
            log.error("Unable to initialize database driver.");
        }

        return d;
    }

    /**
     * selectData Will use the PRIMARY datasource
     *
     * @param selectStmnt
     *            squiggle sql select query
     *            (https://github.com/gchauvet/squiggle-sql)
     * @return ResultSet of the statement execution
     */
    public List<Map<String, Object>> selectData(SelectQuery selectStmnt) {
        return selectData(PRIMARY_DATASOURCE_KEY, selectStmnt);
    }

    /**
     * selectData Will use the PRIMARY datasource
     *
     * @param selectStmnt
     *            string representation of the sql statement to execute
     * @return ResultSet of the statement execution
     */
    public List<Map<String, Object>> selectData(String selectStmnt) {
        return selectData(PRIMARY_DATASOURCE_KEY, selectStmnt);
    }

    /**
     * selectData
     *
     * @param dataSourceKey
     *            the key to locate the datasource to use
     * @param selectStmnt
     *            squiggle sql select query
     *            (https://github.com/gchauvet/squiggle-sql)
     * @return ResultSet of the statement execution
     */
    public List<Map<String, Object>> selectData(String dataSourceKey, SelectQuery selectStmnt) {
        return selectData(dataSourceKey, selectStmnt.toString());
    }

    /**
     * selectData
     *
     * @param dataSourceKey
     *            the key to locate the datasource to use
     * @param selectStmnt
     *            string representation of the sql statement to execute
     * @return Result set of the statement execution
     */
    public List<Map<String, Object>> selectData(String dataSourceKey, String selectStmnt) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> row = null;

        try {
            BasicDataSource dataSource = getDatabaseSource(dataSourceKey);

            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectStmnt.toString());

            ResultSetMetaData metaData = resultSet.getMetaData();
            Integer columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                resultList.add(row);
            }
        } catch (SQLException sqlex) {
            log.error("Unable to execute query.", sqlex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error("Unable to close connection after select Data.", e);
            }
        }

        return resultList;
    }

    /**
     * getTables Primary datasource will be used
     *
     * @return a list of tables
     */
    public List<String> getTables() {
        return getTables(PRIMARY_DATASOURCE_KEY);
    }

    /**
     * getTables
     *
     * @param datasourceKey
     *            datasource to use
     * @return a list of tables
     */
    public List<String> getTables(String datasourceKey) {
        List<String> tables = new ArrayList<String>();

        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        String[] types = { "TABLE" };

        BasicDataSource dataSource = getDatabaseSource(datasourceKey);

        ResultSet result = null;
        Connection con = null;
        try {
            con = dataSource.getConnection();
            result = con.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, types);

            while (result.next()) {
                tables.add(result.getString(3));
            }
        } catch (Exception se) {
            log.error("Unable to retrieve tables from datasource: " + datasourceKey, se);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Unable to close connection after select Data.", e);
            }
        }
        return tables;
    }

    /**
     * getPrimaryKeyForTable
     *
     * @param tableName
     *            name of table to retrieve primary key
     * @return name of primary key
     */
    public String getPrimaryKeyForTable(String tableName) {
        return getPrimaryKeyForTable(PRIMARY_DATASOURCE_KEY, tableName);
    }

    /**
     * getPrimaryKeyForTable
     *
     * @param datasourceKey
     *            datasource to use
     * @param tableName
     *            name of table to retrieve primary key
     * @return name of primary key
     */
    public String getPrimaryKeyForTable(String datasourceKey, String tableName) {
        String primaryKey = "";
        Connection con = null;
        ResultSet result = null;
        String catalog = null;
        String schema = null;

        try {
            BasicDataSource dataSource = getDatabaseSource(datasourceKey);
            con = dataSource.getConnection();
            result = con.getMetaData().getPrimaryKeys(catalog, schema, tableName);

            while (result.next()) {
                primaryKey = result.getString(4);
            }
        } catch (SQLException se) {
            log.error("Unable to retrieve primary key for table: " + tableName, se);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Unable to close connection after select Data.", e);
            }
        }
        return primaryKey;
    }

    /**
     * getColumnsAndTypes Will use primary datasource
     *
     * @param tableName
     *            name of table to retrieve columns and data types
     * @return list of String column name and Types data type
     */
    public Map<String, Integer> getColumnsAndTypes(String tableName) {
        return getColumnsAndTypes(PRIMARY_DATASOURCE_KEY, tableName);
    }

    /**
     * getColumnsAndTypes
     *
     * @param dataSourceKey
     *            datasource to use
     * @param tableName
     *            name of table to retrieve columns and data types
     * @return list of String column name and Types data type
     */
    public Map<String, Integer> getColumnsAndTypes(String dataSourceKey, String tableName) {
        String catalog = null;
        String schemaPattern = null;
        String tableNamePattern = tableName;
        String columnNamePattern = null;

        Map<String, Integer> returnValues = new HashMap<>();

        try {
            BasicDataSource dataSource = getDatabaseSource(dataSourceKey);

            DatabaseMetaData databaseMetaData;

            databaseMetaData = dataSource.getConnection().getMetaData();

            ResultSet result = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);

            while (result.next()) {
                returnValues.put(result.getString(4), result.getInt(5));
            }

        } catch (SQLException e) {
            log.error("Unable to get columns and types for datasource:" + dataSourceKey + " and table:" + tableName);
        }

        return returnValues;
    }

    /**
     * teardown
     */
    public void teardown() {

    }
}
