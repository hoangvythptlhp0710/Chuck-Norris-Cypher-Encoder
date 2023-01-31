import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChuckNorrisTest extends StageTest {
  Object[] test_data(){
    String ascii = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    List<String> list = new ArrayList<>(Arrays.asList(ascii.split("")));
    list.addAll(Arrays.asList("greetings!",
            "hello world!",
            ascii));
    return list.toArray();
  }

  @DynamicTest(data = "test_data")
  CheckResult test(String input) {
    TestedProgram pr = new TestedProgram();
    String output = pr.start().strip().toLowerCase();
    List<String> list = new ArrayList<>(Arrays.asList(output.split("\n")));
    list.removeAll(Arrays.asList(""));

    if(list.size()!=1 || !list.get(0).contains("input")){
      return CheckResult.wrong("When the program just started, output should contain exactly 1 non-empty line, " +
              "containing \"input\" substring as it shown in the example, followed by an input");
    }
    output = pr.execute(input);
    list = new ArrayList<>(Arrays.asList(output.split("\n")));
    list.removeAll(Arrays.asList(""));
    if(list.size()!=2){
      return CheckResult.wrong("When the user has entered a string, there should be printed exactly 2 " +
              "non-empty lines");
    }
    if(!list.get(0).toLowerCase().contains("result")){
      return CheckResult.wrong("When the user has entered a string, the first line of the output " +
              "should contain \"result\" substring");
    }

    //Correct code
    if(!list.get(1).matches("[ 0]*")){
      return CheckResult.wrong("When the user has entered a string, the second line of the output " +
              "should be a code, that contains only '0' and ' ' characters");
    }
    String code = "";
    String[] chars = list.get(1).strip().split(" ");
    if(chars.length%2!=0){
      return CheckResult.wrong("To produce a series of same value bits, should be used two consecutive blocks, so " +
              "printed code should contain even amount of blocks");
    }
    String was = chars[0].equals("00") ? "0" : "00";
    for(int i=0;i<chars.length/2;i++){
      if(!chars[i*2].equals("00") && !chars[i*2].equals("0")){
        return CheckResult.wrong("First block in each sequence of same value bits should be either \"00\" or \"0\"");
      }
      if(was.equals(chars[i*2])){
        return CheckResult.wrong("Encoding of a single character sequence should not be separated");
      }
      was = was.equals("00") ? "0" : "00";
      String type = chars[i*2].equals("00")?"0":"1";
      code = code.concat(type.repeat(chars[i*2+1].length()));
    }
    if(code.length()%7!=0){
      return CheckResult.wrong("Summary length of second blocks in each sequence of same value bits should be multiple"+
              " of 7, as the length of binary code is multiple of 7 (each character should be encoded as 7-bit string)");
    }
    String code_input = "";
    for (int i=0;i<input.length();i++){
      String result = Integer.toBinaryString(input.charAt(i));
      String resultWithPadding = String.format("%7s", result).replaceAll(" ","0");
      code_input = code_input.concat(resultWithPadding);
    }
    if(code_input.replace('0', '2').replace('1', '0').replace('2', '1').equals(code)){
      return CheckResult.wrong("Input string was not encoded correctly, in this case the reason might be that you've " +
              "encoded '0' sequence with first block \"0\" and '1' sequence with first block \"00\", so the code is " +
              "\"inverted\"");
    }
    if(!code_input.equals(code)){
      return CheckResult.wrong("Input string was not encoded correctly. Note, that the result should be the whole message" +
              " converted, and not the concatenation of 7-bit sequences, converted by the principle");
    }

    return CheckResult.correct();
  }
}