//Двоичный поиск (Binary Search)
  
#include <iostream>
#include <vector>
using namespace std;

int binary_search(vector<int>& arr, int target) {
    int left = 0;
    int right = arr.size() - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}

int main() {
    vector<int> arr = {1, 3, 5, 7, 9, 11, 13, 15};
    int target = 7;
    int result = binary_search(arr, target);
    cout << "Элемент " << target << " найден на позиции: " << result << endl;
    return 0;
}

Поиск Фибоначчи (Fibonacci Search)
  
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int fibonacciSearch(vector<int>& arr, int target) {
    int n = arr.size();
    
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
        int i = min(offset + fibMMm2, n - 1);
        
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

int main() {
    vector<int> arr = {10, 22, 35, 40, 45, 50, 80, 82, 85, 90, 100};
    int target = 85;
    int result = fibonacciSearch(arr, target);
    cout << "Элемент " << target << " найден на позиции: " << result << endl;
    return 0;
}

Интерполяционный поиск (Interpolation Search)
  #include <iostream>
#include <vector>
using namespace std;

int interpolationSearch(vector<int>& arr, int target) {
    int low = 0;
    int high = arr.size() - 1;
    
    while (low <= high && target >= arr[low] && target <= arr[high]) {
        if (low == high) {
            if (arr[low] == target) return low;
            return -1;
        }
        
        // Формула интерполяции для определения позиции
        int pos = low + (((double)(high - low) / 
                         (arr[high] - arr[low])) * (target - arr[low]));
        
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

int main() {
    vector<int> arr = {10, 12, 13, 16, 18, 19, 20, 21, 22, 23, 24, 33, 35, 42, 47};
    int target = 18;
    int result = interpolationSearch(arr, target);
    cout << "Элемент " << target << " найден на позиции: " << result << endl;
    return 0;
}

