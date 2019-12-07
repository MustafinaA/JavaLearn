package part1.lesson10.task02.client;

import part1.lesson10.task02.MyProperties;

/**
 * Клиент с именем Client3
 *
 * @author Alina
 * @version 1.0
 */
public class Client3 {
    public static void main(String[] args) {
        new ClientImpl("Client3", MyProperties.SERVER_PORT, MyProperties.ADDRESS);
    }
}
