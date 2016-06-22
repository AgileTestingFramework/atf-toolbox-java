package com.agiletestingframework.toolbox.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletestingframework.toolbox.interfaces.DataDriver;

/**
 * Created by jorge on 8/18/15.
 */
public class CSVDataDriver implements DataDriver {
    private static Logger log = LoggerFactory.getLogger(CSVDataDriver.class);

    private String dataFolder;
    private String tableName;

    static
    {
        try {
            Class.forName("org.relique.jdbc.csv.CsvDriver");
        } catch (ClassNotFoundException e) {
            log.error("Unable to find the CSV driver", e);
        }
    }

    /**
     * Loads data from a CSV file or collection of CSV files
     * All the CSV files should reside in the same folder. Each file represents one test case with multiple scenarios.
     * The first row in the CSV file must contain the column names.
     * The first column in the csv file will be used to name the scenario, but the column will also be available for
     * the test.
     *
     * @param dataFolder The data folder where the CSV file is located. Every
     * @param tableName the CSV file name
     */
    public CSVDataDriver(String dataFolder, String tableName) {
        this.dataFolder = dataFolder;
        this.tableName = tableName;
    }

    @Override
    public TestCaseData load() {
        TestCaseData testCaseData = new TestCaseData(tableName);
        try (Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + dataFolder);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName) )
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                ScenarioData scenarioData = new ScenarioData(rs.getString(1), tableName);
                for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {

                    scenarioData.putScenarioData(rsmd.getColumnName(columnNumber), rs.getString(columnNumber));
                }
                testCaseData.addScenarioData(scenarioData);
            }
        }
        catch (SQLException se)
        {
            log.error("Unable to read the CSV file", se);
        }
        return testCaseData;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(String dataFolder) {
        this.dataFolder = dataFolder;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
