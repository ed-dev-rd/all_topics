Деревья и графы: основные концепции
-
Дерево как структура данных

Дерево представляет собой связный граф без циклов, где любые две вершины соединены единственным маршрутом. Отличительные черты деревьев:

Наличие корневой вершины, являющейся точкой отсчета
Направленность связей от корня к периферийным узлам (листьям)
Каждый некорневой узел имеет строго одного предка
Практические применения: иерархия файловой системы, организационные структуры, дерево решений.

Графы и их разнообразие

Граф - математическая модель отношений между объектами, включающая вершины и соединяющие их ребра. Классификация графов:

По направленности: ориентированные и неориентированные
По наличию весовых характеристик: взвешенные и невзвешенные
Области использования: транспортные сети, web-графы, нейронные сети.

Построение древовидной структуры на Python
-

class TreeElement:
    def __init__(self, val):
        self.val = val
        self.descendants = []
    
    def append_descendant(self, descendant):
        self.descendants.append(descendant)

# Демонстрация работы
base_element = TreeElement(1)
base_element.append_descendant(TreeElement(2))
base_element.append_descendant(TreeElement(3))

Комментарий:
Класс TreeElement хранит значение элемента и перечень его потомков. Предложенная структура легко адаптируется для различных методов обработки дерева.

Моделирование графа на Java

import java.util.LinkedList;
import java.util.List;

class GraphElement {
    int identifier;
    List<GraphElement> adjacentElements;
    
    public GraphElement(int identifier) {
        this.identifier = identifier;
        this.adjacentElements = new LinkedList<>();
    }
    
    public void connectElement(GraphElement element) {
        this.adjacentElements.add(element);
    }
}

// Пример построения
GraphElement element1 = new GraphElement(1);
GraphElement element2 = new GraphElement(2);
element1.connectElement(element2);
element2.connectElement(element1);

Комментарий:

Использование списка смежности обеспечивает гибкость при модификации графа. Связный список (LinkedList) может быть более эффективен для частых операций добавления.

Реализация графа на C++

#include <vector>
#include <memory>

class GraphNode {
public:
    int node_id;
    std::vector<std::shared_ptr<GraphNode>> connected_nodes;
    
    GraphNode(int id) : node_id(id) {}
    
    void link_node(std::shared_ptr<GraphNode> node) {
        connected_nodes.emplace_back(node);
    }
};

// Пример использования
int main() {
    auto node1 = std::make_shared<GraphNode>(1);
    auto node2 = std::make_shared<GraphNode>(2);
    node1->link_node(node2);
    node2->link_node(node1);
    return 0;
}

Комментарий:

Применение умных указателей (shared_ptr) автоматизирует управление памятью, уменьшая вероятность утечек. Контейнер vector обеспечивает быстрый последовательный доступ.

Алгоритм глубинного обхода: детальный разбор

Рассмотрим модифицированную версию DFS на Python:

def depth_first_traversal(start_node):
    explored = set()
    node_stack = [start_node]
    
    while node_stack:
        current = node_stack.pop()
        if current not in explored:
            explored.add(current)
            print(f"Обрабатывается узел: {current.val}")
            # Добавляем потомков в исходном порядке
            for child in current.descendants:
                node_stack.append(child)

# Запуск обхода
depth_first_traversal(base_element)
Процесс выполнения:

Подготовительный этап:

Инициализация начинается с корневого элемента
Создается коллекция explored для учета пройденных узлов
Формируется стек node_stack с начальным узлом
Итеративная обработка:

Из стека извлекается последний добавленный элемент
При первом посещении узел регистрируется и обрабатывается
Все дочерние элементы помещаются в стек для последующего анализа
Особенности реализации:
В данной версии потомки добавляются в естественном порядке, что изменяет последовательность обхода по сравнению с исходным алгоритмом, но сохраняет корректность DFS.

Вычислительная сложность:

Каждый узел обрабатывается однократно
Все связи анализируются один раз
Итоговая сложность: O(V + E), где V - количество вершин, E - количество ребер
Для деревьев, где E = V - 1, сложность упрощается до O(V).
