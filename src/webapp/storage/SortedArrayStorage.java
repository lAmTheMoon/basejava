package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveInStorage(Resume resume, int resumeIdx) {
        resumeIdx = -resumeIdx - 1;
        System.arraycopy(storage, resumeIdx, storage, resumeIdx + 1, size - resumeIdx);
        storage[resumeIdx] = resume;
    }

    @Override
    protected void deleteFromStorage(int resumeIdx) {
        System.arraycopy(storage, resumeIdx + 1, storage, resumeIdx, size - resumeIdx);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
