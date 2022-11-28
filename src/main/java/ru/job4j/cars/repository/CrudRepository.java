package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
public class CrudRepository {

    private final SessionFactory sessionFactory;

    public void run(Consumer<Session> command) {
        executeTransaction(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sq = session
                    .createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    public <T> List<T> query(String query, Class<T> tClass) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, tClass)
                .list();
        return executeTransaction(command);
    }

    public <T> Optional<T> optional(String query, Class<T> tClass, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session
                    .createQuery(query, tClass);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return executeTransaction(command);
    }

    public <T> List<T> query(String query, Class<T> tClass, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, tClass);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return executeTransaction(command);
    }

    public <T> T executeTransaction(Function<Session, T> command) {
        var session = sessionFactory.openSession();
        try (session) {
            var transaction = session.beginTransaction();
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (Exception exception) {
            var transaction = session.getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }
}
