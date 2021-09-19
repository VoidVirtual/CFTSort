-------------------------------REQUIREMENTS-----------------------------
Java version 15 or greater
Maven version 3.8.2 or greater
---------------------------------BUILD----------------------------------

mvn clean package shade:shade

---------------------------------USAGE----------------------------------
Launch:
    cd /target
    java -jar sort.jar -<options> <outputFilePath> <inputFilePath1> ... <inputFilePathN>

Options:
    -s - string elements
    -i - integer elements
    -a - natural sort order
    -d - reverse sort order
Example:
  java -jar sort.jar -s -d out.txt in1.txt int.txt
