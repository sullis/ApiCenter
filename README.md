[![Build Status](https://travis-ci.org/TNG/ApiCenter.svg?branch=master)](https://travis-ci.org/TNG/ApiCenter) [![Waffle.io - Columns and their card count](https://badge.waffle.io/TNG/ApiCenter.svg?columns=In%20Progress)](https://waffle.io/TNG/ApiCenter) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# ApiCenter

ApiCenter is a repository for all your OpenAPI specifications. You can upload new ones and search them so that finding the one you need is easy.

## Prerequisites

To run ApiCenter, you need `gradle` and angular CLI. The backend itself needs a relational database, for example PostgreSQL.

To install angular CLI, enter `npm install -g @angular/cli`

## Getting started

ApiCenter consists of a RESTful backend service written in Kotlin and a SPA frontend in Angular. Both are contained in this git repo.

### Backend
In order to start the backend, clone the repo, navigate to the `backend` folder and start the service with the familiar Spring Boot command:
```
cd backend/
./gradlew bootRun
```

### Frontend
To start the frontend, clone the repo, navigate to the `frontend` folder and start it with angular CLI:
```
cd frontend/
npm install
ng serve
```

## Running the tests

### Backend
Running the backend tests is `./gradlew test` for all Unit-Tests and `./gradlew integrationTest` for all Integration-Tests.

### Frontend
Running all frontend tests is simply `ng test`.

## Mutation Testing
In order to keep the quality of our tests high, we regularly execute mutation testing tools on our code for quality assurance. 
Sadly, the automatic support of reporting tools is not sophisticated enough yet, so we rely on manual execution and monitoring. 
If you want to execute the tools yourself in order to determine the mutation score (maybe for pull requests), execute the following commands:

- Mutation testing in the backend with [pitest](http://pitest.org/): `./gradlew pitest`
- Mutation testing in the frontend with [Stryker](https://stryker-mutator.io/): `stryker run` (make sure that you have the Striker CLI installed, otherwise run: `npm install -g stryker-cli`)

## Built with
- [Gradle](https://gradle.org/)
- [Kotlin](https://kotlinlang.org/)
- [Spring Boot 2](https://spring.io/projects/spring-boot)
- [Angular](https://angular.io/)

## Contributing
See CONTRIBUTING.md

## Versioning
ApiCenter uses [semantic versioning](https://semver.org/). You can expect breakage if the major version changes.

## License
ApiCenter is licensed unter the [Apache 2.0 license](https://github.com/tngtech/apicenter/LICENSE.md).
