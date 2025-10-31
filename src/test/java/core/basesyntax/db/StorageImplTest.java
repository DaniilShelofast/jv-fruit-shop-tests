package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageImplTest {
    private Map<String, Integer> map;
    private Storage storage;

    @BeforeEach
    void setUp() {
        map = new LinkedHashMap<>();
        storage = new StorageImpl(map);
    }

    @Test
    void getFruit_notNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.getFruit(null);
        });
    }

    @Test
    void getFruit_notEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.getFruit(" ");
        });
    }

    @Test
    void add_notNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.add(null, 10);
        });
    }

    @Test
    void add_notEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.add(" ", 10);
        });
    }

    @Test
    void add_quantityNegative_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.add("apple", -1);
        });
    }

    @Test
    void set_notNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.set(null, 10);
        });
    }

    @Test
    void set_notEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.set(" ", 10);
        });
    }

    @Test
    void set_quantityNegative_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.set("apple", -1);
        });
    }

    @Test
    void delete_notNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.delete(null, 10);
        });
    }

    @Test
    void delete_notEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storage.delete(" ", 10);
        });
    }

    @Test
    void delete_quantityNegative_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.delete("apple", -1);
        });
    }

    @Test
    void delete_resultMoreBalance_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.getOperationType("b"),
                "banana", Integer.parseInt("100"));
        storage.add("banana", 10);
        assertThrows(RuntimeException.class, () -> {
            storage.delete(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        });
    }

}
