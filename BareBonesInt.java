import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;

public class BareBonesInt {

    public static void main(String[] args) {

        ArrayDeque<String> program = new ArrayDeque<>(); // store entire program as a list of tokens
        HashMap<String, Integer> variables = new HashMap<String, Integer>(); // dictionary to track variables

        program = programReader(); // converts program to token list

        while (!program.isEmpty()) {
            executeCommand(program, variables); // runs through all the commands in the program
        }

        System.out.println("Final variable values = " + variables);
        System.exit(0); // leave program
    }

    private static ArrayDeque<String> programReader() {
        // reads the program from a text document

        ArrayDeque<String> program = new ArrayDeque<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("BareBonesCode2.txt"));
            String line = reader.readLine(); // read into a line string

            while (line != null) {

                ArrayDeque<String> tempProgram = new ArrayDeque<>();
                tempProgram = tokeniser(line); // splits a line into tokens

                int length = tempProgram.size();
                for (int i = 0; i < length; i = i + 1) {
                    program.addLast(tempProgram.getFirst()); // add a line's work of tokens to program list
                    tempProgram.removeFirst();
                }

                line = reader.readLine(); // reads a new line
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace(); // if all fails
        }

        return program;
    }

    private static ArrayDeque<java.lang.String> tokeniser(String line) {
        // turns a line of the text document into tokens

        ArrayDeque<String> tokenLine = new ArrayDeque<>();
        String token = "";

        for (int i=0; i< line.length(); i++) {
            if ((line.charAt(i) != ' ') && (line.charAt(i) != ';')) { // add a character to token when no whitespace or
                token = token.concat(String.valueOf(line.charAt(i)));
            }

            if (!token.isEmpty() && ((line.charAt(i) == ' ') || (line.charAt(i) == ';'))) { // if finished token, add to the toke list
                tokenLine.addLast(token);
                token = "";
            }
        }

        return tokenLine;
    }

    private static void executeCommand(ArrayDeque<String> program, HashMap<String, Integer> variables) {
        // run any commands and then remove from program

        String[] commands = new String[]{"clear", "incr", "decr", "while", "com", "add", "sub", "product", "divide"};
        Arrays.sort(commands);

        switch (program.getFirst()) {

            case "clear" -> {
                // set variable to zero/ initialise variable

                program.removeFirst();

                if (variables.get(program.getFirst()) == null) {
                    variables.put(program.getFirst(), 0);// add variable value back into the dictionary
                } else {
                    variables.remove(program.getFirst());
                    variables.put(program.getFirst(), 0);// add variable value back into the dictionary
                }

                program.removeFirst();

            }
            case "incr" -> {
                // increment 1
                program.removeFirst();

                int variable = variables.get(program.getFirst());
                variable++;

                variables.remove(program.getFirst());
                variables.put(program.getFirst(), variable);// add variable value back into the dictionary

                program.removeFirst();

            }
            case "decr" -> {
                // decrement 1

                program.removeFirst();

                int variable = variables.get(program.getFirst());
                variable--;

                variables.remove(program.getFirst());
                variables.put(program.getFirst(), variable);// add variable value back into the dictionary

                program.removeFirst();

            }
            case "com" -> {
                // comments

                System.out.println("Comment");

                program.removeFirst();

                while (Arrays.binarySearch(commands, program.getFirst()) < 0) { //checks it's not a command on a new line
                    program.removeFirst();
                }

            } case "add" -> {
                // add the second variable/number to the first

                program.removeFirst();

                int variable1Value = variables.get(program.getFirst()); // get and store details of variable we're modifying
                String variable1Name = program.getFirst();

                program.removeFirst();

                int variable2Value;

                try { // what to do if variable 2 is a variable or just an integer
                    variable2Value = Integer.parseInt(program.getFirst());
                } catch (NumberFormatException e) {
                    variable2Value = variables.get(program.getFirst());
                }

                variable1Value = variable1Value + variable2Value;

                variables.remove(variable1Name);
                variables.put(variable1Name, variable1Value); // add variable 1 value back into the dictionary

                program.removeFirst();

            } case "sub" -> {
                // add the second variable/number to the first

                program.removeFirst();

                int variable1Value = variables.get(program.getFirst()); // get and store details of variable we're modifying
                String variable1Name = program.getFirst();

                program.removeFirst();

                int variable2Value;

                try { // what to do if variable 2 is a variable or just an integer
                    variable2Value = Integer.parseInt(program.getFirst());
                } catch (NumberFormatException e) {
                    variable2Value = variables.get(program.getFirst());
                }

                variable1Value = variable1Value - variable2Value;

                variables.remove(variable1Name);
                variables.put(variable1Name, variable1Value); // add variable 1 value back into the dictionary

                program.removeFirst();

            } case "product" -> {
                // add the second variable/number to the first

                program.removeFirst();

                int variable1Value = variables.get(program.getFirst()); // get and store details of variable we're modifying
                String variable1Name = program.getFirst();

                program.removeFirst();

                int variable2Value;

                try { // what to do if variable 2 is a variable or just an integer
                    variable2Value = Integer.parseInt(program.getFirst());
                } catch (NumberFormatException e) {
                    variable2Value = variables.get(program.getFirst());
                }

                variable1Value = variable1Value * variable2Value;

                variables.remove(variable1Name);
                variables.put(variable1Name, variable1Value); // add variable 1 value back into the dictionary

                program.removeFirst();

            } case "divide" -> {
                // add the second variable/number to the first

                program.removeFirst();

                int variable1Value = variables.get(program.getFirst()); // get and store details of variable we're modifying
                String variable1Name = program.getFirst();

                program.removeFirst();

                int variable2Value;

                try { // what to do if variable 2 is a variable or just an integer
                    variable2Value = Integer.parseInt(program.getFirst());
                } catch (NumberFormatException e) {
                    variable2Value = variables.get(program.getFirst());
                }

                variable1Value = variable1Value / variable2Value;

                variables.remove(variable1Name);
                variables.put(variable1Name, variable1Value); // add variable 1 value back into the dictionary

                program.removeFirst();

            } case "while" -> {
                // while loops

                program.removeFirst();

                Boolean condition = false; // the condition of the while loop
                while (!condition) {

                    condition = whileLoop(program, variables);

                }

                while (!(program.getFirst().equals("end"))) { // end of while loop
                    program.removeFirst();
                }

                program.removeFirst();

            } case "not", "do", "then", "X", "0", "end" -> { // extra words for understanding/user

                program.removeFirst();

            }
            default -> throw new IllegalStateException("Unexpected value: " + program.getFirst()); // can't find command

        }
    }

    private static boolean whileLoop (ArrayDeque<String> program, HashMap<String, Integer> variables) {
        // running a while loop - recursive

        ArrayDeque<String> whileProgram = program.clone();

        String whileVariable = whileProgram.getFirst();
        for (int i = 0; i < 2; i++) {
            whileProgram.removeFirst();
        }

        int whileCondition = Integer.parseInt(whileProgram.getFirst()); //getting while loop
        whileProgram.removeFirst();

        while (!(whileProgram.getFirst().equals("end")) && (variables.get(whileVariable) != whileCondition)) { // check condition at the end of the while loop
            executeCommand(whileProgram, variables); // calling commands inside the while loop
        }

        return variables.get(whileVariable) == whileCondition; // returns if the while loop condition is met

    }

    private static void forLoop (ArrayDeque<String> program, HashMap<String, Integer> variables) {
        // running a while loop - recursive

        ArrayDeque<String> forProgram = program.clone();

        while (!(forProgram.getFirst().equals("end"))) { // run through the for loop
            executeCommand(forProgram, variables); // calling commands inside the while loop
        }

    }

}
