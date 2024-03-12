# Coding dojo Arch Unit

This repository is the results of a coding dojo regarding [ArchUnit](https://www.archunit.org/).
ArchUnit is a library allowing to test the code architecture of an application as unit tests.

## What was covered

We covered rules that could match 2 types of project structure :

- Clean Architecture
- Screaming Architecture `src/main/java/com/example/demo/screamingarchitecture`

### Clean Architecture

The code regarding this architecture can be found here : `src/main/java/com/example/demo/cleanarchi`
The tests regarding this architecture can be found here : `src/test/java/com/example/demo/CleanArchitectureTest.java`

The test file covers these architecture rules :

- `cleanArchitecureLayersAreRespected`
    - Clean architecture layers are defined
    - Access authorization between layers
        - The domain layer representing the business logic should not access any of other layer
        - The application layer representing the application entry points should not be accessed by any other layer
- `usecase_should_have_only_one_public_method_which_should_be_called_execute`
    - Every **class** that resides in the `usecases` package should have only one public method. This method should be
      named execute
- `interactors_should_be_used_in_their_context`
    - A package interactor could be created within a use case in order to externelize some business logic related to
      this use case
    - A class declared under `domain/usecases/<usecase-name>/interactor` should only be used in the use case context i.e
      in the package `domain/usecases/<usecase-name>`
- `interfaces_defined_in_domain_layer_should_be_suffixed_by_Port`
    - The implementation details of a use case ("how" we execute our use case if we consider the use case itself as
      the "what" we are doing) resides outside the domain layer
    - To communicate with other layers, we use the pattern Port/Adpater
    - Every interface declared in the domain layer should respect this pattern and be name `xxxPort.java`
- `classes_implementing_a_port_should_be_suffixed_by_Adapter`
    - As we want to respect the Port/Adapter pattern, each class implementing a Port interface should be
      named `xxxAdapter.java`

### Screaming Architecture

The code regarding this architecture can be found here : `src/main/java/com/example/demo/screamingarchitecture`
The tests regarding this architecture can be found
here : `src/test/java/com/example/demo/ScreamingArchitectureTest.java`

The test file covers this architecture rule :

- `each_package_should_be_isolated`
    - In this type of architecture, we want to isolate each package representing the lifecycle of a business object.
      That is why we don't want any of these packages to communicate with another.
      However, we may want some configuration files to be used from anywhere in the codebase.

