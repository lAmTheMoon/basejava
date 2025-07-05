package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveInStorage(Resume resume, int resumeIdx) {
        resumeIdx = -resumeIdx - 1;
        for (int i = size; i > resumeIdx; i--) {
            storage[i] = storage[i - 1];
        }
        storage[resumeIdx] = resume;
    }

    @Override
    protected void deleteFromStorage(int resumeIdx) {
        for (int i = resumeIdx; i <= size; i++) {
            storage[i] = storage[i + 1];
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
