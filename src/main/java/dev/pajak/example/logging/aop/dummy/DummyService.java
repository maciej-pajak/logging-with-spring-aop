package dev.pajak.example.logging.aop.dummy;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.pajak.example.logging.aop.LogExecutionDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DummyService {

  @LogExecutionDetails(includeResult = true)
  public String annotatedMethod(DummyObject param) {
    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    return "annotatedMethodResult";
  }

  public String nonAnnotatedMethod() {
    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    return "nonAnnotatedMethodResult";
  }

}
