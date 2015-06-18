NOTES:

toolbox-x.x.x.pom must be kept in sync with the pom.xml

for dependencies which do not have a public repository, register them in the atf-toolbox internal repo
<!-- USE the following as an example to install any other 3rd party dependency that doesn't have a public maven -->
C:\Projects\workspace\atf-toolbox\toolbox>mvn install:install-file -DlocalRepositoryPath=repo -DcreateChecksum=true -Dpackaging=jar -Dfile=JSErrorCollector-0.6.jar-DgroupId=net.jsourcerer.webdriver -DartifactId=JSErrorCollector -Dversion=0.6

To build the project, open a command window at the pom.xml location and run: mvn package
This will run all goals, compile, tests, package

To install it into your local .m2 repo run: mvn install
