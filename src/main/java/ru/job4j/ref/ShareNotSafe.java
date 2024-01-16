package ru.job4j.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("main");
        User user2 = User.of("main2");
        User user3 = User.of("main3");
        cache.add(user);
        cache.add(user2);
        cache.add(user3);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("first");
                    }

                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("second");
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(cache.findById(1).getName());
        System.out.println(cache.findById(2).getName());
        System.out.println(cache.findById(3).getName());
        System.out.println(cache.findAll().get(0).getName());
        System.out.println(cache.findAll().get(1).getName());
        System.out.println(cache.findAll().get(2).getName());
    }
}