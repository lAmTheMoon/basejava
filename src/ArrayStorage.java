import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (Objects.nonNull(storage[i])) {
                storage[i] = null;
            } else {
                break;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (Objects.isNull(storage[i])) {
                storage[i] = r;
                break;
            }
        }
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
        Resume[] newStorage = new Resume[10000];
        int idx = 0;
        for (Resume resume : storage) {
            if (Objects.nonNull(resume) && !uuid.equals(resume.uuid)) {
                newStorage[idx] = resume;
                idx++;
            }
            if (Objects.isNull(resume)) {
                break;
            }
        }
        storage = newStorage;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int size = size();
        Resume[] newStorage = new Resume[size];
        for (int i = 0; i < storage.length; i++) {
            if (Objects.nonNull(storage[i])) {
                newStorage[i] = storage[i];
            }
            if (Objects.isNull(storage[i])) {
                break;
            }
        }
        return newStorage;
    }

    int size() {
        int size = 0;
        for (Resume resume : storage) {
            if (Objects.nonNull(resume)) {
                size++;
            }
            if (Objects.isNull(resume)) {
                break;
            }
        }
        return size;
    }
}
