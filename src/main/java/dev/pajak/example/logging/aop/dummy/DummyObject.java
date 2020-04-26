package dev.pajak.example.logging.aop.dummy;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor(staticName = "create")
@ToString
public class DummyObject {
  private String stringField;
  private int intField;
}
