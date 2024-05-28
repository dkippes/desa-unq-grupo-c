package ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.log

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
@Order(2)
class LogExecutionTimeAspectAnnotation {
    companion object {
        private val logger = LoggerFactory.getLogger(LogExecutionTimeAspectAnnotation::class.java)
    }

    @Around("@annotation(ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.log.LogExecutionTime)")
    @Throws(Throwable::class)
    fun logExecutionTimeAnnotation(joinPoint: ProceedingJoinPoint): Any? {
        logger.info("/////// LogExecutionTimeAspectAnnotation - AROUND START  logExecutionTime annotation //////")

        val start = System.currentTimeMillis()
        for (arg in joinPoint.args) {
            logger.info("/////// LogExecutionTimeAspectAnnotation - ARGUMENT: ${arg.toString()}")
        }
        val proceed: Any?
        try {
            proceed = joinPoint.proceed()
        } catch (e: Exception) {
            logger.error("/////// LogExecutionTimeAspectAnnotation - EXCEPTION: ${e.message}")
            val executionTime = System.currentTimeMillis() - start
            logger.info("/////// LogExecutionTimeAspectAnnotation - ${joinPoint.signature} executed in ${executionTime}ms ")
            logger.info("/////// LogExecutionTimeAspectAnnotation - AROUND FINISH  logExecutionTime annotation ///////")
            throw e
        }
        val executionTime = System.currentTimeMillis() - start

        logger.info("/////// LogExecutionTimeAspectAnnotation - ${joinPoint.signature} executed in ${executionTime}ms ")
        logger.info("/////// LogExecutionTimeAspectAnnotation - RETURN: $proceed.toString()")
        logger.info("/////// LogExecutionTimeAspectAnnotation - AROUND FINISH  logExecutionTime annotation ///////")

        return proceed
    }
}