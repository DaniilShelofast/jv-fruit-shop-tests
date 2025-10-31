package core.basesyntax.service.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {
    private WriteFileFruit writeFileFruit;
    private Path files;

    @BeforeEach
    void setUp() throws IOException {
        writeFileFruit = new WriteFileFruitImpl();
        files = Files.createTempFile(Path.of("src/test/resources/"), "temp_", ".csv");

    }

    @Test
    void writeAll_writeFileEqualsReadFile_Ok() throws IOException {
        String content = "Hello World!" + System.lineSeparator() + "Hello Ukraine.";
        writeFileFruit.writeAll(content, files.toString());
        String readFile = Files.readString(files);
        assertEquals(content, readFile, "File content and order do not match");
    }

    @Test
    void writeAll_writeFileFruit_notNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writeFileFruit.writeAll(null, files.toString());
        });
    }

    @Test
    void writeAll_writeFileFruit_notEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            writeFileFruit.writeAll(" ", files.toString());
        });
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(files);
    }
}
