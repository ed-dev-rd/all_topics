Типовая задача со структурой данных «двусвязный список» на ЯП C++
#include <iostream>
using namespace std;

// Узел списка с целочисленным значением
struct Node {
    int value;
    Node* prev;
    Node* next;
    
    Node(int val) : value(val), prev(nullptr), next(nullptr) {}
};

// Класс для управления двусвязным списком
class DoublyLinkedList {
private:
    Node* head;
    Node* tail;
    
public:
    DoublyLinkedList() : head(nullptr), tail(nullptr) {}
    
    // Добавление элемента в конец списка
    void append(int value) {
        Node* newNode = new Node(value);
        if (!head) {
            head = tail = newNode;
        } else {
            tail->next = newNode;
            newNode->prev = tail;
            tail = newNode;
        }
    }
    
    // Вывод элементов от начала к концу
    void displayForward() {
        Node* current = head;
        while (current) {
            cout << current->value << " ";
            current = current->next;
        }
        cout << endl;
    }
    
    // Вывод элементов от конца к началу
    void displayBackward() {
        Node* current = tail;
        while (current) {
            cout << current->value << " ";
            current = current->prev;
        }
        cout << endl;
    }
};

// Демонстрация работы списка
int main() {
    DoublyLinkedList list;
    list.append(10);
    list.append(20);
    list.append(30);
    
    cout << "Прямой обход: ";
    list.displayForward();    // 10 20 30
    
    cout << "Обратный обход: ";
    list.displayBackward();   // 30 20 10
    
    return 0;
}
