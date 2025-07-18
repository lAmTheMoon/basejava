package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {
    protected static final String UUID_NOT_EXIST_INFO = "Резюме с uuid - %s не существует\n";
    protected static final String UUID_EXIST_INFO = "Резюме с uuid - %s уже существует\n";
    protected static final String STORAGE_IS_FULL = "Невозможно сохранить новое резюме, хранилище заполнено\n";
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
        if (size == STORAGE_LIMIT) {
            System.out.println(STORAGE_IS_FULL);
            return;
        }
        if (Objects.isNull(resume)) {
            System.out.println(RESUME_IS_NULL);
            return;
        }

        int resumeIdx = getIndex(resume.getUuid());
        if (resumeIdx > OBJECT_NOT_EXIST) {
            System.out.printf(UUID_EXIST_INFO, resume.getUuid());
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
        System.out.printf(UUID_NOT_EXIST_INFO, uuid);
        return null;
    }

    @Override
    public final void update(Resume resume) {
        if (Objects.isNull(resume)) {
            System.out.println(RESUME_IS_NULL);
            return;
        }
        int resumeIdx = getIndex(resume.getUuid());
        if (resumeIdx > OBJECT_NOT_EXIST) {
            storage[resumeIdx] = resume;
        } else {
            System.out.printf(UUID_NOT_EXIST_INFO, resume.getUuid());
        }
    }

    @Override
    public final void delete(String uuid) {
        int resumeIdx = getIndex(uuid);
        if (resumeIdx > OBJECT_NOT_EXIST) {
            deleteFromStorage(resumeIdx);
            size--;
        } else {
            System.out.printf(UUID_NOT_EXIST_INFO, uuid);
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
