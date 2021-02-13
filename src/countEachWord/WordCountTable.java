package countEachWord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/*
 * This class takes in a input file 
 * return a sorted array in descending order for each unique wordCountMap
 */
public class WordCountTable {
	
	private static String filePath = "input.txt";
	private static String fileName = "";
	private static Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
	private static String fileContent = "";
		
	public static void main(String[] args) throws FileNotFoundException {
		
		//if there is argument passes in
		if (args.length > 0) {
			
			//get the first argument
			filePath = args[0];
			
		}else {
			
			System.out.println("Please pass in a path to input file as an argument");
			
		}
		
		//read the file put words and count into map  
		wordCountMap = fileToMap();
		
		//sort the hashMap 
		List<Entry<String, Integer>> sortedWordCountMap = sortHashMap();
		
		//upload the content to html file
		updateHTMLFile(sortedWordCountMap);
				
	}

	/*
	 * Given a HashMap<String,Integer> it sorts it based on the values
	 */
	private static  List<Entry<String, Integer>> sortHashMap() {
		
		//create a linked list out if the hashmap entries
		List<Entry<String,Integer>> wordCountList = new LinkedList<Entry<String, Integer>>(wordCountMap.entrySet());
				
		//sort list depending on the value, so the count of the words
		Collections.sort(wordCountList, new Comparator<Entry<String,Integer>>(){

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				
				//comparing the o2 value with o1 so we get the descending order
				return o2.getValue().compareTo(o1.getValue());
				
			}
			
		});

		return wordCountList;
		
	}
	
	/*
	 * This function returns a hashMap of unique wordCountMap and the number of their occurance 
	 * @param - filePath
	 * @return - Map<word, count>
	 */
	public static Map<String, Integer> fileToMap(){
		 
		try {
			
			//creates file object 
			File file = new File(filePath);
			
			//this is to extact the fileName
			fileName = file.getName();
			
			//creates scanner object to read file
			Scanner fileScanner = new Scanner(file);
			
			//while file is not empty
			while (fileScanner.hasNext()) {
				
				//file.next reads the next word in file
				String word = fileScanner.next();
				
				//append the word read to fileContent
				fileContent += " " + word;
				
				//this gets the number of times the word appears, from map 
				Integer wordCount = wordCountMap.get(word);
				
				//if the word has a associated count 
				if (wordCount != null) {
					
					//the wordCountMap has been read before, it is present in map, so the count increases by one
					wordCount++;
					
				}else {
					
					//the word has not been read before so its count set to 1 
					wordCount = 1;
					
				}
				
				//after updating the word count we update the hashMap 
				wordCountMap.put(word, wordCount);
				
			}
			
			//close the scanner since all no more wordCountMap left 
			fileScanner.close();

		}catch(FileNotFoundException error){
			
			System.out.println("An occurred while finding trying to read file.");
		    
			error.printStackTrace();
		    
		}
		
	    return wordCountMap;

	}
	
	/*
	 * this populates the HTML file
	 */
	private static void updateHTMLFile(List<Entry<String, Integer>> sortedWordCountMap) {
		
		File file = new File("report.html");
		
        String htmlData ="<html>\r\n"
        		+ "    <head>\r\n"
        		+ "        <title>Word Counter Output</title>\r\n"
        		+ "        <style>\r\n"
        		+ "            table, th, td {\r\n"
        		+ "                border:1px solid black; \r\n"
        		+ "                text-align: center;\r\n"
        		+ "            }\r\n"
        		+ "            .table-size {\r\n"
        		+ "                width: 500px;\r\n"
        		+ "            }\r\n"
        		+ "            .title-space {\r\n"
        		+ "                padding-bottom: 40px;\r\n"
        		+ "                font-size: 32px;\r\n"
        		+ "            }\r\n"
        		+ "            body{\r\n"
        		+ "              box-sizing: border-box;  \r\n"
        		+ "            }\r\n"
        		+ "        </style>\r\n"
        		+ "    </head>\r\n"
        		+ "    <body>\r\n"
        		+ "        <div>\r\n"
        		+ "            <h1 class=\"title-space\"> Input file name: "+ fileName +"</h1>\r\n"
        		+ "            <table style=\"margin-bottom: 40px; width: 100%;\">\r\n"
        		+ "                <tbody>\r\n"
        		+ "                    <tr>\r\n"
        		+ "                        <th>File Contents:</th>\r\n"
        		+ "                    </tr>\r\n"
        		+ "                    <tr>\r\n"
        		+ "                        <td>"+ fileContent+"</td>\r\n"
        		+ "                    </tr>\r\n"
        		+ "                </tbody>\r\n"
        		+ "            </table>\r\n"
        		+ "            <table class=\"table-size\">\r\n"
        		+ "                <tbody>\r\n"
        		+ "                    <tr>\r\n"
        		+ "                        <th>Word</th>\r\n"
        		+ "                        <th>Count</th>\r\n"
        		+ "                    </tr>\r\n"
        		+ "                    <tr>\r\n";
        
        for (Entry<String, Integer> count : sortedWordCountMap) {
        	
        	htmlData += "<tr>\r\n"
        			+ "    <td>"+ count.getKey() +"</td>\r\n"
        			+ "    <td>"+ count.getValue() +"</td>\r\n"
        			+ " </tr>";
     
        }

        htmlData += "</tbody>\r\n"
        		+ "            </table>\r\n"
        		+ "        </div>\r\n"
        		+ "    </body>\r\n"
        		+ "</html>";
        
		try {
			
			//creates a PrintWriter object to write to HTML file
			PrintWriter writer = new PrintWriter(file);
			
	        writer.write(htmlData);
	        
	        writer.close();
	        
		} catch (FileNotFoundException e) {
			
			System.out.println("An occurred while finding trying to write to file.");

			e.printStackTrace();
		}
		
	}
}
