package cd;

public class UserBook {
    private User[] data = new User[10000];

    public void addUser(User u) {
        data[u.getId()] = u;
    }

    public User findUser(int id) {
        return data[id];
    }

    public boolean removeUser(int id) {
        if (data[id] == null) {
            System.out.println("This id is null, without person!");
            return false;
        }
        data[id] = null;
        return true;
    }

    public boolean borrow(int id, int money) {
        if (data[id] == null) {
            System.out.println("There is no person for " + id);
            return false;
        } else {
            if (data[id].getMoney() - money >= 0) {
                data[id].setMoney(data[id].getMoney() - money);
                return true;
            } else {
                System.out.println("The money is not enough");
                return false;
            }
        }
    }

    public void print() {
        for (User user : data) {
            if (user != null) {
                System.out.println(user);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (User user : data) {
            if (user != null) {
                result.append(user).append("\n");
            }
        }
        return result.toString();
    }
}
