package core.basesyntax.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private Path tempFiles;
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() throws IOException {
        dataConverter = new DataConverterImpl();
        tempFiles = Files.createTempFile(Path.of("src/test/resources/"), "temp_", ".csv");
    }

    @Test
    void convertAll_validLine_Ok() throws IOException {
        List<String> fruits = List.of("type,fruit,quantity",
                "b,banana,100");
        Path tempWrite = Files.write(tempFiles, fruits);
        List<String> readFile = Files.readAllLines(tempWrite);

        List<FruitTransaction> fruitTransactions = dataConverter.convertAll(readFile);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.getOperationType("b"),
                        "banana", Integer.parseInt("100"));
        assertEquals(fruitTransaction, fruitTransactions.get(0));
    }

    @Test
    void convertAll_invalidLineLength_throwsRuntimeException() throws IOException {
        List<String> fruits = List.of("type,fruit,quantity",
                "banana,100");
        Path tempWrite = Files.write(tempFiles, fruits);
        List<String> readFile = Files.readAllLines(tempWrite);
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertAll(readFile);
        });
    }

    @Test
    void convertAll_invalidQuantityFormat_throwsRuntimeException() throws IOException {
        List<String> fruits = List.of("type,fruit,quantity",
                "b,banana,apple");
        Path tempWrite = Files.write(tempFiles, fruits);
        List<String> readFile = Files.readAllLines(tempWrite);
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertAll(readFile);
        });
    }

    @Test
    void convertAll_negativeQuantity_throwsRuntimeException() throws IOException {
        List<String> fruits = List.of("type,fruit,quantity",
                "b,banana,-1");
        Path tempWrite = Files.write(tempFiles, fruits);
        List<String> readFile = Files.readAllLines(tempWrite);
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertAll(readFile);
        });
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(tempFiles);
    }
}
