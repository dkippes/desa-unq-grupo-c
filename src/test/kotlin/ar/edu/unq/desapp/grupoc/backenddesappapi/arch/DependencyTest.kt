package ar.edu.unq.desapp.grupoc.backenddesappapi.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class DependencyTest {
    private var baseClasses: JavaClasses? = null

    @BeforeEach
    fun setup() {
        baseClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.edu.unq.desapp.grupoc.backenddesappapi")
    }


    @Test
    fun exceptionsClassesShouldEndWithException() {
        classes().that().resideInAPackage("..exceptions")
            .should().haveSimpleNameEndingWith("Exception").check(baseClasses)
    }

    @Test
    fun persistanceClassesShouldEndWithRepo() {
        classes().that().resideInAPackage("..persistence..")
            .should().haveSimpleNameEndingWith("Repository").check(baseClasses)
    }


    @Test
    fun webserviceClassesShouldEndWithController() {
        classes().that().resideInAPackage("..webservice")
            .should().haveSimpleNameEndingWith("Controller").check(baseClasses)
    }

    @Test
    fun layeredArchitectureShouldBeRespected() {
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..webservice..")
            .layer("Service").definedBy("..service..")
            .layer("Persistence").definedBy("..persistence..")

            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
    }


    @Test
    fun controllerClassesShouldHaveSpringControllerAnnotation() {
        classes().that().resideInAPackage("..webservice")
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .check(baseClasses)
    }
}