Типовая задача со структурой данных «очередь и дек» на ЯП Java
import java.util.*;

public class QueueExample {
    public static void main(String[] args) {
        // Создание очереди на основе LinkedList
        Queue<String> queue = new LinkedList<>();
        
        // Добавление элементов в конец очереди
        queue.add("Первый");
        queue.add("Второй");
        queue.add("Третий");
        
        // Обработка элементов в порядке поступления
        while (!queue.isEmpty()) {
            System.out.println("Обработан: " + queue.poll());
        }
        // Результат:
        // Обработан: Первый
        // Обработан: Второй
        // Обработан: Третий
    }
}
Дек (двусторонняя очередь):
import java.util.*;

public class DequeExample {
    public static void main(String[] args) {
        // Создание двусторонней очереди
        Deque<Integer> deque = new ArrayDeque<>();
        
        // Добавление элементов в начало и конец
        deque.addFirst(10);
        deque.addLast(20);
        deque.addFirst(5);
        deque.addLast(25);
        
        System.out.println("Содержимое дека: " + deque);
        // Результат: [5, 10, 20, 25]
        
        // Извлечение с обоих концов
        System.out.println("Первый элемент: " + deque.removeFirst());
        System.out.println("Последний элемент: " + deque.removeLast());
    }
}
