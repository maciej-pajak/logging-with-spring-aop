package dev.pajak.example.logging.aop;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Pointcut("@within(dev.pajak.example.logging.aop.LogExecutionDetails)")
  public void customAnnotationOnClass() {}

  @Pointcut("@annotation(dev.pajak.example.logging.aop.LogExecutionDetails)")
  public void customAnnotationOnMethod() {}

  @Around("customAnnotationOnClass() && @within(annotation)")
  public Object logClassExecutionDetails(ProceedingJoinPoint joinPoint, LogExecutionDetails annotation) throws Throwable {
    return logExecutionDetails(joinPoint, annotation);
  }

  @Around("customAnnotationOnMethod() && @annotation(annotation)")
  public Object logMethodExecutionDetails(ProceedingJoinPoint joinPoint, LogExecutionDetails annotation) throws Throwable {
    return logExecutionDetails(joinPoint, annotation);
  }

  private Object logExecutionDetails(ProceedingJoinPoint joinPoint, LogExecutionDetails annotation) throws Throwable {
    if (log.isDebugEnabled()) {
      final Stopwatch stopWatch = Stopwatch.createStarted();
      Object result = null;
      Throwable caughtThrowable = null;
      try {
        result = joinPoint.proceed();
        return result;
      } catch (Throwable throwable) {
        caughtThrowable = throwable;
        throw throwable;
      } finally {
        stopWatch.stop();
        log.debug("Finished executing {} with args {} in {} ms, result: {}",
            StructuredArguments.kv("methodName", getName(joinPoint)),
            joinPoint.getArgs(),
            StructuredArguments.kv("executionTime", stopWatch.elapsed(TimeUnit.MILLISECONDS)),
            annotation.includeResult() ? result : "<result logging disabled>",
            caughtThrowable);
      }
    } else {
      return joinPoint.proceed();
    }
  }

  private String getName(ProceedingJoinPoint joinPoint) {
    return joinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + joinPoint.getSignature().getName();
  }

}
