Работа со списком
import java.util.ArrayList;
import java.util.Collections;

public class ArrayListExample {
    public static void main(String[] args) {
        // Создание ArrayList
        ArrayList<String> fruits = new ArrayList<>();
        
        // Добавление элементов
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Grape");
        
        // Вывод исходного списка
        System.out.println("Исходный список: " + fruits);
        
        // Сортировка списка
        Collections.sort(fruits);
        System.out.println("Отсортированный список: " + fruits);
        
        // Поиск элемента
        int index = fruits.indexOf("Orange");
        System.out.println("Индекс 'Orange': " + index);
        
        // Удаление элемента
        fruits.remove("Banana");
        System.out.println("После удаления 'Banana': " + fruits);
        
        // Получение элемента по индексу
        String fruit = fruits.get(1);
        System.out.println("Элемент с индексом 1: " + fruit);
        
        // Размер списка
        System.out.println("Размер списка: " + fruits.size());
    }
}
Реализация стека 
import java.util.EmptyStackException;

class Stack<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    private Node<T> top;
    private int size;
    
    public Stack() {
        top = null;
        size = 0;
    }
    
    // Добавление элемента в стек
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    // Удаление и возврат верхнего элемента
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }
    
    // Просмотр верхнего элемента без удаления
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }
    
    // Проверка на пустоту
    public boolean isEmpty() {
        return top == null;
    }
    
    // Получение размера стека
    public int size() {
        return size;
    }
    
    // Вывод содержимого стека
    public void display() {
        if (isEmpty()) {
            System.out.println("Стек пуст");
            return;
        }
        
        System.out.print("Стек (сверху вниз): ");
        Node<T> current = top;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        
        // Добавление элементов в стек
        stack.push(10);
        stack.push(20);
        stack.push(30);
        
        // Вывод содержимого стека
        stack.display();
        
        // Просмотр верхнего элемента
        System.out.println("Верхний элемент: " + stack.peek());
        
        // Извлечение элементов
        System.out.println("Извлечен: " + stack.pop());
        System.out.println("Извлечен: " + stack.pop());
        
        // Вывод оставшегося стека
        stack.display();
        
        // Проверка размера
        System.out.println("Размер стека: " + stack.size());
        
        // Проверка на пустоту
        System.out.println("Стек пуст: " + stack.isEmpty());
    }
}
