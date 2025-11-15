Заполнение и обработка массива 
def main():
    # Создание и заполнение массива
    numbers = [5, 2, 8, 1, 9, 3]
    
    # Вывод исходного массива
    print("Исходный массив:", *numbers)
    
    # Сортировка массива
    numbers.sort()
    
    # Вывод отсортированного массива
    print("Отсортированный массив:", *numbers)
    
    # Поиск максимального элемента
    max_element = max(numbers)
    print("Максимальный элемент:", max_element)
    
    # Вычисление суммы элементов
    total_sum = sum(numbers)
    print("Сумма элементов:", total_sum)

if __name__ == "__main__":
    main()
  реализация стека
class Stack:
    def __init__(self):
        self.data = []
    
    # Добавление элемента в стек
    def push(self, value):
        self.data.append(value)
    
    # Удаление и возврат верхнего элемента
    def pop(self):
        if self.is_empty():
            raise RuntimeError("Стек пуст")
        return self.data.pop()
    
    # Просмотр верхнего элемента без удаления
    def peek(self):
        if self.is_empty():
            raise RuntimeError("Стек пуст")
        return self.data[-1]
    
    # Проверка на пустоту
    def is_empty(self):
        return len(self.data) == 0
    
    # Получение размера стека
    def size(self):
        return len(self.data)
    
    # Вывод содержимого стека
    def display(self):
        print("Стек (сверху вниз):", *reversed(self.data))

def main():
    stack = Stack()
    
    # Добавление элементов в стек
    stack.push(10)
    stack.push(20)
    stack.push(30)
    
    # Вывод содержимого стека
    stack.display()
    
    # Просмотр верхнего элемента
    print("Верхний элемент:", stack.peek())
    
    # Извлечение элементов
    print("Извлечен:", stack.pop())
    print("Извлечен:", stack.pop())
    
    # Вывод оставшегося стека
    stack.display()
    
    # Проверка размера
    print("Размер стека:", stack.size())

if __name__ == "__main__":
    main()
