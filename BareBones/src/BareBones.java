
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class BareBones {

//  author: You Wu
//  try and come up with a decent object oriented design. Also beware of the nested while loop!


    // -------------------------------constructor part------------------------------------

    // fileName : stores file name (.txt) of the test case
    private String fileName;

    // stackIteration : the stack used for processing iteration,
    // stores the number of lines where iteration start.
    // stackPointer : pointer of stack.
    private Integer[] stackIteration;
    private Integer stackPointer;

    // linePointer : pointer for line of statement currently processing.
    private Integer linePointer;

    // instructionSet: array string for storing instructions.
    private String[] instructionSet;

    // codeLength: stores number of lines of the code.
    private Integer codeLength;

    // tokenMap: storing every token variable.
    private Map<String, Integer> tokenMap = new HashMap<String, Integer>();

    public BareBones (String inputFileName) {
        fileName = inputFileName;
        stackIteration = new Integer[100];

        stackPointer = -1;

        linePointer = codeLength = 0;
        instructionSet = new String[1000];


    }

    // -------------------------------initialization part------------------------------------

    public void Initiallize() {
        try {

            // read instruction from .txt file
            String lastInstruction;
            File fileHandler = new File(this.fileName);
            Scanner fileReader = new Scanner(fileHandler);

            while (fileReader.hasNextLine()) {
                lastInstruction = fileReader.nextLine();


                // ignore the indentation of the code.
                while (lastInstruction.substring(0, 3).equals("   ")) {
                    lastInstruction = lastInstruction.substring(3);
                }

                lastInstruction = lastInstruction.substring(0, lastInstruction.length() - 1);

                // import into the instruction set.

                Import(lastInstruction);

            }

            codeLength -= 1;

        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found.");
        }
    }

    public void Import(String inputInstruction) {

        // import the code into the array and self-increment the code length.
        this.instructionSet[this.codeLength] = inputInstruction;
        this.codeLength += 1;

        // check the new token, if not in the token list, then add the new token
        String[] temp = inputInstruction.split(" ");
        if (temp.length > 1) {
            if (tokenMap.containsKey(temp[1]) == false) {
                tokenMap.put(temp[1], 0);

            }
        }

    }

    // -------------------------------Execution part------------------------------------

    // clear instruction method
    public void Clear(String variable) {
        tokenMap.computeIfPresent(variable, (k, v) -> 0);
    }

    // incr instruction method
    public void Incr(String variable) {
        tokenMap.computeIfPresent(variable, (k, v) -> v + 1);
    }

    // decr instruction method
    public void Decr(String variable) {
        tokenMap.computeIfPresent(variable, (k, v) -> v - 1);
    }


    // Executing part
    public void Execute() {

        boolean done = false;

        while (!done) {

            // check the instructions all already done or not
            if (linePointer > codeLength) {
                done = true;

            } else {
                // decode (split) the instruction.
                String[] instructionRead = this.instructionSet[this.linePointer].split(" ");

                // execute the instruction.
                if (Objects.equals(instructionRead[0], "clear")) {
                    this.Clear(instructionRead[1]);
                    linePointer += 1;
                } else if (Objects.equals(instructionRead[0], "incr")) {
                    this.Incr(instructionRead[1]);
                    linePointer += 1;
                } else if (Objects.equals(instructionRead[0], "decr")) {
                    this.Decr(instructionRead[1]);
                    linePointer += 1;
                } else if (Objects.equals(instructionRead[0], "while")) { // if meets iteration:

                    // push the line number into the stack.
                    stackPointer += 1;
                    this.stackIteration[stackPointer] = linePointer;
                    linePointer += 1;

                } else if (Objects.equals(instructionRead[0], "end")) {

                    // pop out the line number of the stack.
                    Integer tempAddress = this.stackIteration[stackPointer];
                    stackPointer -= 1;

                    // check meets condition of iteration or not.
                    // if meets, jump back to the iteration.
                    if (tokenMap.get(this.instructionSet[tempAddress].split(" ")[1]) == 0) {
                        linePointer += 1;
                    } else {
                        linePointer = tempAddress;
                    }


                }

            }

        }

    }

    // -------------------------------Output part------------------------------------

    // iterating token map through for the loop
    public void OutputResult(){

        for (Map.Entry<String, Integer> set : tokenMap.entrySet()) {
            System.out.println(set.getKey() + " = " + set.getValue());
        }

    }


    public static void main(String[] args) {

        // testcase stored in the .txt file
        BareBones test = new BareBones("test2.txt");
        test.Initiallize();
        test.Execute();
        test.OutputResult();

    }
}