N-Gram
------
Run Instructions:
javac Driver.java
java Driver sample_text.txt


The purpose of this program is to analyze a piece of text and determine some of the most frequent and infrequent used words. These frequency counts are used in analyzing languagues and text classification.

The program reads in a piece of text (.txt) and extracts each word and their frequencies into a map, where each key is a word and its value its number of appearances in the text file. The map implemented is Java's HashMap.

The program will then output the 20 most frequent and in-frequent words and their percentages of appearance. The program will also demos a random haiku generated from a HashMap where each key is 4-words (4-gram). Although the program was intended to test 1-gram and 4-grams, the user can test out other numbers by changing the within Driver.java (see documentation).

