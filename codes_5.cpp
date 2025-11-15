#include <iostream>
#include <vector>
#include <string>
using namespace std;

vector<string> generateBinary(int n) {
    if (n == 0) {
        return {""};
    }
    
    vector<string> smaller = generateBinary(n - 1);
    vector<string> result;
    
    for (string s : smaller) {
        result.push_back(s + "0");
        result.push_back(s + "1");
    }
    
    return result;
}

int main() {
    int n = 3;
    vector<string> binaryStrings = generateBinary(n);
    
    cout << "Все бинарные строки длины " << n << ":" << endl;
    for (string s : binaryStrings) {
        cout << s << endl;
    }
    
    return 0;
}
