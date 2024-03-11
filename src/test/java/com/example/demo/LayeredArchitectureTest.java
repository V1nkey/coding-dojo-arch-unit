package com.example.demo;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
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
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tngtech.archunit.core.domain.JavaModifier.PUBLIC;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
        packages = "com.example.demo.cleanarchi",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class LayeredArchitectureTest {

    // Layers
    private static final String APPLICATION_LAYER = "application";
    private static final String DOMAIN_LAYER = "domain";
    private static final String MODEL_LAYER = "model";
    private static final String USECASES_LAYER = "usecases";
    private static final String INFRASTRUCTURE_LAYER = "infrastructure";

    // Path
    private static final String ROOT_PATH = "com.example.demo.cleanarchi";
    private static final String APPLICATION_PATH = ROOT_PATH + ".application..";
    private static final String DOMAIN_PATH = ROOT_PATH + ".domain..";
    private static final String MODEL_PATH = ROOT_PATH + ".domain.model..";
    private static final String USECASES_PATH = ROOT_PATH + ".domain.usecases..";
    private static final String INFRASTRUCTURE_PATH = ROOT_PATH + ".infrastructure..";

    // Classes
    private static final JavaClasses classes =
            new ClassFileImporter().importPackages("com.example.demo.cleanarchi");

    @ArchTest
    public static final ArchRule clean_architecture_layers_are_respected =
            layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()
                    .layer(APPLICATION_LAYER)
                    .definedBy(APPLICATION_PATH)
                    .layer(DOMAIN_LAYER)
                    .definedBy(DOMAIN_PATH)
                    .layer(MODEL_LAYER)
                    .definedBy(MODEL_PATH)
                    .layer(USECASES_LAYER)
                    .definedBy(USECASES_PATH)
                    .layer(INFRASTRUCTURE_LAYER)
                    .definedBy(INFRASTRUCTURE_PATH)
                    .whereLayer(APPLICATION_LAYER)
                    .mayNotBeAccessedByAnyLayer()
                    .whereLayer(DOMAIN_LAYER)
                    .mayOnlyBeAccessedByLayers(APPLICATION_LAYER, INFRASTRUCTURE_LAYER);

    @Test
    public void autre_methode_de_test() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.example.demo.cleanarchi");

        ArchRule rule =
                layeredArchitecture()
                        .consideringOnlyDependenciesInLayers()
                        .layer(APPLICATION_LAYER)
                        .definedBy(APPLICATION_PATH)
                        .layer(DOMAIN_LAYER)
                        .definedBy(DOMAIN_PATH)
                        .layer(MODEL_LAYER)
                        .definedBy(MODEL_PATH)
                        .layer(USECASES_LAYER)
                        .definedBy(USECASES_PATH)
                        .layer(INFRASTRUCTURE_LAYER)
                        .definedBy(INFRASTRUCTURE_PATH)
                        .whereLayer(APPLICATION_LAYER)
                        .mayNotBeAccessedByAnyLayer()
                        .whereLayer(DOMAIN_LAYER)
                        .mayOnlyBeAccessedByLayers(APPLICATION_LAYER, INFRASTRUCTURE_LAYER);

        rule.check(classes);
    }

    @Test
    public void les_usecase_ne_doivent_avoir_qu_une_methode_public_appelee_executer() {
        ArchRule rule =
                classes()
                        .that()
                        .areNotInterfaces()
                        .and()
                        .resideInAPackage(USECASES_PATH)
                        .and()
                        .resideOutsideOfPackage("..interactors..")
                        .should(
                                new ArchCondition<>(
                                        "les classes usecases ne doivent avoir qu'une méthode public qui s'appelle executer") {
                                    @Override
                                    public void check(
                                            JavaClass javaClass, ConditionEvents conditionEvents) {
                                        List<JavaMethod> publicMethods =
                                                javaClass.getMethods().stream()
                                                        .filter(
                                                                javaMethod ->
                                                                        javaClass
                                                                                .getModifiers()
                                                                                .contains(PUBLIC))
                                                        .toList();

                                        int nombreDeMethodesPublic = publicMethods.size();

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        nombreDeMethodesPublic == 1,
                                                        String.format(
                                                                "La classe %s a plus d'une méthode public",
                                                                javaClass.getName())));

                                        boolean methodeSAppelleExecuter =
                                                publicMethods
                                                        .getFirst()
                                                        .getName()
                                                        .equals("executer");

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        methodeSAppelleExecuter,
                                                        String.format(
                                                                "La méthode %s ne s'appelle pas executer",
                                                                publicMethods
                                                                        .getFirst()
                                                                        .getName())));
                                    }
                                });
        rule.check(classes);
    }

    @Test
    public void les_interactors_ne_doivent_pas_etre_appeles_en_dehors_de_leur_parent() {
        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAPackage("..interactors..")
                        .should(
                                new ArchCondition<>(
                                        "doivent être appelées en dehors du package parent") {
                                    @Override
                                    public void check(
                                            JavaClass javaClass, ConditionEvents conditionEvents) {
                                        String interactorPackageName =
                                                javaClass.getPackage().getName();

                                        boolean estAppeleEnDehorsDuPackageParent =
                                                javaClass.getDirectDependenciesToSelf().stream()
                                                        .anyMatch(
                                                                classeAppelantInteractor ->
                                                                        !classeAppelantInteractor
                                                                                .getOriginClass()
                                                                                .getPackageName()
                                                                                .contains(
                                                                                        interactorPackageName));

                                        conditionEvents.add(
                                                new SimpleConditionEvent(
                                                        javaClass,
                                                        estAppeleEnDehorsDuPackageParent,
                                                        String.format(
                                                                "L'interactor %s est appelé en dehors de son package parent",
                                                                javaClass.getName())));
                                    }
                                });

        rule.check(classes);
    }

    @ArchTest
    private static final ArchRule test =
            classes()
                    .that()
                    .resideInAPackage("..interactors..")
                    .should(
                            new ArchCondition<>(
                                    "doivent uniquement être appelées dans son package parent") {
                                @Override
                                public void check(
                                        JavaClass javaClass, ConditionEvents conditionEvents) {
                                    String interactorPackageName = javaClass.getPackage().getName();

                                    boolean estAppeleDansLePackageParent =
                                            javaClass.getDirectDependenciesToSelf().stream()
                                                    .allMatch(
                                                            classeAppelantInteractor -> {
                                                                String classPackage =
                                                                        classeAppelantInteractor
                                                                                .getOriginClass()
                                                                                .getPackageName();
                                                                return interactorPackageName
                                                                        .contains(classPackage);
                                                            });

                                    conditionEvents.add(
                                            new SimpleConditionEvent(
                                                    javaClass,
                                                    estAppeleDansLePackageParent,
                                                    String.format(
                                                            "L'interactor %s est appelé en dehors de son package parent",
                                                            javaClass.getName())));
                                }
                            });
}
