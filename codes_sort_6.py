Сортировка Шелла (Shell Sort)
def shell_sort(arr):
    n = len(arr)
    gap = n // 2
    
    while gap > 0:
        for i in range(gap, n):
            temp = arr[i]
            j = i
            while j >= gap and arr[j - gap] > temp:
                arr[j] = arr[j - gap]
                j -= gap
            arr[j] = temp
        gap //= 2

# Пример использования
arr = [12, 34, 54, 2, 3, 15, 8, 29, 43, 11]
print("Исходный массив:", arr)
shell_sort(arr)
print("Отсортированный массив:", arr)
Быстрая сортировка (Quick Sort)
def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    
    pivot = arr[len(arr) // 2]
    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]
    
    return quick_sort(left) + middle + quick_sort(right)

# Или итеративная версия с разделением
def quick_sort_inplace(arr, low=0, high=None):
    if high is None:
        high = len(arr) - 1
    
    if low < high:
        pi = partition(arr, low, high)
        quick_sort_inplace(arr, low, pi - 1)
        quick_sort_inplace(arr, pi + 1, high)

def partition(arr, low, high):
    pivot = arr[high]
    i = low - 1
    
    for j in range(low, high):
        if arr[j] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

# Пример использования
arr = [10, 7, 8, 9, 1, 5]
print("Исходный массив:", arr)
quick_sort_inplace(arr)
print("Отсортированный массив:", arr)

Сортировка вставками (Insertion Sort)
def insertion_sort(arr):
    for i in range(1, len(arr)):
        key = arr[i]
        j = i - 1
        
        while j >= 0 and arr[j] > key:
            arr[j + 1] = arr[j]
            j -= 1
        
        arr[j + 1] = key

# Пример использования
arr = [12, 11, 13, 5, 6]
print("Исходный массив:", arr)
insertion_sort(arr)
print("Отсортированный массив:", arr)

Пузырьковая сортировка (Bubble Sort)

def bubble_sort(arr):
    n = len(arr)
    
    for i in range(n - 1):
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]

# Оптимизированная версия
def bubble_sort_optimized(arr):
    n = len(arr)
    
    for i in range(n - 1):
        swapped = False
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]
                swapped = True
        if not swapped:
            break

# Пример использования
arr = [64, 34, 25, 12, 22, 11, 90]
print("Исходный массив:", arr)
bubble_sort_optimized(arr)
print("Отсортированный массив:", arr)
