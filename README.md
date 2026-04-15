# StayBnB — Test Automation Framework

Selenium WebDriver end-to-end test suite for an Airbnb-like rental platform. Multi-tenant app with URL pattern `/t/{slug}/`.

**Stack:** Java 21 · JUnit 5 · Selenium WebDriver 4 · Log4j2 · Allure

---

## Setup

Create a `.env` file in the project root (never commit it):

```
TEST_BASE_URL=https://qa-playground.nixdev.co/t/<slug>
TEST_USER_EMAIL=<email>
TEST_PASSWORD=<password>
TEST_FIRST_NAME=<first>
TEST_LAST_NAME=<last>
```

Config priority: System Properties (`-D`) > `.env` > Environment Variables.

---

## Commands

```bash
mvn clean test                                              # Run all tests
mvn clean test -Dheadless=true                             # Headless mode
mvn clean test -Dtest=LoginTest                            # Single class
mvn clean test -Dtest=LoginTest#testSuccessfulLogin        # Single method
mvn clean test -Dgroups=smoke                              # By tag
mvn allure:report                                          # Generate report
```

Headless mode is enabled automatically in CI (GitHub Actions, Jenkins).

---

## Architecture

| Layer | Location | Responsibility |
|---|---|---|
| Tests | `tests/ui/`, `tests/api/` | Business logic and assertions only |
| Pages | `pages/` | All Selenium interactions |
| Components | `components/` | Reusable UI fragments (e.g. `Navbar`) |
| Locators | `locators/Locators.java` | All `By` selectors |
| Config | `config/` | Env loading, URL/timeout constants, `DriverFactory` |

- 21 UI test classes tagged `smoke` or `regression`
- 12 API test classes tagged `api`
- Classes run concurrently; methods within a class run on the same thread

---

## Known Bugs

| # | Sprint | Area                     | Description | Status |
|---|---|--------------------------|---|---|
| 1 | Sprint 2 | Create Property — Step 3 | Field constraints (min/max values) on the Details step are not consistently enforced in the UI | Open |
| 2 | Sprint 2 | Access Control           | Non-host users navigating directly to `/hosting/create` get a 403 instead of a redirect to the "Become a Host" flow | Open |
| 3 | Sprint 2 | Amenities                | Amenity grouping (Essentials, Features, Safety) is inconsistent across properties | Open |
| 4 | Sprint 2 | Home Page                | Category bar scroll position is not reset when navigating back to the home page | Open |
| 5 | Sprint 3 | Search                   |  Given the search form is expanded, when the user selects a check-in date, then only dates on or after today are selectable | Open |
| 5 | Sprint 3 | Search                   |  Given the user has selected a check-in date, when they select a check-out date, then only dates after the check-in date are selectable | Open |
