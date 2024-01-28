package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            if (getById(fromId).isEmpty() || getById(toId).isEmpty()) {
                return false;
            }
            if (accounts.get(fromId).amount() < amount) {
                return false;
            }
            accounts.put(fromId, new Account(fromId, accounts.get(fromId).amount() - amount));
            accounts.put(toId, new Account(toId, accounts.get(toId).amount() + amount));
            return true;
        }
    }
}