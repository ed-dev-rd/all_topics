'''
Упорядоченное дерево с переменным количеством потомков
'''

class MultiTree:
    def __init__(self, val):
        self.val = val
        self.up = None
        self.down = None
        self.side = None
        self.level = 0

class ForestCollection:
    def __init__(self):
        self.primary = None
    
    def add_value(self, val):
        """
        Добавление нового значения в коллекцию
        """
        new_forest = ForestCollection()
        new_forest.primary = MultiTree(val)
        self.combine_forests(new_forest)
    
    def combine_forests(self, other_forest):
        """
        Объединение двух лесных массивов
        """
        self.primary = self._join_trees(self.primary, other_forest.primary)
        
        if self.primary is None:
            return
        
        prev_tree = None
        curr_tree = self.primary
        next_tree = curr_tree.side
        
        while next_tree is not None:
            if (curr_tree.level != next_tree.level or 
                (next_tree.side is not None and next_tree.side.level == curr_tree.level)):
                prev_tree = curr_tree
                curr_tree = next_tree
            elif curr_tree.val <= next_tree.val:
                curr_tree.side = next_tree.side
                self._attach_trees(next_tree, curr_tree)
            else:
                if prev_tree is None:
                    self.primary = next_tree
                else:
                    prev_tree.side = next_tree
                self._attach_trees(curr_tree, next_tree)
                curr_tree = next_tree
            next_tree = curr_tree.side
    
    def _join_trees(self, tree1, tree2):
        """
        Объединение цепочек деревьев
        """
        dummy = MultiTree(0)
        tail = dummy
        
        while tree1 is not None and tree2 is not None:
            if tree1.level <= tree2.level:
                tail.side = tree1
                tree1 = tree1.side
            else:
                tail.side = tree2
                tree2 = tree2.side
            tail = tail.side
        
        tail.side = tree1 if tree1 is not None else tree2
        return dummy.side
    
    def _attach_trees(self, smaller, larger):
        """
        Присоединение дерева к корню другого
        """
        smaller.up = larger
        smaller.side = larger.down
        larger.down = smaller
        larger.level += 1
    
    def get_minimum(self):
        """
        Поиск наименьшего значения в коллекции
        """
        if self.primary is None:
            return None
        
        min_val = float('inf')
        current = self.primary
        while current is not None:
            if current.val < min_val:
                min_val = current.val
            current = current.side
        return min_val
    
    def __str__(self):
        items = []
        current = self.primary
        while current is not None:
            items.append(f"{current.val}(lvl={current.level})")
            current = current.side
        return f"ForestCollection [{', '.join(items)}]"

# Проверка работы
forest = ForestCollection()
forest.add_value(10)
forest.add_value(5)
forest.add_value(8)
forest.add_value(3)
print("Минимальное значение:", forest.get_minimum())

'''
Двоичное дерево с приоритетом
'''

class PriorityTree:
    def __init__(self):
        self.nodes = []
    
    def push(self, val):
        """
        Добавление элемента в дерево
        """
        self.nodes.append(val)
        self._float_up(len(self.nodes) - 1)
    
    def _float_up(self, pos):
        """
        Всплытие элемента на нужную позицию
        """
        while pos > 0:
            parent_pos = (pos - 1) // 2
            if self.nodes[pos] >= self.nodes[parent_pos]:
                break
            self._swap(pos, parent_pos)
            pos = parent_pos
    
    def _swap(self, i, j):
        """
        Обмен элементов местами
        """
        self.nodes[i], self.nodes[j] = self.nodes[j], self.nodes[i]
    
    def peek_min(self):
        """
        Просмотр минимального элемента без удаления
        """
        if not self.nodes:
            return None
        return self.nodes[0]
    
    def _sink_down(self, start_pos):
        """
        Погружение элемента вниз
        """
        pos = start_pos
        length = len(self.nodes)
        current_val = self.nodes[pos]
        
        while True:
            left_child = 2 * pos + 1
            right_child = left_child + 1
            smallest = pos
            
            if left_child < length and self.nodes[left_child] < self.nodes[smallest]:
                smallest = left_child
            
            if right_child < length and self.nodes[right_child] < self.nodes[smallest]:
                smallest = right_child
            
            if smallest == pos:
                break
                
            self._swap(pos, smallest)
            pos = smallest

# Пример использования
pt = PriorityTree()
pt.push(10)
pt.push(5)
pt.push(8)
pt.push(3)
print("Минимальный элемент:", pt.peek_min())

'''
Динамическая коллекция с быстрым доступом к минимуму
'''

class FastMinCollection:
    def __init__(self):
        self.min_node = None
        self.total_count = 0
    
    def add(self, val):
        """
        Добавление нового элемента
        """
        new_node = self._create_node(val)
        
        if self.min_node is None:
            self.min_node = new_node
            new_node.prev = new_node
            new_node.next = new_node
        else:
            self._insert_into_ring(new_node)
            if val < self.min_node.val:
                self.min_node = new_node
        self.total_count += 1
    
    def _create_node(self, val):
        """
        Создание нового узла
        """
        return type('Node', (), {
            'val': val,
            'parent': None,
            'child': None,
            'prev': None,
            'next': None,
            'rank': 0,
            'marked': False
        })()
    
    def _insert_into_ring(self, node):
        """
        Вставка узла в кольцевой список
        """
        node.prev = self.min_node.prev
        node.next = self.min_node
        self.min_node.prev.next = node
        self.min_node.prev = node
    
    def remove_min(self):
        """
        Извлечение минимального элемента
        """
        if self.min_node is None:
            return None

        min_val = self.min_node.val
        
        # Переносим дочерние элементы в основной список
        if self.min_node.child:
            children = self._get_children_list(self.min_node)
            for child in children:
                self._insert_into_ring(child)
                child.parent = None

        # Удаляем минимальный узел
        if self.min_node.next == self.min_node:
            self.min_node = None
        else:
            self.min_node = self.min_node.next
            self._optimize_structure()

        self.total_count -= 1
        return min_val
    
    def _get_children_list(self, node):
        """
        Получение списка дочерних элементов
        """
        children = []
        if node.child:
            current = node.child
            while True:
                children.append(current)
                current = current.next
                if current == node.child:
                    break
        return children
    
    def _optimize_structure(self):
        """
        Оптимизация структуры коллекции
        """
        max_rank = self.total_count.bit_length() + 1
        rank_table = [None] * max_rank
        
        nodes = list(self._iterate_main_nodes())
        
        for node in nodes:
            current = node
            rank = current.rank
            
            while rank_table[rank] is not None:
                existing = rank_table[rank]
                if current.val > existing.val:
                    current, existing = existing, current
                self._merge_nodes(existing, current)
                rank_table[rank] = None
                rank += 1
            
            rank_table[rank] = current
        
        # Восстанавливаем ссылки
        self.min_node = None
        for node in rank_table:
            if node is not None:
                if self.min_node is None or node.val < self.min_node.val:
                    self.min_node = node
    
    def _merge_nodes(self, child, parent):
        """
        Слияние двух узлов
        """
        child.prev.next = child.next
        child.next.prev = child.prev
        child.parent = parent
        child.marked = False
        
        if parent.child is None:
            parent.child = child
            child.prev = child
            child.next = child
        else:
            child.prev = parent.child.prev
            child.next = parent.child
            parent.child.prev.next = child
            parent.child.prev = child
        
        parent.rank += 1
    
    def _iterate_main_nodes(self):
        """
        Обход основных узлов коллекции
        """
        if self.min_node is None:
            return
        
        current = self.min_node
        while True:
            yield current
            current = current.next
            if current == self.min_node:
                break
    
    def __len__(self):
        return self.total_count

# Демонстрация
fmc = FastMinCollection()
for i in [10, 5, 8, 3]:
    fmc.add(i)

print("Минимальный элемент:", fmc.remove_min())

'''
Ассоциативный массив с хешированием
'''

class KeyValueStore:
    def __init__(self, capacity=10):
        self.capacity = capacity
        self.buckets = [[] for _ in range(capacity)]
    
    def _get_bucket_index(self, key):
        """
        Вычисление номера сегмента для ключа
        """
        return hash(key) % self.capacity
    
    def set(self, key, value):
        """
        Установка значения по ключу
        """
        index = self._get_bucket_index(key)
        segment = self.buckets[index]
        
        # Проверяем наличие ключа
        for i, (k, v) in enumerate(segment):
            if k == key:
                segment[i] = (key, value)
                return
        
        # Добавляем новую пару
        segment.append((key, value))
    
    def get(self, key):
        """
        Получение значения по ключу
        """
        index = self._get_bucket_index(key)
        segment = self.buckets[index]
        
        for k, v in segment:
            if k == key:
                return v
        return None
    
    def remove(self, key):
        """
        Удаление пары по ключу
        """
        index = self._get_bucket_index(key)
        segment = self.buckets[index]
        
        for i, (k, v) in enumerate(segment):
            if k == key:
                del segment[i]
                return
        
        raise KeyError(f"Ключ '{key}' отсутствует")
    
    def __str__(self):
        lines = []
        for i, segment in enumerate(self.buckets):
            if segment:
                pairs = [f"{k}:{v}" for k, v in segment]
                lines.append(f"Сегмент {i}: {', '.join(pairs)}")
        return '\n'.join(lines)

# Пример использования
store = KeyValueStore()
store.set('яблоко', 10)
store.set('банан', 5)
store.set('апельсин', 8)

print("Значение для 'банан':", store.get('банан'))
store.remove('яблоко')
print("После удаления:")
print(store)
