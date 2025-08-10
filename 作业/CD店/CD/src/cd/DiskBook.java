package cd;

import java.util.Arrays;
import java.util.LinkedList;

public class DiskBook {
    private Disk[] data = new Disk[1000];

    public void addDisk(Disk d) {
        int id = d.getId();
        Disk disk = findDisk(id);
        if (disk == null) {
            data[id] = d;
        } else {
            int num = disk.getNum();
            num += d.getNum();
            disk.setNum(num);
        }
    }

    public boolean subDisk(int id, int number) {
        if (data[id] == null) {
            System.out.println("There is no disk for " + id);
            return false;
        } else {
            if (data[id].getNum() - number >= 0) {
                data[id].setNum(data[id].getNum() - number);
                return true;
            } else {
                System.out.println("The num is not enough");
                return false;
            }
        }
    }

    public void print() {
        for (Disk disk : data) {
            if (disk != null) {
                System.out.println(disk);
            }
        }
    }

    public int sumOfMoney(int id, int number) {
        if (data[id] != null) {
            return data[id].getPrice() * number;
        } else {
            System.out.println("There is no disk for " + id);
            return 0;
        }
    }

    public boolean removeDisk(int id) {
        if (data[id] == null) {
            System.out.println("This id is null, without disk!");
            return false;
        }
        data[id] = null;
        return true;
    }

    public Disk findDisk(int id) {
        return data[id];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Disk disk : data) {
            if (disk != null) {
                result.append(disk).append("\n");
            }
        }
        return result.toString();
    }
}
