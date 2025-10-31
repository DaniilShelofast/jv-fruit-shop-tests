package core.basesyntax.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private Map<String, Integer> map;
    private Storage storage;
    private ReportGenerator reportGenerator;
    private Path tempFiles;

    @BeforeEach
    void setUp() throws IOException {
        map = new LinkedHashMap<>();
        storage = new StorageImpl(map);
        reportGenerator = new ReportGeneratorImpl(storage);
        tempFiles = Files.createTempFile(Path.of("src/test/resources/"), "temp_", ".csv");
    }

    @Test
    void getReport_shouldGenerateCorrectReport_Ok() throws IOException {
        storage.add("banana", 100);
        storage.add("apple", 50);
        String report = reportGenerator.getReport();
        Path tempWrite = Files.writeString(tempFiles, report);
        List<String> readFile = Files.readAllLines(tempWrite);
        List<String> expected = List.of("fruit,quantity",
                "banana,100",
                "apple,50");
        assertEquals(expected, readFile);
    }

    @Test
    void getReport_emptyInventory_headerOnly_Ok() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity", report.trim());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(tempFiles);
    }
}
