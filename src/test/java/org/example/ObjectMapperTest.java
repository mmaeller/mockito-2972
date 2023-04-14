package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {

  @Test
  void spyObjectMapperWithJavaTimeModule() {
    var objectMapper = spy(new ObjectMapper().registerModule(new JavaTimeModule()));
    var reference = new AtomicReference<Dummy>();
    assertDoesNotThrow(
        () ->
            reference.set(
                objectMapper.readValue(
                    """
          {
          "timestamp": "2023-04-13T09:00:00"
          }
          """,
                    Dummy.class)));
    assertEquals(LocalDateTime.of(2023, 4, 13, 9, 0), reference.get().timestamp());
  }

  record Dummy(LocalDateTime timestamp) {}
}
