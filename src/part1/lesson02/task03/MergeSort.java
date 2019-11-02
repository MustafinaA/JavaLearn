package part1.lesson02.task03;

/**
 * Сортировка слиянием
 * O(nlogn)
 */
public class MergeSort {
    /**
     * Массив Person
     */
    private Person[] arr;

    /**
     * Конструктор
     */
    public MergeSort(Person[] arr) {
        this.arr = arr;
    }

    /**
     * Основной метод сортировки
     *
     * @return - отсортированный массив
     * @throws Exception - ошибка из метода mergeSort
     */
    public Person[] compareTo() throws Exception {
        mergeSort(arr, arr.length);
        return arr;
    }

    /**
     * Рекурсивный метод разделения массива на 2 "половинные" массивы
     * @param a - массив
     * @param n -
     * @throws Exception - ошибка из метода merge
     */
    public void mergeSort(Person[] a, int n) throws Exception {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Person[] l = new Person[mid];
        Person[] r = new Person[n - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    /**
     * Сортировка
     * @param a - массив объединения
     * @param l - левый подмассив
     * @param r - правый подмассив
     * @param left - размер девого массива
     * @param right - размер правого массива
     * @throws Exception - ошибка при обнаружении двух Person с одинаковым возрастом и именами
     */
    private void merge(Person[] a, Person[] l, Person[] r, int left, int right) throws Exception {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].getAge() == r[j].getAge() && l[i].getName().equals(r[j].getName())) {
                throw new Exception(l[i].toString());
            }
            if (
                    l[i].getSex() == Person.Sex.MAN && r[j].getSex() == Person.Sex.WOMAN ||
                            l[i].getSex().equals(r[j].getSex()) && l[i].getAge() > r[j].getAge() ||
                            l[i].getSex().equals(r[j].getSex()) && l[i].getAge() == r[j].getAge() && l[i].getName().compareTo(r[j].getName()) < 0
            ) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }
}
