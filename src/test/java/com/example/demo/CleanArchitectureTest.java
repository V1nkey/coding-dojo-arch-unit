package com.example.demo;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
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

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
        packages = "com.example.demo.cleanarchi",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class CleanArchitectureTest {

    @ArchTest
    private static final ArchRule cleanArchitecureLayersAreRespected =
            layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()
                    .layer("application")
                    .definedBy("com.example.demo.cleanarchi.application..")
                    .layer("domain")
                    .definedBy("com.example.demo.cleanarchi.domain..")
                    .layer("usecases")
                    .definedBy("com.example.demo.cleanarchi.domain.usecases..")
                    .layer("entities")
                    .definedBy("com.example.demo.cleanarchi.domain.model..")
                    .layer("infrastructure")
                    .definedBy("com.example.demo.cleanarchi.infrastructure..")
                    .whereLayer("application")
                    .mayNotBeAccessedByAnyLayer()
                    .whereLayer("domain")
                    .mayNotAccessAnyLayer();

    @Test
    public void usecase_methods_should_be_called_execute() {
        JavaClasses classes =
                new ClassFileImporter()
                        .importPackages("com.example.demo.cleanarchi.domain.usecases");

        ArchRule rule =
                ArchRuleDefinition.classes()
                        .that()
                        .areNotInterfaces()
                        .and()
                        .resideOutsideOfPackage("..interactors..")
                        .should(
                                new ArchCondition<>("should have a public method called execute") {
                                    @Override
                                    public void check(
                                            JavaClass javaClass, ConditionEvents conditionEvents) {
                                        boolean conditionSatisfied =
                                                javaClass.getMethods().stream()
                                                        .allMatch(
                                                                javaMethod ->
                                                                        javaMethod
                                                                                .getName()
                                                                                .equals("execute"));

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        conditionSatisfied,
                                                        String.format(
                                                                "The class %s has a method not called execute",
                                                                javaClass.getName())));
                                    }
                                });

        rule.check(classes);
    }

    @Test
    public void interactors_should_be_used_in_their_context() {
        JavaClasses classes =
                new ClassFileImporter()
                        .importPackages("com.example.demo.cleanarchi.domain.usecases");

        ArchRule rule =
                ArchRuleDefinition.classes()
                        .that()
                        .resideInAPackage("..interactors..")
                        .should(
                                new ArchCondition<>("should be used in their context") {
                                    @Override
                                    public void check(
                                            JavaClass javaClass, ConditionEvents conditionEvents) {
                                        String interactorPackageName = javaClass.getPackageName();
                                        boolean conditionSatisfied =
                                                javaClass.getDirectDependenciesToSelf().stream()
                                                        .allMatch(
                                                                dependency -> {
                                                                    String dependencyPackageName =
                                                                            dependency
                                                                                    .getOriginClass()
                                                                                    .getPackageName();
                                                                    return interactorPackageName
                                                                            .contains(
                                                                                    dependencyPackageName);
                                                                });

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        conditionSatisfied,
                                                        String.format(
                                                                "The class %s is used outside its context",
                                                                javaClass.getName())));
                                    }
                                });
        rule.check(classes);
    }

    @ArchTest
    private static final ArchRule interfaces_defined_in_domain_layer_should_be_suffixed_by_Port =
            ArchRuleDefinition.classes()
                    .that()
                    .areInterfaces()
                    .and()
                    .resideInAPackage("com.example.demo.cleanarchi.domain.usecases")
                    .should()
                    .haveNameMatching(".*Port");

    @Test
    public void classes_implementing_a_port_should_be_suffixed_by_Adapter() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.demo.cleanarchi");

        DescribedPredicate<JavaClass> implementsAPort =
                new DescribedPredicate<>("have a field annotated with @Payload") {
                    @Override
                    public boolean test(JavaClass javaClass) {
                        boolean implementsAPort =
                                javaClass.getInterfaces().stream()
                                        .anyMatch(
                                                javaInterface ->
                                                        javaInterface.getName().matches(".*Port"));
                        return implementsAPort;
                    }
                };

        ArchRule rule =
                ArchRuleDefinition.classes()
                        .that(implementsAPort)
                        .should()
                        .haveNameMatching(".*Adapter");

        rule.check(classes);
    }
}
