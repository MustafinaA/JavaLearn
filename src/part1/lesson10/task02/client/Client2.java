package part1.lesson10.task02.client;

import part1.lesson10.task02.MyProperties;

/**
 * Клиент с именем Client2
 *
 * @author Alina
 * @version 1.0
 */
public class Client2 {
    public static void main(String[] args) {
        new ClientImpl("Client2", MyProperties.SERVER_PORT, MyProperties.ADDRESS);
    }
}
