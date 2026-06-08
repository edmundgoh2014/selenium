# Selenium WebDriver Automation Project

A Java web automation project built with **Selenium WebDriver (v4.9.0)** and **JUnit 4**, developed in **VS Code** and integrated with **GitHub Actions** for continuous testing.

## 🚀 Automated Test Cases

The project automates the following web scenarios:

| No. | Test Case |
|------|------------|
| 1 | Basic Auth |
| 2 | Broken Images |
| 3 | Checkboxes |
| 4 | Drag & Drop |
| 5 | Dropdown |
| 6 | Multiple Windows |

---

## 🛠 Tech Stack

* Java 17
* Selenium WebDriver 4.9.0
* JUnit 4
* Maven
* GitHub Actions
* Chrome Browser (Headless in CI)

---

## 📁 Project Structure

```text
project-root/
│
├── src/
│   ├── main/
│   └── test/
│       └── java/
│           └── tests/
│
├── .github/
│   └── workflows/
│       └── selenium-tests.yml
│
├── pom.xml
└── README.md
```

---

## ⚙️ Prerequisites

Before running the project locally, ensure you have:

* Java JDK 17 or later
* Maven 3.8+
* Google Chrome installed
* Internet connection

Verify installation:

```bash
java -version
mvn -version
```

---

## ▶️ Running Tests Locally

Clone the repository:

```bash
git clone <repository-url>
cd <project-name>
```

Execute all tests:

```bash
mvn test
```

Execute a specific test:

```bash
mvn -Dtest=TestClassName test
```

---

## ⚙️ CI/CD Integration: GitHub Actions

This project automatically executes its test suite in a headless Linux environment on every `push` or `pull_request` to the `main` and `master` branches.

### Workflow Configuration (`.github/workflows/selenium-tests.yml`)

```yaml
name: Java Selenium CI

on:
  push:
    branches: [ "main", "master" ]
  pull_request:
    branches: [ "main", "master" ]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v5

    - name: Set up JDK 17
      uses: actions/setup-java@v5
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven

    - name: Run Tests with Maven
      run: mvn test
```