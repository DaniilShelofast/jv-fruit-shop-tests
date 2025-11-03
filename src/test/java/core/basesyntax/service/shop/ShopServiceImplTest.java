package core.basesyntax.service.shop;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.FruitTransactionHandler;
import core.basesyntax.service.strategy.FruitTransactionStrategy;
import core.basesyntax.service.strategy.FruitTransactionStrategyImpl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private Map<Operation, FruitTransactionHandler> map;
    private FruitTransactionStrategy fruitTransactionStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        map = new LinkedHashMap<>();
        fruitTransactionStrategy = new FruitTransactionStrategyImpl(map);
        shopService = new ShopServiceImpl(fruitTransactionStrategy);
    }

    @Test
    void process_isNull_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            shopService.process(null);
        });
    }

    @Test
    void process_fruitNameEmpty_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new
                FruitTransaction(Operation.getOperationType("s"), " ", Integer.parseInt("100"));
        List<FruitTransaction> fruitTransactions = List.of(fruitTransaction);
        assertThrows(RuntimeException.class, () -> {
            shopService.process(fruitTransactions);
        });
    }

    @Test
    void process_fruitQuantityNegative_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new
                FruitTransaction(Operation.getOperationType("s"), "apple", Integer.parseInt("-1"));
        List<FruitTransaction> fruitTransactions = List.of(fruitTransaction);
        assertThrows(RuntimeException.class, () -> {
            shopService.process(fruitTransactions);
        });
    }
}
