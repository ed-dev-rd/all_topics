import java.util.*;

public class TabuSearchScheduling {
    private int workers;
    private int days;
    private int workDaysPerWorker;
    private int maxConsecutiveDaysOff;
    private int[][] schedule;
    private TabuList tabuList;
    private Random random;
    
    public TabuSearchScheduling(int workers, int days, int workDaysPerWorker, int maxConsecutiveDaysOff) {
        this.workers = workers;
        this.days = days;
        this.workDaysPerWorker = workDaysPerWorker;
        this.maxConsecutiveDaysOff = maxConsecutiveDaysOff;
        this.schedule = new int[workers][days];
        this.tabuList = new TabuList(50); // Размер табу-листа
        this.random = new Random();
        initializeSchedule();
    }
    
    // Инициализация начального расписания
    private void initializeSchedule() {
        for (int i = 0; i < workers; i++) {
            // Создаем список дней и перемешиваем
            List<Integer> dayList = new ArrayList<>();
            for (int j = 0; j < days; j++) {
                dayList.add(j);
            }
            Collections.shuffle(dayList);
            
            // Выбираем случайные дни для работы
            for (int j = 0; j < workDaysPerWorker; j++) {
                schedule[i][dayList.get(j)] = 1; // 1 - рабочий день
            }
        }
    }
    
    // Вычисление стоимости решения (чем меньше, тем лучше)
    public int evaluateSolution(int[][] solution) {
        int cost = 0;
        
        // Штраф за нарушение ограничения на максимальное количество выходных подряд
        for (int i = 0; i < workers; i++) {
            int consecutiveDaysOff = 0;
            for (int j = 0; j < days; j++) {
                if (solution[i][j] == 0) { // Выходной
                    consecutiveDaysOff++;
                    if (consecutiveDaysOff > maxConsecutiveDaysOff) {
                        cost += 10; // Штраф за нарушение ограничения
                    }
                } else {
                    consecutiveDaysOff = 0;
                }
            }
        }
        
        // Штраф за отклонение от требуемого количества рабочих дней
        for (int i = 0; i < workers; i++) {
            int actualWorkDays = 0;
            for (int j = 0; j < days; j++) {
                actualWorkDays += solution[i][j];
            }
            cost += Math.abs(actualWorkDays - workDaysPerWorker) * 5;
        }
        
        return cost;
    }
    
    // Генерация соседних решений
    public List<Neighbor> generateNeighbors(int[][] currentSolution) {
        List<Neighbor> neighbors = new ArrayList<>();
        
        for (int i = 0; i < workers; i++) {
            for (int j = 0; j < days; j++) {
                // Пробуем поменять статус дня (рабочий/выходной)
                int[][] neighbor = copySolution(currentSolution);
                neighbor[i][j] = 1 - neighbor[i][j]; // Инвертируем значение
                
                // Проверяем, не в табу-листе ли это движение
                Move move = new Move(i, j);
                if (!tabuList.contains(move)) {
                    int cost = evaluateSolution(neighbor);
                    neighbors.add(new Neighbor(neighbor, cost, move));
                }
                
                // Пробуем обменять дни между работниками
                for (int k = i + 1; k < workers; k++) {
                    for (int l = 0; l < days; l++) {
                        if (neighbor[i][j] != neighbor[k][l]) {
                            int[][] swapNeighbor = copySolution(currentSolution);
                            // Меняем дни местами
                            int temp = swapNeighbor[i][j];
                            swapNeighbor[i][j] = swapNeighbor[k][l];
                            swapNeighbor[k][l] = temp;
                            
                            Move swapMove = new Move(i, j, k, l);
                            if (!tabuList.contains(swapMove)) {
                                int swapCost = evaluateSolution(swapNeighbor);
                                neighbors.add(new Neighbor(swapNeighbor, swapCost, swapMove));
                            }
                        }
                    }
                }
            }
        }
        
        return neighbors;
    }
    
    // Копирование решения
    private int[][] copySolution(int[][] solution) {
        int[][] copy = new int[workers][days];
        for (int i = 0; i < workers; i++) {
            System.arraycopy(solution[i], 0, copy[i], 0, days);
        }
        return copy;
    }
    
    // Основной алгоритм табу-поиска
    public void tabuSearch(int maxIterations) {
        int[][] currentSolution = copySolution(schedule);
        int currentCost = evaluateSolution(currentSolution);
        int[][] bestSolution = copySolution(currentSolution);
        int bestCost = currentCost;
        
        System.out.println("Начальная стоимость: " + currentCost);
        
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            List<Neighbor> neighbors = generateNeighbors(currentSolution);
            
            if (neighbors.isEmpty()) {
                System.out.println("Нет допустимых соседних решений на итерации " + iteration);
                break;
            }
            
            // Сортируем соседей по стоимости (лучшие первыми)
            neighbors.sort(Comparator.comparingInt(n -> n.cost));
            
            // Выбираем лучшее допустимое соседнее решение
            Neighbor bestNeighbor = neighbors.get(0);
            
            // Обновляем текущее решение
            currentSolution = bestNeighbor.solution;
            currentCost = bestNeighbor.cost;
            
            // Добавляем движение в табу-лист
            tabuList.add(bestNeighbor.move);
            
            // Обновляем лучшее решение
            if (currentCost < bestCost) {
                bestSolution = copySolution(currentSolution);
                bestCost = currentCost;
                System.out.println("Итерация " + iteration + ": Новый лучший результат = " + bestCost);
            }
            
            // Критерий остановки
            if (bestCost == 0) {
                System.out.println("Найдено оптимальное решение!");
                break;
            }
        }
        
        // Сохраняем лучшее найденное решение
        schedule = bestSolution;
        System.out.println("Финальная стоимость: " + bestCost);
    }
    
    // Вывод расписания
    public void printSchedule() {
        System.out.println("\nФинальное расписание:");
        System.out.print("Работник\\День\t");
        for (int j = 0; j < days; j++) {
            System.out.print("День " + (j + 1) + "\t");
        }
        System.out.println("Всего раб.дн.");
        
        for (int i = 0; i < workers; i++) {
            System.out.print("Работник " + (i + 1) + "\t\t");
            int workDaysCount = 0;
            for (int j = 0; j < days; j++) {
                System.out.print(schedule[i][j] == 1 ? "Раб\t" : "Вых\t");
                workDaysCount += schedule[i][j];
            }
            System.out.println(workDaysCount);
        }
    }
    
    // Проверка ограничений
    public void checkConstraints() {
        System.out.println("\nПроверка ограничений:");
        
        boolean allConstraintsSatisfied = true;
        
        // Проверка количества рабочих дней
        for (int i = 0; i < workers; i++) {
            int workDaysCount = 0;
            for (int j = 0; j < days; j++) {
                workDaysCount += schedule[i][j];
            }
            if (workDaysCount != workDaysPerWorker) {
                System.out.println("Ошибка: Работник " + (i + 1) + " работает " + workDaysCount + 
                                 " дней вместо " + workDaysPerWorker);
                allConstraintsSatisfied = false;
            }
        }
        
        // Проверка ограничения на максимальное количество выходных подряд
        for (int i = 0; i < workers; i++) {
            int consecutiveDaysOff = 0;
            for (int j = 0; j < days; j++) {
                if (schedule[i][j] == 0) {
                    consecutiveDaysOff++;
                    if (consecutiveDaysOff > maxConsecutiveDaysOff) {
                        System.out.println("Ошибка: Работник " + (i + 1) + 
                                         " имеет " + consecutiveDaysOff + " выходных подряд в день " + (j + 1));
                        allConstraintsSatisfied = false;
                    }
                } else {
                    consecutiveDaysOff = 0;
                }
            }
        }
        
        if (allConstraintsSatisfied) {
            System.out.println("Все ограничения удовлетворены!");
        } else {
            System.out.println("Найдены нарушения ограничений!");
        }
    }
    
    // Вложенные классы для табу-поиска
    
    // Класс для представления движения
    static class Move {
        int worker1, day1;
        int worker2, day2;
        boolean isSwap;
        
        // Конструктор для простого изменения
        Move(int worker, int day) {
            this.worker1 = worker;
            this.day1 = day;
            this.isSwap = false;
        }
        
        // Конструктор для обмена
        Move(int worker1, int day1, int worker2, int day2) {
            this.worker1 = worker1;
            this.day1 = day1;
            this.worker2 = worker2;
            this.day2 = day2;
            this.isSwap = true;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Move move = (Move) obj;
            if (isSwap != move.isSwap) return false;
            if (isSwap) {
                return (worker1 == move.worker1 && day1 == move.day1 && 
                        worker2 == move.worker2 && day2 == move.day2) ||
                       (worker1 == move.worker2 && day1 == move.day2 && 
                        worker2 == move.worker1 && day2 == move.day1);
            } else {
                return worker1 == move.worker1 && day1 == move.day1;
            }
        }
        
        @Override
        public int hashCode() {
            if (isSwap) {
                // Для обмена порядок не важен
                int hash1 = Objects.hash(worker1, day1, worker2, day2);
                int hash2 = Objects.hash(worker2, day2, worker1, day1);
                return Math.min(hash1, hash2);
            } else {
                return Objects.hash(worker1, day1);
            }
        }
    }
    
    // Класс для соседнего решения
    static class Neighbor {
        int[][] solution;
        int cost;
        Move move;
        
        Neighbor(int[][] solution, int cost, Move move) {
            this.solution = solution;
            this.cost = cost;
            this.move = move;
        }
    }
    
    // Класс табу-листа
    static class TabuList {
        private Queue<Move> queue;
        private int maxSize;
        
        TabuList(int maxSize) {
            this.maxSize = maxSize;
            this.queue = new LinkedList<>();
        }
        
        void add(Move move) {
            if (queue.size() >= maxSize) {
                queue.poll();
            }
            queue.offer(move);
        }
        
        boolean contains(Move move) {
            return queue.contains(move);
        }
    }
    
    // Главный метод
    public static void main(String[] args) {
        // Параметры: 5 работников, 7 дней, каждый работает 5 дней, максимум 2 выходных подряд
        TabuSearchScheduling scheduler = new TabuSearchScheduling(5, 7, 5, 2);
        
        System.out.println("Задача планирования работников");
        System.out.println("Параметры: " + 5 + " работников, " + 7 + " дней, " + 
                          5 + " рабочих дней на работника, максимум " + 2 + " выходных подряд");
        
        // Запуск табу-поиска
        scheduler.tabuSearch(1000);
        
        // Вывод результатов
        scheduler.printSchedule();
        scheduler.checkConstraints();
        
        System.out.println("\nОценка качества: " + scheduler.evaluateSolution(scheduler.schedule));
        System.out.println("0 - оптимальное решение, чем больше значение, тем хуже качество");
    }
}
