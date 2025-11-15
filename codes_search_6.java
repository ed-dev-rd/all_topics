Поиск Фибоначчи (Fibonacci Search)
  
import java.util.Arrays;

public class FibonacciSearch {
    
    public static int fibonacciSearch(int[] arr, int target) {
        int n = arr.length;
        
        // Инициализация чисел Фибоначчи
        int fibMMm2 = 0; // F(m-2)
        int fibMMm1 = 1; // F(m-1)
        int fibM = fibMMm2 + fibMMm1; // F(m)
        
        // Находим наименьшее число Фибоначчи, большее или равное n
        while (fibM < n) {
            fibMMm2 = fibMMm1;
            fibMMm1 = fibM;
            fibM = fibMMm2 + fibMMm1;
        }
        
        int offset = -1;
        
        while (fibM > 1) {
            int i = Math.min(offset + fibMMm2, n - 1);
            
            if (arr[i] < target) {
                fibM = fibMMm1;
                fibMMm1 = fibMMm2;
                fibMMm2 = fibM - fibMMm1;
                offset = i;
            } else if (arr[i] > target) {
                fibM = fibMMm2;
                fibMMm1 = fibMMm1 - fibMMm2;
                fibMMm2 = fibM - fibMMm1;
            } else {
                return i;
            }
        }
        
        if (fibMMm1 == 1 && arr[offset + 1] == target) {
            return offset + 1;
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        int[] arr = {10, 22, 35, 40, 45, 50, 80, 82, 85, 90, 100};
        int target = 85;
        int result = fibonacciSearch(arr, target);
        System.out.println("Элемент " + target + " найден на позиции: " + result);
    }
}

Интерполяционный поиск (Interpolation Search)

public class InterpolationSearch {
    
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high && target >= arr[low] && target <= arr[high]) {
            if (low == high) {
                if (arr[low] == target) return low;
                return -1;
            }
            
            // Формула интерполяции для определения позиции
            int pos = low + (((high - low) * 
                            (target - arr[low])) / (arr[high] - arr[low]));
            
            if (arr[pos] == target) {
                return pos;
            } else if (arr[pos] < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        int[] arr = {10, 12, 13, 16, 18, 19, 20, 21, 22, 23, 24, 33, 35, 42, 47};
        int target = 18;
        int result = interpolationSearch(arr, target);
        System.out.println("Элемент " + target + " найден на позиции: " + result);
    }
}

Линейный поиск (Linear Search)

public class LinearSearch {
    
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        int target = 7;
        int result = linearSearch(arr, target);
        
        if (result != -1) {
            System.out.println("Элемент " + target + " найден на позиции: " + result);
        } else {
            System.out.println("Элемент " + target + " не найден в массиве");
        }
    }
}

