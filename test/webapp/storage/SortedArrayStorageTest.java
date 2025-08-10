package webapp.storage;

import org.junit.jupiter.api.Assertions;
import webapp.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    private static final Resume[] SORTED_EXPECTED = {RESUME_2, RESUME_3, RESUME_4};

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Override
    void getAll() {
        assertSize(3);
        Assertions.assertArrayEquals(SORTED_EXPECTED, storage.getAll());
    }
}
