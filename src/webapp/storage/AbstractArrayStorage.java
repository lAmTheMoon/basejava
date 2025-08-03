package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {

    private static final String STORAGE_IS_FULL = "Невозможно сохранить новое резюме, хранилище заполнено\n";
    private static final String RESUME_IS_NULL = "Resume is NULL";

    protected static final int OBJECT_NOT_EXIST = -1;
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final void save(Resume resume) {
        if (Objects.isNull(resume)) {
            throw new StorageException(RESUME_IS_NULL, null);
        }

        if (size == STORAGE_LIMIT) {
            throw new StorageException(STORAGE_IS_FULL, resume.getUuid());
        }

        int resumeIdx = getIndex(resume.getUuid());
        if (resumeIdx > OBJECT_NOT_EXIST) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveInStorage(resume, resumeIdx);
            size++;
        }
    }

    protected abstract void saveInStorage(Resume resume, int resumeIdx);

    @Override
    public final Resume get(String uuid) {
        int resumeIdx = getIndex(uuid);
        if (resumeIdx > OBJECT_NOT_EXIST) {
            return storage[resumeIdx];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public final void update(Resume resume) {
        if (Objects.isNull(resume)) {
            throw new StorageException(RESUME_IS_NULL, null);
        }
        int resumeIdx = getIndex(resume.getUuid());
        if (resumeIdx > OBJECT_NOT_EXIST) {
            storage[resumeIdx] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public final void delete(String uuid) {
        int resumeIdx = getIndex(uuid);
        if (resumeIdx > OBJECT_NOT_EXIST) {
            deleteFromStorage(resumeIdx);
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void deleteFromStorage(int resumeIdx);

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    @Override
    public final int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}
