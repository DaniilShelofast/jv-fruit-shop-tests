package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyFruitTransactionHandlerTest {
    private Map<String, Integer> map;
    private Storage storage;
    private FruitTransactionHandler fruitTransactionHandler;

    @BeforeEach
    void setUp() {
        map = new LinkedHashMap<>();
        storage = new StorageImpl(map);
        fruitTransactionHandler = new SupplyFruitTransactionHandler(storage);
    }

    @Test
    void handler_supplyFruitTransactionNull_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionHandler.handler(null);
        });
    }

    @Test
    void handler_supplyFruitNameIsEmpty_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new
                FruitTransaction(Operation.getOperationType("s"), " ", Integer.parseInt("100"));
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionHandler.handler(fruitTransaction);
        });
    }

    @Test
    void handler_supplyFruitNameIsNull_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new
                FruitTransaction(Operation.getOperationType("s"), null, Integer.parseInt("100"));
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionHandler.handler(fruitTransaction);
        });
    }

    @Test
    void handler_supplyFruitQuantityNegative_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.getOperationType("s"),
                "banana", Integer.parseInt("-1"));
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionHandler.handler(fruitTransaction);
        });
    }
}
