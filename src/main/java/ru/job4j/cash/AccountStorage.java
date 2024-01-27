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
            accounts.put(account.id(), account);
            return accounts.containsKey(account.id());
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            boolean result = false;
            if (accounts.containsKey(account.id())) {
                accounts.put(account.id(), account);
                result = true;
            }
            return result;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            if (accounts.containsKey(id)) {
                accounts.put(id, null);
            } else {
                throw new IllegalStateException("Not found account by id = " + id);
            }
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            if (!accounts.containsKey(fromId) || !accounts.containsKey(toId)) {
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