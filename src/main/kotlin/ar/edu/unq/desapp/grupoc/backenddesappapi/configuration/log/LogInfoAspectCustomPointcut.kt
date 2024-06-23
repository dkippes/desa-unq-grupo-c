package ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.log

import jakarta.annotation.PostConstruct
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.File

@Aspect
@Component
@Order(0)
class LogInfoAspectCustomPointcut {
    companion object {
        private val logger = LoggerFactory.getLogger(LogInfoAspectCustomPointcut::class.java)
    }

    @Pointcut("execution(* ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.*.*(..))")
    fun methodsStarterServicePointcut() {
        // Este método está vacío porque su única función es definir el punto de corte
        // para la anotación @Pointcut. No necesita implementación adicional.
    }

    @PostConstruct
    fun initializeLogFile() {
        println("Initializing log file deletion process...")
        val logFilePath = "./logs/spring-boot-logger.log"
        val logFile = File(logFilePath)
        if (logFile.exists()) {
            val success = logFile.createNewFile()
            if (success) {
                println("Log file deleted successfully.")
            } else {
                println("Failed to delete log file.")
            }
        } else {
            println("No log file exists.")
        }
    }

    @Before("methodsStarterServicePointcut()")
    @Throws(Throwable::class)
    fun beforeMethods() {
        logger.info("/////// LogInfoAspectCustomPointcut - BEFORE POINTCUT /////")
    }

    @After("methodsStarterServicePointcut()")
    @Throws(Throwable::class)
    fun afterMethods() {
        logger.info("/////// LogInfoAspectCustomPointcut - AFTER POINTCUT /////")
    }
}