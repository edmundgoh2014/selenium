# Selenium WebDriver Automation Project

A Java web automation project built with **Selenium WebDriver (v4.9.0)** and **JUnit 4** using VS Code, featuring an automated cloud test pipeline via **GitHub Actions**.

## 🚀 Automated Test Cases
* **Basic Auth** – Handles authentication via user credentials injected directly into the URL.
* **Broken Images** – Programmatically detects broken images using the HTML `naturalWidth` attribute.
* **Checkboxes** – Independently toggles and validates standalone checkbox input elements.
* **Drag & Drop** – Simulates HTML5 drag-and-drop utilizing a `JavascriptExecutor` script injection.
* **Dropdown** – Validates list selection states using Selenium's native `Select` wrapper class.
* **Multiple Windows** – Focuses and tracks newly spawned browser tab handles.

---

## ⚙️ CI/CD Integration: GitHub Actions

This project automatically executes its test suite in a headless Linux environment on every `push` or `pull_request` to the `main`/`master` branches.

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