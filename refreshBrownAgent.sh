 #!/bin/bash
 # compiles the contents of the naive folder using the
 # adx-agnet.1.0.2.jar file
 # then compresses the naive folder into a jar
 # then replaces the brownAgent.jar file in lib
 #
 # NOTE: If you're having issues, try making sure that brownAgent.jar is included
 in the project build/path in eclipse, try making sure that the project name in 
 the agent file is correct, try making sure that the configuration file is... 
 configured, try making sure that the name you're giving your agent in the 
 configuration file is registered with the server

cd /Users/Brendan/git/tacadx/tacadx

javac -cp "lib/adx-1.0.3.jar" $(find src -name \*.java -print)

cd src

jar -cf brownAgent.jar $(find brown -name \*.class -print) $(find tau -name \*.class -print)

mv brownAgent.jar /Users/Brendan/desktop/adx/adx-agnet-1.0.2/lib/brownAgent.jar

echo "done"
