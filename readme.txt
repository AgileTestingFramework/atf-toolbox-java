NOTES:

toolbox-x.x.x.pom must be kept in sync with the pom.xml

for dependencies which do not have a public repository, register them in the atf-toolbox internal repo
<!-- USE the following as an example to install any other 3rd party dependency that doesn't have a public maven -->
C:\Projects\workspace\atf-toolbox\toolbox>mvn install:install-file -DlocalRepositoryPath=repo -DcreateChecksum=true -Dpackaging=jar -Dfile=JSErrorCollector-0.6.jar-DgroupId=net.jsourcerer.webdriver -DartifactId=JSErrorCollector -Dversion=0.6

To build the project, open a command window at the pom.xml location and run: mvn package
This will run all goals, compile, tests, package

To install it into your local .m2 repo run: mvn install

JMETER Setup
1. Install apache jmeter
2. Install the jmeter plugin dependencies
a) copy the following groovy-all jar to the jmeter\lib\ folder - jar can be downloaded at: https://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.4.3/groovy-all-2.4.3.jar
b) unzip the following jars to the jmeter\lib\ folder - zip file can be downloaded at: http://jmeter-plugins.org/downloads/file/JMeterPlugins-ExtrasLibs-1.3.0.zip

Note: If running windows, be sure to start JMeter IDE as Administrator or the GUI doesn't work.