package com.agiletestingframework.toolbox.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletestingframework.toolbox.interfaces.DataDriver;

import java.sql.*;

/**
 * Created by jorge on 8/18/15.
 */
public class ExcelDataDriver implements DataDriver {
    private static Logger log = LoggerFactory.getLogger(ExcelDataDriver.class);

    private String fileName;
    private String sheetName;

    static {
        try {
            Class.forName("com.googlecode.sqlsheet.Driver");
        } catch (ClassNotFoundException e) {
            log.error("Unable to find the Excel driver", e);
        }
    }

    /**
     * Loads data from an Excel SpreadSheet Each sheet in the file represents
     * one test case with multiple scenarios. The first row in the sheet must
     * contain the column names. The first column in the sheet will be used to
     * name the scenario, but the column will also be available for the test.
     *
     * @param fileName
     *            The spreadsheet file name, including extension
     * @param sheetName
     *            the sheet containing the test cases. The sheet name must not
     *            contain spaces.
     */
    public ExcelDataDriver(String fileName, String sheetName) {
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    @Override
    public TestCaseData load() {
        TestCaseData testCaseData = new TestCaseData(sheetName);
        try (Connection conn = DriverManager.getConnection("jdbc:xls:file:" + fileName);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + sheetName)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                ScenarioData scenarioData = new ScenarioData(rs.getString(1), sheetName);
                for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
                    scenarioData.putScenarioData(rsmd.getColumnName(columnNumber), rs.getString(columnNumber));
                }
                testCaseData.addScenarioData(scenarioData);
            }
        } catch (SQLException se) {
            log.error("Unable to read the Excel spreadsheet file", se);
        }
        return testCaseData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

}
