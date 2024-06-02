package org.example.productservice.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(value = 1)
public class LockAspect {
    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(org.example.productservice.redisson.DistributedLock)")
    // ProceedingJoinPoint : 메소드를 가져올 수 있음
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = distributedLock.value();
//        String dynamicKey = generateDynamicKey(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.value()+"id");

        RLock rLock = redissonClient.getLock(key);

        log.info("[{} 잠금 시도]", key);
        try {
            boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) {
                log.info("[{} 잠금 실패]", key);
                throw new RuntimeException("Redisson Lock을 획득하지 못했습니다.");
            }

            log.info("[{} 잠금 성공]", key);
            return aopForTransaction.proceed(joinPoint);
        } finally {
            try {
                log.info("[{} 잠금 해제]", key);
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("{}{}", e, key);
            }
        }
    }


}