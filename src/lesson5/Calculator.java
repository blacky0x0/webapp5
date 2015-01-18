package lesson5;

import ru.javawebinar.webapp.model.Resume;

import java.util.Comparator;

/**
 * GKislin
 * 16.01.2015.
 */
public class Calculator {
    public int abs(int value) {
        return Math.abs(value);
    }

    public static void main(String[] args) {
        Comparator<Resume> comparator = new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                System.out.println(this.getClass().getSimpleName());
                return 0;
            }
        };
        System.out.println(comparator);
    }
}
