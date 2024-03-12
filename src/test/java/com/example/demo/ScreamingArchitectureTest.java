package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

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

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AnalyzeClasses(
        packages = "com.example.demo.screamingarchitecture",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class ScreamingArchitectureTest {

    private static final String[] SHARED_PACKAGES = {
        "com.example.demo.screamingarchitecture.configuration.."
    };

    // Every class related to a business object should be under the package
    // com.example.demo.screamingarchitecture.<business-object>
    private static final String BUSINESS_OBJECT_PACKAGE_REGEX =
            "^com.example.demo.screamingarchitecture\\.[a-z]+";

    @Test
    public void each_package_should_be_isolated() {

        JavaClasses classes =
                new ClassFileImporter().importPackages("com.example.demo.screamingarchitecture");

        ArchRule rule =
                classes()
                        .that()
                        .resideInAPackage("com.example.demo.screamingarchitecture..")
                        .and()
                        .resideOutsideOfPackages(SHARED_PACKAGES)
                        .should(
                                new ArchCondition<>(
                                        "should not be called outside of its root package") {
                                    @Override
                                    public void check(
                                            JavaClass javaClass, ConditionEvents conditionEvents) {
                                        String classPackage = javaClass.getPackageName();

                                        Pattern pattern =
                                                Pattern.compile(BUSINESS_OBJECT_PACKAGE_REGEX);
                                        Matcher matcher = pattern.matcher(classPackage);
                                        if (!matcher.find()) {
                                            return;
                                        }
                                        String businessObjectPackageName = matcher.group();

                                        boolean isClassUsedOnlyInsideItsBusinessObjectPackage =
                                                javaClass.getDirectDependenciesToSelf().stream()
                                                        .allMatch(
                                                                dependency ->
                                                                        dependency
                                                                                .getOriginClass()
                                                                                .getPackageName()
                                                                                .contains(
                                                                                        businessObjectPackageName));

                                        String errorMessage =
                                                String.format(
                                                        "The class '%s' is used outside its business"
                                                                + " object package.",
                                                        javaClass.getFullName());
                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        isClassUsedOnlyInsideItsBusinessObjectPackage,
                                                        errorMessage));
                                    }
                                });

        rule.check(classes);
    }
}
