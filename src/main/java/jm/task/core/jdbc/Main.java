package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import static jm.task.core.jdbc.util.Util.buildSessionFactory;

public class Main {

    public static void main(String[] args) {
        buildSessionFactory();
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Рулон", "Обоев", (byte) 32);
        userService.saveUser("Ушат", "Помоев", (byte) 20);
        userService.saveUser("Улов", "Налимов", (byte) 63);
        userService.saveUser("Букет", "Левкоев", (byte) 45);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.shutdown();
    }
}