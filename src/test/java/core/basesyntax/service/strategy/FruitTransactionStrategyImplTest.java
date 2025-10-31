package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionStrategyImplTest {
    private Map<Operation, FruitTransactionHandler> map;
    private FruitTransactionStrategy fruitTransactionStrategy;

    @BeforeEach
    void setUp() {
        map = new LinkedHashMap<>();
        fruitTransactionStrategy = new FruitTransactionStrategyImpl(map);
    }

    @Test
    void get_notNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionStrategy.get(null);
        });
    }

    @Test
    void get_notEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionStrategy.get(Operation.getOperationType(" "));
        });
    }

    @Test
    void get_notCorrectOperationType_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionStrategy.get(Operation.getOperationType("G"));
        });
    }

    @Test
    void get_notNullOperationType_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionStrategy.get(Operation.getOperationType(null));
        });
    }
}
