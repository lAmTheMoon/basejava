package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {
    protected static final String UUID_NOT_EXIST_INFO = "Резюме с uuid - %s не существует\n";
    protected static final String UUID_EXIST_INFO = "Резюме с uuid - %s уже существует\n";
    protected static final String STORAGE_IS_FULL = "Невозможно сохранить новое резюме, хранилище заполнено\n";

    protected static final int OBJECT_NOT_EXIST = -1;
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println(STORAGE_IS_FULL);
            return;
        }

        int resumeIdx = getIndex(resume);
        if (resumeIdx > OBJECT_NOT_EXIST) {
            System.out.printf(UUID_EXIST_INFO, resume.getUuid());
        } else {
            saveInStorage(resume, resumeIdx);
            size++;
        }
    }

    protected abstract void saveInStorage(Resume resume, int resumeIdx);

    @Override
    public Resume get(String uuid) {
        int resumeIdx = getIndex(uuid);
        if (resumeIdx > OBJECT_NOT_EXIST) {
            return storage[resumeIdx];
        }
        System.out.printf(UUID_NOT_EXIST_INFO, uuid);
        return null;
    }

    @Override
    public void update(Resume resume) {
        int resumeIdx = getIndex(resume);
        if (resumeIdx > OBJECT_NOT_EXIST) {
            storage[resumeIdx] = resume;
        } else {
            System.out.printf(UUID_NOT_EXIST_INFO, resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    @Override
    public int size() {
        return size;
    }

    protected int getIndex(Resume r) {
        if (Objects.isNull(r)) {
            return OBJECT_NOT_EXIST;
        }
        return getIndex(r.getUuid());
    }

    protected abstract int getIndex(String uuid);
}
