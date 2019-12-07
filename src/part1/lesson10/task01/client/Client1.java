package part1.lesson10.task01.client;

import part1.lesson10.task01.MyProperties;

/**
 * Клиент с именем Client1
 * @author Alina
 * @version 1.0
 */
public class Client1 {
    public static void main(String[] args) {
       new ClientImpl("Client1", MyProperties.SERVER_PORT, MyProperties.ADDRESS);
    }
}
