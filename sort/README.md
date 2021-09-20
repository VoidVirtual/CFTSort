## DESCRIPTION

 A simple program that merges the finite number of sorted text files into one sorted file.
The sorted file is a file, containing the sorted sequence of elements.
The type of those elements can be string or integer, which must be specified by corresponding command-line option.(see USAGE -> Options)
The order of sort can be natural or reverse, which can be specified by corresponding command-line option.(see USAGE -> Options)
The order of sort is natural by default.
If an original file isn't sorted, the resulting file will be partial sorted.
If a file contains unparsable string (e.g: asfqq1as for integer), the string will be ignored and a warning will be logged.

## REQUIREMENTS

1. Java version 15 or greater
2. Maven version 3.8.2 or greater

## BUILD

mvn clean package shade:shade
    
## USAGE

Launch:
1. cd $projetDirPath/target
2. java -jar sort.jar -[option1] ... -[optionN] [outputFilePath] [inputFilePath1] ... [inputFilePathN]

Options:
    
-s     string elements
-i     integer elements
-a     natural sort order
-d     reverse sort order
    
Example:
        
java -jar sort.jar -s -d out.txt in1.txt in2.txt
    
File format:
    
The values should be separated by the new line symbol. E.g:
 A
 B
 C

    
