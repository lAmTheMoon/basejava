package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final String UUID_NOT_EXIST_INFO = "Резюме с uuid - %s не существует\n";
    private static final String UUID_EXIST_INFO = "Резюме с uuid - %s уже существует\n";
    private final Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume resume) {
        if (isExistResumeWithUuid(resume)) {
            return;
        }

        for (int i = 0; i < size; i++) {
            if (resume.getUuid().equals(storage[i].getUuid())) {
                storage[i] = resume;
                return;
            }
        }
        System.out.printf(UUID_NOT_EXIST_INFO, resume.getUuid());
    }

    public void save(Resume resume) {
        if (isExistResumeWithUuid(resume)) {
            return;
        }
        for (int i = 0; i < size; i++) {
            if (resume.getUuid().equals(storage[i].getUuid())) {
                System.out.printf(UUID_EXIST_INFO, resume.getUuid());
                return;
            }
        }
        storage[size++] = resume;
    }

    public Resume get(String uuid) {
        if (Objects.isNull(uuid)) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
        }
        System.out.printf(UUID_NOT_EXIST_INFO, uuid);
        return null;
    }

    public void delete(String uuid) {
        if (Objects.isNull(uuid)) {
            return;
        }

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }
        System.out.printf(UUID_NOT_EXIST_INFO, uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    public int size() {
        return size;
    }

    private static boolean isExistResumeWithUuid(Resume r) {
        return Objects.isNull(r) || Objects.isNull(r.getUuid());
    }
}
