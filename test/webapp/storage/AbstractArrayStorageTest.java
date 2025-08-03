package webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;


abstract class AbstractArrayStorageTest {
    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.save(new Resume(UUID_4));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void save() {
        int maxSize = AbstractArrayStorage.STORAGE_LIMIT;
        int freeSize = maxSize - storage.size();
        try {
            for (int i = 0; i < freeSize; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assertions.fail("Exception thrown before full storage");
        }
        Assertions.assertEquals(maxSize, storage.size());
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void saveExistResumeInStorage() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_3)));
    }

    @Test
    void saveNullInStorage() {
        Assertions.assertThrows(StorageException.class, () -> storage.save(null));
    }

    @Test
    void get() {
        Resume resume = new Resume(UUID_1);
        storage.save(resume);
        Assertions.assertEquals(resume, storage.get(UUID_1));
    }

    @Test
    void getNotExistResume() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID_3);
        storage.update(resume);
        Assertions.assertEquals(3, storage.size());
        Assertions.assertEquals(resume, storage.get(UUID_3));
    }

    @Test
    void updateNullInStorage() {
        Assertions.assertThrows(StorageException.class, () -> storage.update(null));
    }

    @Test
    void updateNotExistResume() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_1)));
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        Assertions.assertEquals(2, storage.size());
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
    }

    @Test
    void deleteNotExistResume() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
    }

    @Test
    void getAll() {
        Assertions.assertEquals(3, storage.size());
        Assertions.assertEquals(3, storage.getAll().length);
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
        storage.save(new Resume(UUID_1));
        Assertions.assertEquals(4, storage.size());
        storage.update(new Resume(UUID_1));
        Assertions.assertEquals(4, storage.size());
        storage.delete(UUID_1);
        Assertions.assertEquals(3, storage.size());
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }
}