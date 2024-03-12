package com.example.demo;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(
        packages = "com.example.demo.cleanarchitecture",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class CleanArchitectureTest {

    @ArchTest
    private static final ArchRule cleanArchitectureLayersAreRespected =
            Architectures.layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()
                    .layer("application")
                    .definedBy("com.example.demo.cleanarchitecture.application..")
                    .layer("domain")
                    .definedBy("com.example.demo.cleanarchitecture.domain..")
                    .layer("infrastructure")
                    .definedBy("com.example.demo.cleanarchitecture.infrastructure..")
                    .whereLayer("domain")
                    .mayNotAccessAnyLayer()
                    .whereLayer("application")
                    .mayNotBeAccessedByAnyLayer();

    @Test
    void usecases_should_have_a_method_called_execute() {
        JavaClasses classes =
                new ClassFileImporter()
                        .importPackages("com.example.demo.cleanarchitecture.domain..");

        ArchRule rule =
                ArchRuleDefinition.classes()
                        .that()
                        .resideInAPackage("..usecases..")
                        .and()
                        .areNotInterfaces()
                        .and()
                        .resideOutsideOfPackage("..interactors..")
                        .should(
                                new ArchCondition<JavaClass>("have a method called execute") {
                                    @Override
                                    public void check(
                                            JavaClass javaClass, ConditionEvents conditionEvents) {

                                        boolean hasOnlyOnePublicMethod =
                                                javaClass.getMethods().stream()
                                                                .filter(
                                                                        javaMethod ->
                                                                                javaMethod
                                                                                        .getModifiers()
                                                                                        .contains(
                                                                                                JavaModifier
                                                                                                        .PUBLIC))
                                                                .toList()
                                                                .size()
                                                        == 1;

                                        boolean isConditionOk =
                                                javaClass.getMethods().stream()
                                                        .anyMatch(
                                                                javaMethod ->
                                                                        javaMethod
                                                                                .getName()
                                                                                .equals("execute"));

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        hasOnlyOnePublicMethod && isConditionOk,
                                                        String.format(
                                                                "The class %s has not a method called execute",
                                                                javaClass)));
                                    }
                                });

        rule.check(classes);
    }

    @Test
    void interactor_should_be_used_in_its_context() {
        JavaClasses classes =
                new ClassFileImporter()
                        .importPackages("com.example.demo.cleanarchitecture.domain..");

        ArchRule rule =
                ArchRuleDefinition.classes()
                        .that()
                        .resideInAPackage("..interactors..")
                        .should(
                                new ArchCondition<JavaClass>("be used in their context") {
                                    @Override
                                    public void check(
                                            JavaClass interactor, ConditionEvents conditionEvents) {
                                        String interactorPackage = interactor.getPackageName();
                                        boolean conditionOk =
                                                interactor.getDirectDependenciesToSelf().stream()
                                                        .allMatch(
                                                                dependency -> {
                                                                    String dependencyPackage =
                                                                            dependency
                                                                                    .getOriginClass()
                                                                                    .getPackageName();
                                                                    return interactorPackage
                                                                            .contains(
                                                                                    dependencyPackage);
                                                                });

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        interactor,
                                                        conditionOk,
                                                        String.format(
                                                                "The class %s is called outside its context",
                                                                interactor.getName())));
                                    }
                                });

        rule.check(classes);
    }

    @ArchTest
    private static ArchRule domainInterfacesAreSuffixedByPort =
            ArchRuleDefinition.classes()
                    .that()
                    .areInterfaces()
                    .and()
                    .resideInAPackage("..domain..")
                    .should()
                    .haveNameMatching(".*Port");
}
