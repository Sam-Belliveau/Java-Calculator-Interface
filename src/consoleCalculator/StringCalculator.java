package consoleCalculator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class StringCalculator {

	public static boolean isDeg = false;
	
	public static final boolean debug = true;
	
	public static String steps;
	
	public static String getSteps(){
		System.out.println(steps);
		return steps;
	}
	
	public static final String[] op = {	"(", ")",
										"+", "-", "*", "/", 
										"^", "%", 
										"sqrt", "root", 
										"sin", "cos"," tan", 
										"abs", "log", "ln", 
										"pi", "e"};
	
	public static String solveString(String input, JTextArea textArea, int Iindent){
		ArrayList<String> problem = splitInput(input);
		
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		
		String steps = "";
		String output = "";

		int indent = Iindent;
		
		try{
			/* Constants */
			boolean done = false;
			while(!done){
				
				done = true;
				
				for(int i = 0; i < problem.size(); i++){
					
					switch(problem.get(i)){
					
					case "e":
						done = false;
						problem.set(i, "2.718281828459045");
						break;
					
					case "pi":
						done = false;
						problem.set(i, "3.141592653589793");
						break;
						
					default :
						break;
					}
					
					if(!done){
					    lookForBlanks(problem, i);
					    break;
				    }
				}
			}
			
			/* Negatives */
			done = false;
			while(!done){
				
				done = true;
				// If First Thing Is Negative
			    if(problem.get(0).equals("-")) {
			    	done = false;
			    	problem.set(0, Double.toString(0-Double.parseDouble(problem.get(1))));
	    			problem.remove(1);
			    }
			    // If other things are negative
			    for(int i = 1; i < problem.size()-1; i++){
			        if(problem.get(i).equals("-")){
				        try{
				            Double.parseDouble(problem.get(i-1));
				        } 
				        // If Number before can not be parsed, it is negative
				        catch (NumberFormatException e) {
				        	done = false;
				        	
				        	/* Sets number to negative and removes other number */
				        	problem.set(i, Double.toString(0-Double.parseDouble(problem.get(i+1))));
	    					problem.remove(i+1);
				        }
			        }
			    }
			}
			
			if(debug){
				for(int z = 0; z < indent; z++){
					System.out.print("--");
				}
				System.out.print("Start: ");
				System.out.println(returnList(problem));
				for(int z = 0; z < indent; z++){
					System.out.print("--");
				}System.out.println();
				steps.concat(returnList(problem) + System.lineSeparator());
			}
			
			/* Brackets */
			done = false;
			while(!done){
				
				done = true;
				int indexStart = 0; // Starting and stopping brackets
				int indexEnd = 0;
				boolean useSection = false; // if bracket was found
				String section = ""; // Used to solve what's in brackets
				
				int pCounter = 0; // Counts up and down, when at 0, it fount matching bracket
				for(int i = 0; i < problem.size(); i++){
					if(problem.get(i).equals("(")){ // Found First Bracket
						if(pCounter == 0){
							indexStart = i;
						} pCounter++;
					}
					
					if(problem.get(i).equals(")")){
						pCounter--;
						if(pCounter == 0){ // Found Matching Bracket
							indexEnd = i;
							useSection = true;
							break;
						}
					}
				}
				/* If Brackets Are Found */
				if(useSection){ 
					done = false;
					indent++;
					if(debug){
						for(int z = 0; z < indent; z++){
							System.out.print("==");	
						}
						System.out.println("START BRACKET=");
					}
					/* Combine The List From Two Points */
					for(int index = indexStart+1; index < indexEnd; index++){
						section += problem.get(index);
					} problem.set(indexStart, solveString(section, textArea, indent));
					
					/* Remove Left Over Items */
					for(int i = 0; i < indexEnd-indexStart; i++){
						problem.remove(indexStart+1);
					}
					
					lookForBlanks(problem, indexStart);
					
					if(debug){
						for(int z = 0; z < indent; z++){
							System.out.print("==");
						}
						System.out.println("END BRACKET===");
						indent--;
						for(int z = 0; z < indent; z++){
							System.out.print("--");
						}
						System.out.print("Start: ");
						System.out.println(returnList(problem));
						for(int z = 0; z < indent; z++){
							System.out.print("--");
						}System.out.println();
						steps.concat(returnList(problem) + System.lineSeparator());
					}
				}
			}

			
			/* Single Number Operators */
			done = false;
			while(!done){
				
				done = true;
				
				for(int i = 0; i < problem.size(); i++){
					switch(problem.get(i)){
					
					/* Solves and then replaces */
					case "sqrt": // Square Root
						done = false;
					    problem.set(i, Double.toString(
					    		Math.sqrt(Double.parseDouble(problem.get(i+1))
					    				)));
						problem.remove(i+1);
						break;
					
					case "sin":
						done = false;
						if (isDeg){
							problem.set(i, Double.toString(
									Math.sin(Math.toRadians(Double.parseDouble(problem.get(i+1)))
											)));
						} else {
							problem.set(i, Double.toString(
									Math.sin(Double.parseDouble(problem.get(i+1))
											)));
						}
						problem.remove(i+1);
						break;
					
					case "cos":
						done = false;
						if (isDeg){
							problem.set(i, Double.toString(
									Math.cos(Math.toRadians(Double.parseDouble(problem.get(i+1)))
											)));
						} else {
							problem.set(i, Double.toString(
									Math.cos(Double.parseDouble(problem.get(i+1))
											)));
						}
						problem.remove(i+1);
						break;
					
					case "tan":
						done = false;
						if (isDeg){
							problem.set(i, Double.toString(
									Math.tan(Math.toRadians(Double.parseDouble(problem.get(i+1)))
											)));
						} else {
							problem.set(i, Double.toString(
									Math.tan(Double.parseDouble(problem.get(i+1))
											)));
						}
						problem.remove(i+1);
						break;
					
					case "ln":
						done = false;
					    problem.set(i, Double.toString(
					    		Math.log(Double.parseDouble(problem.get(i+1)))
					    		));
						problem.remove(i+1);
						break;
					
					case "log":
						done = false;
					    problem.set(i, Double.toString(
					    		Math.log10(Double.parseDouble(problem.get(i+1)))
					    		));
						problem.remove(i+1);
						break;
						
					case "abs":
						done = false;
					    problem.set(i, Double.toString(
					    		Math.abs(Double.parseDouble(problem.get(i+1)))
					    		));
						problem.remove(i+1);
						break;
					
					default :
						break;
					}
					if(!done){ // looks for spots with no op and replaces with multiplication
						lookForBlanks(problem, i);
						if(debug){
							for(int z = 0; z < indent; z++){
								System.out.print("--");
							}
							System.out.println(returnList(problem));
							steps.concat(returnList(problem) + System.lineSeparator());
						}
					    break;
					}
				}
			}
			
			/* Mult-Number Operators That Come Before *, /, ect */
			done = false;
			while(!done){
				
				done = true; 
				
				for(int i = 0; i < problem.size(); i++){
					switch(problem.get(i)){
					
					case "root": // Rounds Root to 10 Digits To Prevent bugs
						done = false;
						problem.set(i, Double.toString(
								(Math.round(
										Math.pow(Double.parseDouble(problem.get(i+1)), (1/Double.parseDouble(problem.get(i-1)))
												)*Math.pow(10, 10)))/Math.pow(10, 10))); // Rounds The Number
						removeSurroundingItems(problem, i);
						break;
					
					case "^":
						done = false;
						problem.set(i, Double.toString(
								Math.pow(Double.parseDouble(problem.get(i-1)), Double.parseDouble(problem.get(i+1))
										)));
						removeSurroundingItems(problem, i);
						break;
	
					
					default :
						break;
					}
					if(!done){
						if(debug){
							for(int z = 0; z < indent; z++){
								System.out.print("--");
							}
							System.out.println(returnList(problem));
							steps.concat(returnList(problem) + System.lineSeparator());
						}
						break;
					}
				}
			}
			
			/* MULTIPLYING DEVIDING AND MOD */
			done = false;
			while(!done){
				
				done = true;
				/*** MULTIPLY/DIVIDE ***/
				for(int i = 0; i < problem.size(); i++){
					switch(problem.get(i)){
					
					case "*":
						problem.set(i, Double.toString(
								Double.parseDouble(problem.get(i-1))*Double.parseDouble(problem.get(i+1)
										)));
						done = false;
						removeSurroundingItems(problem, i);
						break;
					
					case "/":
						problem.set(i, Double.toString(
								Double.parseDouble(problem.get(i-1))/Double.parseDouble(problem.get(i+1)
										)));
						done = false;
						removeSurroundingItems(problem, i);
						break;
						
					case "%":
						problem.set(i, Double.toString(
								Double.parseDouble(problem.get(i-1))%Double.parseDouble(problem.get(i+1)
										)));
						done = false;
						removeSurroundingItems(problem, i);
						break;
					
					default :
						break;
					}
					if(!done){
						if(debug){
							for(int z = 0; z < indent; z++){
								System.out.print("--");
							}
							System.out.println(returnList(problem));
							steps.concat(returnList(problem) + System.lineSeparator());
						}
						break;
					}
				}
			}
			
			/* ADD/SUBTRACT */
			done = false;
			while(!done){
				
				done = true;
				for(int i = 1; i < problem.size(); i++){
					switch(problem.get(i)){
					
					case "+":
						problem.set(i, Double.toString(
								Double.parseDouble(problem.get(i-1))+Double.parseDouble(problem.get(i+1)
										)));
						done = false;
						removeSurroundingItems(problem, i);
						break;
					
					case "-":
						problem.set(i, Double.toString(
								Double.parseDouble(problem.get(i-1))-Double.parseDouble(problem.get(i+1)
										)));
						done = false;
						removeSurroundingItems(problem, i);
						break;
						
					default :
						break;
					}
					if(!done){
						if(debug){
							for(int z = 0; z < indent; z++){
								System.out.print("--");
							}
							System.out.println(returnList(problem));
							steps.concat(returnList(problem) + System.lineSeparator());
						}
						break;
					}
				}
			}
			
			/* Return Answer */
			for(int i = 0; i < problem.size(); i++){
				if(i != 0){
					output += ", ";
				}
				output += problem.get(i);
			}
			
		} catch (Exception e){
			output = e + "";
		}
		

		for(int z = 0; z < indent; z++){
			System.out.print("--");
		}System.out.println();
		for(int z = 0; z < indent; z++){
			System.out.print("--");
		}
		System.out.println("RESULT: " + output);
		
		return output;
	}
		
	public static ArrayList<String> splitInput(String input){
		ArrayList<String> outputList = new ArrayList<String>();
		
		String item = null; // Adds what is found to this, then adds this to list
		input += " "; // Prevents out of bounds
		boolean numFound = false; // Used to execute code if a number is found.
		
		for(int index = 0; index < input.length()-1; index++){
			/* Reseting Vars */
			item = "";
			numFound = false;
			
			/* Looks For Numbers */
			while(index < input.length()){
				if(isNum(input.charAt(index)) || input.charAt(index) == '.'){
					item += String.valueOf(input.charAt(index));
					index++;
					numFound = true;
				} else if (numFound) { 
					index--; 
					outputList.add(item); 
					break; 
				} else { break; }
			}
			
			/* Looks For OPs */
			for(int o = 0; o < op.length; o++){
				for(int oIndex = 0; oIndex < op[o].length(); oIndex++){
					
					//System.out.println(input + ", " + index);
					if(input.charAt(index) == op[o].charAt(oIndex)){
						
						item += String.valueOf(op[o].charAt(oIndex));
						
						if(!(oIndex < op[o].length()-1)) { 
							o = op.length; // Breaks Nested Loop
							outputList.add(item); 
							break; 
						}
						index++;
					} else {
						item = "";
						index -= oIndex; // Goes back a certain amount if it could not find whole operator
						break;
					}
				}
			}/*
			String output = "";
			for(int i = 0; i < outputList.size(); i++){
				output += outputList.get(i);
			}
			System.out.println(output);
			*/
		}
		
		return outputList;
	}

	private static String returnList(ArrayList<String> problem){
		String out = "";
		for(int i = 0; i < problem.size(); i++){
			out += problem.get(i) + " ";
		}
		
		return out;
	}
	
	private static void removeSurroundingItems(ArrayList<String> problem, int index){
		problem.remove(index+1);
		problem.remove(index-1);
		index -= 2;
	}
	
	private static void lookForBlanks(ArrayList<String> problem, int index){
		try {
			if(!(isOP(problem.get(index+1)))){
				problem.add(index+1, "*");
			}
		} catch (Exception e) {} 
		try {
			if (!(isOP(problem.get(index-1)))){
				problem.add(index, "*");
			}
		} catch (Exception e) {} 
	}
	
	private static boolean isNum(char num){
		for(int i = 0; i < 10; i++){
			if (num == Character.forDigit(i, 10)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isOP(String input){
		for(int o = 0; o < op.length; o++){
			if (input.equals(op[o])){
				return true;
			}
		}
		return false;
	}
} class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
