# StayBnB — Test Automation Framework

End-to-end Selenium WebDriver test automation suite for an Airbnb-like rental property management platform. The application is a multi-tenant SaaS with URL pattern `/t/{slug}/` for tenant isolation.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Test Coverage](#test-coverage)
- [Test Writing Rules](#test-writing-rules)
- [Known Bugs](#known-bugs)

---

## Prerequisites

- Java 21
- Maven 3.8+
- Google Chrome (latest stable)
- ChromeDriver matching your Chrome version (managed automatically via WebDriverManager if configured)

---

## Setup

1. Clone the repository.

2. Create a `.env` file in the project root — **never commit this file**:

```
TEST_BASE_URL=https://qa-playground.nixdev.co/t/<your-slug>
TEST_USER_EMAIL=<email>
TEST_PASSWORD=<password>
TEST_FIRST_NAME=<first>
TEST_LAST_NAME=<last>
```

Config is loaded in priority order: **System Properties** (`-D` flags) > `.env` file > Environment Variables.

---

## Running Tests

```bash
# Run all tests
mvn clean test

# Run all tests in headless mode
mvn clean test -Dheadless=true

# Run a single test class
mvn clean test -Dtest=LoginTest

# Run a single test method
mvn clean test -Dtest=LoginTest#testSuccessfulLoginRedirection

# Run only smoke tests
mvn clean test -Dgroups=smoke

# Run only API tests
mvn clean test -Dgroups=api

# Compile without running tests
mvn clean compile

# Generate Allure report (after test run)
mvn allure:report
```

Headless mode is enabled automatically when running in CI environments (GitHub Actions, Jenkins).

---

## Project Structure

```
StayBnB/
├── .env                                    # Local credentials (not committed)
├── pom.xml
├── media/
│   └── apts/                               # Test image assets
├── src/
│   ├── main/java/com/staybnb/
│   │   ├── pages/                          # Page Object Model classes
│   │   ├── components/                     # Reusable UI components (Navbar)
│   │   ├── config/                         # TestConfig, AppConstants, DriverFactory
│   │   └── locators/                       # Locators.java — all By selectors
│   ├── main/resources/com/staybnb/scripts/ # JavaScript snippets
│   └── test/java/com/staybnb/
│       ├── tests/
│       │   ├── BaseTest.java               # WebDriver lifecycle, screenshot on failure
│       │   ├── ui/                         # UI test classes
│       │   └── api/                        # API test classes
│       ├── assertions/
│       │   └── ErrorMessages.java          # All assertion message strings
│       └── data/
│           ├── PropertyPayloads.java       # API request bodies
│           └── MediaPaths.java             # Paths to test media files
└── src/test/resources/
    ├── config.properties                   # Browser, wait timeouts, viewport sizes
    ├── allure.properties
    ├── junit-platform.properties           # Parallel execution config
    └── log4j2.xml
```

---

## Architecture

**Stack:** Java 21 · JUnit 5 · Selenium WebDriver 4 · ChromeDriver · Log4j2 · Allure

### Layers

| Layer | Location | Responsibility |
|---|---|---|
| **Tests** | `tests/ui/`, `tests/api/` | Business-logic assertions only — no driver interaction |
| **Pages** | `pages/` | All Selenium interactions; one class per UI page |
| **Components** | `components/` | Reusable UI fragments (e.g., `Navbar`) |
| **Locators** | `locators/Locators.java` | All `By` selectors centralized in one file |
| **Config** | `config/` | Env loading (`TestConfig`), URL/timeout constants (`AppConstants`), thread-safe driver creation (`DriverFactory`) |
| **Scripts** | `resources/.../scripts/` | JavaScript snippets executed via `executeScript` |

### Key Design Decisions

- `BasePage` provides the navbar reference and JWT token retrieval from `localStorage`.
- `BaseTest` handles WebDriver setup/teardown via `DriverFactory` (`ThreadLocal`) and captures Allure screenshots on failure.
- Wait constants — `SHORT` (5s), `MEDIUM` (10s), `LONG` (20s) — are defined in `AppConstants` and read from `config.properties`.
- API preconditions (e.g., `becomeHostViaApi()`) are called directly from test setup to avoid UI coupling.
- Classes run concurrently; methods within a class run on the same thread (configured in `junit-platform.properties`).

---

## Test Coverage

### UI Tests (21 classes — `@Tag("smoke")` / `@Tag("regression")`)

| Class | Feature |
|---|---|
| `LoginTest` | Successful login, invalid credentials, blank field validation |
| `RegisterTest` | Successful registration, duplicate email, blank field validation |
| `LogoutTest` | Token removal, redirect to home |
| `HomeTest` | Hero section, category bar, property grid layout, responsive columns |
| `NavbarTest` | Authenticated vs. visitor state, profile/logout navigation, mobile layout |
| `BecomeHostTest` | "Become Host" visibility, role state changes |
| `OwnProfileTest` | Profile info display, avatar, name rendering |
| `OtherProfileTest` | Public profile access, 404 for non-existent users |
| `EditProfileTest` | Field updates, validation, persistence |
| `PropertyListingTest` | Grid display, card structure, filtering, sorting |
| `PropertyDetailsTest` | Property info, host details, amenities, gallery, booking widget |
| `PropertyCategoriesTest` | Category bar, chip selection, active state, property filtering |
| `PropertyAmenitiesTest` | Amenity grid, grouping, selection |
| `PropertyAmenityDisplayTest` | Few vs. no amenities display, "Show all" button |
| `SearchTest` | Destination search, empty results |
| `CreatePropertyTest` | 7-step wizard (Basics → Location → Details → Amenities → Photos → Pricing → Review), validation, back navigation, image reordering, max images |
| `EditPropertyTest` | Field updates, amenity selection, persistence |
| `DeletePropertyTest` | Confirmation dialog, dashboard removal |
| `PublishPropertyTest` | Publish/unpublish toggle, dashboard status |
| `HostDashboardTest` | Property grid, CRUD actions, empty state |

### API Tests (12 classes — `@Tag("api")`)

| Class | Endpoint(s) |
|---|---|
| `AuthMeApiTest` | `GET /api/t/{slug}/me` |
| `BecomeHostApiTest` | `POST /api/t/{slug}/become-host` |
| `CreatePropertyApiTest` | `POST /api/t/{slug}/properties` |
| `EditPropertyApiTest` | `PUT /api/t/{slug}/properties/{id}` |
| `DeletePropertyApiTest` | `DELETE /api/t/{slug}/properties/{id}` |
| `PublishPropertyApiTest` | `PATCH /api/t/{slug}/properties/{id}/publish` |
| `HostingPropertiesApiTest` | `GET /api/t/{slug}/hosting/properties` |
| `AmenitiesApiTest` | `GET /api/t/{slug}/amenities` |
| `CategoriesApiTest` | `GET /api/t/{slug}/categories` |
| `ImageUploadApiTest` | `POST /api/t/{slug}/images` |
| `UpdateProfileApiTest` | `PATCH /api/t/{slug}/profile` |
| `OtherUserApiTest` | `GET /api/t/{slug}/users/{id}` |

---

## Test Writing Rules

- **One assertion per test** — each test method must have exactly one assertion.
- **Tests contain only business logic** — delegate all Selenium interactions to the page class; never interact with the driver directly in a test.
- **Use `@ParameterizedTest`** whenever the same logic applies to multiple inputs.
- **Locators go in `Locators.java`** — not inline in page classes.
- **JS snippets go in `resources/com/staybnb/scripts/`** — not as inline strings.
- **Shared data/constants go in `utils/`** — not duplicated across tests.
- All assertions must include an error message from `ErrorMessages.java`.
- Layout state changes must be cleaned up in `@AfterEach`, not inline in test methods.
- URL assertions must use `isUrlContains()` from `BaseTest`, not `driver.getCurrentUrl()` directly.
- **No `Thread.sleep()`** — use `WebDriverWait` or `FluentWait` exclusively.
- **Waits belong in page classes** — tests must never instantiate a wait object.
- **100% test independence** — tests must not rely on previous state; able to run in any order or in parallel.
- **No `System.out.println()`** — use Log4j2/SLF4J for all logging.
- **Fluent Page Objects** — methods that trigger navigation return the next `Page` instance.
- **Driver instantiation goes in `DriverFactory`** — `ThreadLocal` ensures thread-safety.
- **Externalize configs** — URLs, browser types, and timeouts live in `config.properties`, not Java code.
- **Dynamic test data** — use UUIDs (or Java Faker) to generate unique emails and inputs.
- **Use `Assertions.assertAll()` for UI checks** — group multi-field validations so all are evaluated before failing.
- **`@Tag` on all tests** — categorize with `smoke`, `regression`, or `api` for targeted CI/CD runs.
- **`@ExtendWith` for cross-cutting concerns** — screenshot on failure lives in `BaseTest`, not scattered in test classes.
- **`@DisplayName` on all tests** — human-readable descriptions for Allure reports.
- **Design for concurrent execution** — no static variables for test state.

---

## Known Bugs

Tracked bugs found during exploratory testing and sprint reviews. Status: **Open** unless noted.

| # | Sprint | Area | Description | Status |
|---|---|---|---|---|
| 1 | Sprint 2 | Create Property — Step 3 | Step 3 (Details) field spec: max guests (number, min 1), bedrooms (number, min 0), beds (number, min 1), bathrooms (number, min 0, allows 0.5 increments) — field constraints not consistently enforced on the UI | Open |
| 2 | Sprint 2 | Access Control | Non-host users who navigate directly to `/t/{slug}/hosting/create` receive a 403 error instead of a redirect to the "Become a Host" flow | Open |
| 3 | Sprint 2 | Amenities | Amenities grid groups amenities by type (Essentials, Features, Safety) — ordering and grouping logic is inconsistent across properties | Open |
| 4 | Sprint 2 | Home Page | Category bar renders as a horizontal scrollable row — scroll position is not reset when navigating back to the home page | Open |
