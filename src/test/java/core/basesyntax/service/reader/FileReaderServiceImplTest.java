package core.basesyntax.service.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private ReadFileFruit readFileFruit;
    private Path files;

    @BeforeEach
    void setUp() throws IOException {
        readFileFruit = new ReadFileFruitImpl();
        files = Files.createTempFile(Path.of("src/test/resources/"), "temp_", ".csv");
    }

    @Test
    void readAll_readFileSeveralLines_Ok() throws IOException {
        List<String> list = List.of("Hello World!",
                "Hello Ukraine.");
        Path tempWrite = Files.write(files, list);
        List<String> lines = readFileFruit.readAll(tempWrite.toString());
        assertEquals(list, lines, "File content and order do not match");
    }

    @Test
    void readAll_readFileEmpty_Ok() {
        List<String> lines = readFileFruit.readAll(files.toString());
        assertEquals(0, lines.size(), "File should return an empty list");
    }

    @Test
    void readAll_readFileInvalidPath_notOk() {
        String filePath = "src/test/resources/readFileInvalid.csv";
        assertThrows(RuntimeException.class, () -> {
            readFileFruit.readAll(filePath);
        });
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(files);
    }
}
