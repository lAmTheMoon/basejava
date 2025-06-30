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
    private static final String STORAGE_IS_FULL = "Невозможно сохранить новое резюме, хранилище заполнено\n";
    private static final int OBJECT_NOT_EXIST = -1;
    private static final int STORAGE_LIMIT = 10000;

    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int resumeIdx = findSearchKey(resume);
        if (resumeIdx != OBJECT_NOT_EXIST) {
            storage[resumeIdx] = resume;
            return;
        }
        System.out.printf(UUID_NOT_EXIST_INFO, resume.getUuid());
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println(STORAGE_IS_FULL);
            return;
        }

        int resumeIdx = findSearchKey(resume);
        if (resumeIdx == OBJECT_NOT_EXIST) {
            storage[size++] = resume;
            return;
        }
        System.out.printf(UUID_EXIST_INFO, resume.getUuid());
    }

    public Resume get(String uuid) {
        int resumeIdx = findSearchKey(uuid);
        if (resumeIdx != OBJECT_NOT_EXIST) {
            return storage[resumeIdx];
        }
        System.out.printf(UUID_NOT_EXIST_INFO, uuid);
        return null;
    }

    public void delete(String uuid) {
        int resumeIdx = findSearchKey(uuid);
        if (resumeIdx != OBJECT_NOT_EXIST) {
            storage[resumeIdx] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
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

    private int findSearchKey(Resume r) {
        if (Objects.isNull(r)) {
            return OBJECT_NOT_EXIST;
        }
        return findSearchKey(r.getUuid());
    }

    private int findSearchKey(String uuid) {
        if (Objects.isNull(uuid)) {
            return OBJECT_NOT_EXIST;
        }

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return OBJECT_NOT_EXIST;
    }
}
