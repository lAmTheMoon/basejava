package webapp;

import webapp.model.Resume;
import webapp.storage.ArrayStorage;
import webapp.storage.SortedArrayStorage;
import webapp.storage.Storage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {

    public static void main(String[] args) {
        Storage sortedStorage = new SortedArrayStorage();
        Storage storage = new ArrayStorage();
        checkStorage(sortedStorage);
        checkStorage(storage);
    }

    private static void checkStorage(Storage storage) {
        System.out.println("Size: " + storage.size());
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r5 = new Resume();
        r5.setUuid("uuid5");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");

        storage.save(r1);
        System.out.println("Size: " + storage.size());
        storage.save(r5);
        System.out.println("Size: " + storage.size());
        storage.save(r3);
        System.out.println("Size: " + storage.size());
        storage.save(r2);
        System.out.println("Size: " + storage.size());
        System.out.println("Save null");
        storage.save(null);
        System.out.println("Size: " + storage.size());

        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        System.out.println("Size: " + storage.size());

        System.out.println("Get dummy: " + storage.get("dummy"));

        printAll(storage);
        System.out.println("Delete r3");
        storage.delete(r3.getUuid());
        printAll(storage);
        System.out.println("Delete r1");
        storage.delete(r1.getUuid());
        printAll(storage);
        System.out.println("Size: " + storage.size());

        System.out.println("Delete dummy");
        storage.delete("dummy");
        System.out.println("Size: " + storage.size());

        System.out.println("Update null");
        storage.update(null);
        System.out.println("Update new");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        storage.update(r4);
        System.out.println("Size: " + storage.size());

        System.out.println("Save new");
        storage.save(r4);
        storage.update(r4);
        System.out.println("Size: " + storage.size());
        printAll(storage);

        System.out.println("Clear");
        storage.clear();
        printAll(storage);

        System.out.println("Size: " + storage.size());
        System.out.println("----------------");
    }

    static void printAll(Storage storage) {
        System.out.println("\nGet All");
        for (Resume r : storage.getAll()) {
            System.out.println(r);
        }
    }
}
