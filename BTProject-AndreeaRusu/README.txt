WINDOWS:
Compile the code:
cd BTProject-AndreeaRusu
javac -cp lib\junit-4.12.jar -d classes src\project\*.java src\testing\*java

Run the code:
java -cp classes project.FindValidRouters path\filename.csv

Run JUnitTests:
java -cp classes;lib\junit-4.12.jar;lib\hamcrest-core-1.3.jar org.junit.runner.JUnitCore testing.CSVReaderTest
