package ar.edu.unq.desapp.grupoc.backenddesappapi.configuration

import org.apache.commons.logging.LogFactory
import org.ehcache.event.CacheEvent
import org.ehcache.event.CacheEventListener


class CacheEventLogger : CacheEventListener<Any, Any> {
    protected val logger = LogFactory.getLog(javaClass)
    override fun onEvent(
        cacheEvent: CacheEvent<out Any, out Any>
    ) {
        logger.info("UpdateCache | KEY: " + cacheEvent.key + " | OLDVALUE: " + cacheEvent.oldValue + " | NEWVALUE: " + cacheEvent.newValue)
    }
}