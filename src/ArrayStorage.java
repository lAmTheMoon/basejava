import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        if (Objects.isNull(uuid)) {
            return null;
        }
        for (Resume resume : storage) {
            if (Objects.nonNull(resume) && uuid.equals(resume.uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (Objects.isNull(uuid)) {
            return;
        }

        int idx = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            return;
        }

        for (int i = idx; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int size = size();
        Resume[] newStorage = new Resume[size];
        for (int i = 0; i < size; i++) {
            if (Objects.nonNull(storage[i])) {
                newStorage[i] = storage[i];
            }
        }
        return newStorage;
    }

    int size() {
        return size;
    }
}
