# Known Bugs in sprint 2

3- Given I complete Step 2 and click "Next", when Step 3 (Details) loads, then I see: max guests (number input, min 1), bedrooms (number input, min 0), beds (number input, min 1), and bathrooms (number input, min 0, allows 0.5 increments)
3- Given I am not a host, when I try to access /t/{slug}/hosting/create, then I am blocked with a 403 error
4- Given I view the amenities grid, when I inspect the layout, then amenities are grouped by type: Essentials, Features, and Safety
12 - Given I am on the home page (/t/{slug}/), when I view the category bar, then I see a horizontal scrollable row of category icons (Apartment, House, Villa, Cabin, Loft, Studio, etc.)

## Test Writing Rules

- **One assertion per test** — each test method must have exactly one assertion.
- **Tests contain only business logic** — delegate all Selenium interactions to the page class, never interact with the driver directly in a test.
- **Use `@ParameterizedTest`** whenever the same test logic applies to multiple inputs.
- **Locators go in `Locators.java`**, not inline in page classes.
- **JS snippets go in `resources/com/staybnb/scripts/`**, not as inline strings.
- **Shared data/constants go in `utils/`**, not duplicated across tests.
- All assertions must include an error message from ErrorMessages.java
- Layout state changes must be cleaned up in @AfterEach, not inline in test methods
- URL assertions must use isUrlContains() from BaseTest, not driver.getCurrentUrl() directly
- **No `Thread.sleep()`** — strictly use `WebDriverWait` or `FluentWait` for all state changes; hardcoded waits are prohibited.
- **Waits belong in page classes** — tests should never instantiate a wait; delegate all synchronization logic to the page objects.
- **100% test independence** — tests must not rely on previous state and must be capable of running in parallel or in any random order.
- **No `System.out.println()`** — mandate the use of a proper logging framework (like SLF4J or Log4j2) for CI/CD visibility.
- **Fluent Page Objects** — page methods that trigger navigation must return the next Page Object instance to allow clean action chaining.
- **Driver instantiation goes in a Factory** — encapsulate driver setup in a `DriverFactory` class using `ThreadLocal` to ensure thread-safety.
- **Externalize environment configs** — store properties like URLs, browser types, and timeouts in external files (e.g., `config.properties`), never in Java code.
- **Dynamic test data** — use libraries like Java Faker or UUIDs to generate unique inputs (like emails) to prevent database conflicts.
- **Use `Assertions.assertAll()` for UI checks** — group multiple UI state validations (like checking 5 different text fields) so the test evaluates all elements before failing.
- **Mandate `@Tag` on all tests** — categorize test classes or methods (e.g., `@Tag("smoke")`, `@Tag("regression")`) for targeted CI/CD execution.
- **Use `@ExtendWith` for cross-cutting concerns** — move generic test lifecycle logic, like taking screenshots on failure, into custom `TestWatcher` extensions rather than cluttering base classes.
- **Use `@DisplayName`** — provide clean, human-readable test descriptions for reporting instead of relying on long, camel-case method names.
- **Design for concurrent execution** — completely avoid static variables for test state to ensure safe parallel execution via `@Execution(ExecutionMode.CONCURRENT)`.
