import java.util.ArrayList;
import java.util.List;

public class BinaryGenerator {
    public static List<String> generateBinary(int n) {
        if (n == 0) {
            List<String> result = new ArrayList<>();
            result.add("");
            return result;
        }
        
        List<String> smaller = generateBinary(n - 1);
        List<String> result = new ArrayList<>();
        
        for (String s : smaller) {
            result.add(s + "0");
            result.add(s + "1");
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int n = 3;
        List<String> binaryStrings = generateBinary(n);
        
        System.out.println("Все бинарные строки длины " + n + ":");
        for (String s : binaryStrings) {
            System.out.println(s);
        }
    }
}
