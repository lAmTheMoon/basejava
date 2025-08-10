package webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;


abstract class AbstractArrayStorageTest {
    protected final Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4);
    protected static final Resume[] EXPECTED = {RESUME_4, RESUME_2, RESUME_3};

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.save(RESUME_4);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
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
        assertSize(maxSize);
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void saveExistResumeInStorage() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_3));
    }

    @Test
    void saveNullInStorage() {
        Assertions.assertThrows(StorageException.class, () -> storage.save(null));
    }

    @Test
    void get() {
        storage.save(RESUME_1);
        assertGet(RESUME_1);
    }

    @Test
    void getNotExistResume() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void update() {
        storage.update(RESUME_3);
        assertSize(3);
        Assertions.assertSame(RESUME_3, storage.get(UUID_3));
    }

    @Test
    void updateNullInStorage() {
        Assertions.assertThrows(StorageException.class, () -> storage.update(null));
    }

    @Test
    void updateNotExistResume() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_1));
    }

    @Test
    void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_3));
    }

    @Test
    void deleteNotExistResume() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_1));
    }

    @Test
    void getAll() {
        assertSize(3);
        Assertions.assertArrayEquals(EXPECTED, storage.getAll());
    }
    @Test
    void size() {
        assertSize(3);
        storage.save(RESUME_1);
        assertSize(4);
        storage.update(RESUME_1);
        assertSize(4);
        storage.delete(UUID_1);
        assertSize(3);
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(new Resume[0], storage.getAll());
    }

    protected void assertSize(int maxSize) {
        Assertions.assertEquals(maxSize, storage.size());
    }

    protected void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }
}