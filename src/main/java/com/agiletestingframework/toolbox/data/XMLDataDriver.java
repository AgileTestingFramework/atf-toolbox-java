package com.agiletestingframework.toolbox.data;

import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletestingframework.toolbox.interfaces.DataDriver;

public class XMLDataDriver implements DataDriver {
    private static Logger log = LoggerFactory.getLogger(XMLDataDriver.class);

    private String xmlFileName;
    private String name;
    private Document xmlDocument;

    /**
     * XMLDataDriver Loads data from an XML file
     * 
     * @param fileName
     *            Name of the XML test data file to locate the test case
     *            scenario data
     * @param testCaseId
     *            id to locate in XML test case section in the test data file
     */
    public XMLDataDriver(String fileName, String testCaseId) {
        xmlFileName = fileName;
        name = testCaseId;

        initializeXMLForLoad();
    }

    private void initializeXMLForLoad() {
        try {
            SAXBuilder jdomBuilder = new SAXBuilder();
            xmlDocument = jdomBuilder.build(XMLDataDriver.class.getResourceAsStream("/" + xmlFileName));

        } catch (JDOMException jdomEx) {
            log.error("Unable to parse test data file: " + xmlFileName, jdomEx);
        } catch (IOException ioEx) {
            log.error("Unable to find test data file: " + xmlFileName + " to parse.", ioEx);
        }

    }

    @Override
    public TestCaseData load() {
        TestCaseData tcData = new TestCaseData();

        XPathFactory xFactory = XPathFactory.instance();

        Element testCase = xFactory.compile("//testcase[@name='" + name + "']", Filters.element()).evaluateFirst(xmlDocument);

        List<Element> scenarios = testCase.getChildren();

        for (Element scenario : scenarios) {
            List<Element> parameters = scenario.getChildren();
            ScenarioData testScenario = new ScenarioData(scenario.getName(), testCase.getName());

            for (Element parameter : parameters) {
                testScenario.putScenarioData(parameter.getName(), parameter.getValue());
            }

            tcData.addScenarioData(testScenario);
        }

        return tcData;
    }

}
