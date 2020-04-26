package dev.pajak.example.logging.aop.dummy;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.pajak.example.logging.aop.LogExecutionDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@LogExecutionDetails
public class DummyController {

  private final DummyService dummyService;

  @GetMapping("/test")
  public String test(@RequestParam(required = false) String param1) {
    Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
    dummyService.annotatedMethod(DummyObject.create("value", 10));
    dummyService.nonAnnotatedMethod();
    return "OK";
  }

}
