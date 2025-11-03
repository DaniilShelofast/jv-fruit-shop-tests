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
    void getFruit_notNullFoundFruit_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.getFruit(null);
        });
    }

    @Test
    void getFruit_notEmptyFoundFruit_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.getFruit(" ");
        });
    }

    @Test
    void add_keyNull_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.add(null, 10);
        });
    }

    @Test
    void add_keyFruitEmpty_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.add(" ", 10);
        });
    }

    @Test
    void add_valueNumberNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.add("apple", -1);
        });
    }

    @Test
    void set_keyFruitIsNull_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.set(null, 10);
        });
    }

    @Test
    void set_keyFruitEmpty_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.set(" ", 10);
        });
    }

    @Test
    void set_valueNumberNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.set("apple", -1);
        });
    }

    @Test
    void delete_keyIsNull_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.delete(null, 10);
        });
    }

    @Test
    void delete_keyFruitIsEmpty_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            storage.delete(" ", 10);
        });
    }

    @Test
    void delete_valueNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            storage.delete("apple", -1);
        });
    }

    @Test
    void delete_resultMoreBalance_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.getOperationType("b"),
                "banana", Integer.parseInt("100"));
        storage.add("banana", 10);
        assertThrows(RuntimeException.class, () -> {
            storage.delete(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        });
    }

}
