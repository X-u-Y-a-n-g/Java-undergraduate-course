package cd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Boss {
    private UserBook ub = new UserBook();
    private DiskBook db = new DiskBook();
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.begin();
    }

    public void begin() {
        while (true) {
            printMainMenu();
            int choice = readUserInput();
            switch (choice) {
                case 1:
                    userManage();
                    break;
                case 2:
                    diskManage();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void borrowdisk() {
        try {
            System.out.println("please tell your id");
            int id = Integer.parseInt(in.readLine());
            System.out.println("please tell me the disk id you want to borrow");
            int diskid = Integer.parseInt(in.readLine());
            System.out.println("number?");
            int number = Integer.parseInt(in.readLine());
            int money = db.sumOfMoney(diskid, number);
            if (db.subDisk(diskid, number) && ub.borrow(id, money)) {
                System.out.println("borrow disk successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returndisk() {
        try {
            System.out.println("please tell your id");
            int id = Integer.parseInt(in.readLine());
            System.out.println("please tell me the disk id you want to return");
            int diskid = Integer.parseInt(in.readLine());
            System.out.println("number?");
            int number = Integer.parseInt(in.readLine());
            if (db.subDisk(diskid, -number)) { // Assuming this is the correct method
                System.out.println("return disk successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void diskManage() {
        while (true) {
            printDiskMenu();
            int choice = readDiskInput();
            switch (choice) {
                case 1:
                    borrowdisk();
                    break;
                case 2:
                    returndisk();
                    break;
                case 3:
                    findDisk();
                    break;
                case 4:
                    adddisk();
                    break;
                case 5:
                    db.print();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void adddisk() {
        try {
            System.out.println("please tell me diskname");
            String name = in.readLine();
            System.out.println("id?");
            int id = Integer.parseInt(in.readLine());
            System.out.println("num?");
            int num = Integer.parseInt(in.readLine());
            System.out.println("price?");
            int price = Integer.parseInt(in.readLine());
            Disk disk = new Disk(id, name, price, num);
            db.addDisk(disk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findDisk() {
        System.out.println("id?");
        try {
            int id = Integer.parseInt(in.readLine());
            Disk disk = db.findDisk(id);
            System.out.println(disk);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private int readDiskInput() {
        try {
            String line = in.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void printDiskMenu() {
        System.out.println("1:borrow disk");
        System.out.println("2:return disk");
        System.out.println("3:find disk");
        System.out.println("4:add disk");
        System.out.println("5:print");
        System.out.println("6:quit");
    }

    private void userManage() {
        while (true) {
            printUserMenu();
            int choice = readUserInput();
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    removeUser();
                    break;
                case 3:
                    findUser();
                    break;
                case 4:
                    ub.print();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void findUser() {
        System.out.println("id?");
        try {
            int id = Integer.parseInt(in.readLine());
            User user = ub.findUser(id);
            System.out.println(user);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void removeUser() {
        try {
            System.out.println("please tell me id");
            int id = Integer.parseInt(in.readLine());
            if (ub.removeUser(id)) {
                System.out.println("Remove " + id + " successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUser() {
        try {
            System.out.println("please tell me username");
            String name = in.readLine();
            System.out.println("id?");
            int id = Integer.parseInt(in.readLine());
            System.out.println("money?");
            int money = Integer.parseInt(in.readLine());
            User user = new User(id, name, money);
            ub.addUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printUserMenu() {
        System.out.println("1:add user");
        System.out.println("2:remove user");
        System.out.println("3:find user");
        System.out.println("4:print");
        System.out.println("6:quit");
    }

    private int readUserInput() {
        try {
            String line = in.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void printMainMenu() {
        System.out.println("---------------------------");
        System.out.println("1:User Manage");
        System.out.println("2:Disk Manage");
        System.out.println("6:quit");
    }
}
