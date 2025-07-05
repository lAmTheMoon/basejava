package webapp.storage;

import webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void saveInStorage(Resume resume, int resumeIdx) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromStorage(int resumeIdx) {
        storage[resumeIdx] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    protected int getIndex(String uuid) {
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
