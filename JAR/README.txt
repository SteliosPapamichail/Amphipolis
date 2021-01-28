[ Execution Instructions ]

1. The project was built and compiled into a JAR using the Maven framework. 
You can simply run the following command from the command line
from within the directory that includes the "amphipolis.jar" file: "java -jar amphipolis.jar".

[ Build Instructions ]

If you'd like to build the project and create a new JAR file, simply follow the steps below:

 a) open the project using IntelliJ IDE (make sure you have maven installed)
 b) Navigate to "View->Tool windows->Maven", then expand the "Lifecycle" dropdown and run the "install" script.
 This will compile the project's .java files into .class files and aggregate them into a JAR along with any dependencies & resources.
 You can then navigate to the "target" dir within the project and execute the generated JAR file using the instructions above.
 c) Make sure that the MANIFEST.MF file is located under java/main/resources/META-INF/ and that it points to the Main class. Otherwise,
 if you want to change its location, don't forget to also alter the manifest's location in the pom.xml file.