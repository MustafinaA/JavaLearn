package part1.lesson02.task03;

/**
 * O(n2)
 */
public class BubbleSort {
    /**
     * Массив Person
     */
    private Person[] arr;

    /**
     * Конструктор
     */
    public BubbleSort(Person[] arr) {
        this.arr = arr;
    }

    /**
     * Основной метод сортировки
     *
     * @return - отсортированный массив
     * @throws Exception - ошибка при обнаружении двух Person с одинаковым возрастом и именами
     */
    public Person[] compareTo() throws Exception {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < (arr.length - i); j++) {
                if (arr[j].getAge() == arr[j - 1].getAge() && arr[j].getName().equals(arr[j - 1].getName())) {
                    throw new Exception(arr[j].toString());
                }
                if ((arr[j].getSex() == Person.Sex.MAN && arr[j - 1].getSex() == Person.Sex.WOMAN)
                        || (arr[j].getSex().equals(arr[j - 1].getSex()) && arr[j].getAge() > arr[j - 1].getAge())
                        || (arr[j].getSex().equals(arr[j - 1].getSex()) && arr[j].getAge() == arr[j - 1].getAge()
                                && arr[j].getName().compareTo(arr[j - 1].getName()) < 0)
                ) {
                    swap(arr, j, j - 1);
                }
            }
        }
        return this.arr;
    }

    /**
     * Перестановка двух элементов массива
     *
     * @param array - массив Person
     * @param ind1  - индекс одного из элементов для перестановки
     * @param ind2  - индекс другого элемента для перестановки
     */
    private void swap(Person[] array, int ind1, int ind2) {
        Person tmp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = tmp;
    }
}
