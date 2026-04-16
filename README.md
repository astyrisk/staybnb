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

Full reproduction steps are in [DEFECTS.md](DEFECTS.md).

| # | Sprint | Area | Description | Status |
|---|---|---|---|---|
| [BUG-1](DEFECTS.md#bug-1--beds-minimum-constraint-not-enforced-create-property-step-3) | Sprint 2 | Create Property — Step 3 | Beds counter minimum is 0 instead of 1 | Open |
| [BUG-2](DEFECTS.md#bug-2--non-host-direct-navigation-to-hostingcreate-does-not-return-403) | Sprint 2 | Access Control | Non-host navigating directly to `/hosting/create` gets no 403 or redirect | Open |
| [BUG-3](DEFECTS.md#bug-3--amenity-groups-missing-on-create-property-step-4) | Sprint 2 | Amenities | Amenity group headings (Essentials, Features, Safety) missing on Step 4 | Open |
| [BUG-4](DEFECTS.md#bug-4--category-bar-scroll-position-not-reset-on-back-navigation) | Sprint 2 | Home Page | Category bar scroll position not reset when navigating back | Open |
| [BUG-5](DEFECTS.md#bug-5--navbar-does-not-show-my-properties-after-becoming-a-host) | Sprint 2 | Hosting | Navbar does not show "My Properties" after becoming a host | Open |
| [BUG-6](DEFECTS.md#bug-6--become-a-host-button-on-profile-page-does-not-redirect-to-hosting) | Sprint 2 | Hosting | "Become a Host" button on profile page does not redirect to hosting | Open |
| [BUG-7](DEFECTS.md#bug-7--property-details-page-does-not-show-category-alongside-type) | Sprint 2 | Property Details | Property type field missing category and `·` separator | Open |
| [BUG-8](DEFECTS.md#bug-8--selecting-a-category-chip-does-not-mark-it-as-active) | Sprint 2 | Home Page | Clicking a category chip does not apply active styling | Open |
| [BUG-9](DEFECTS.md#bug-9--property-listing-page-shows-unexpected-searchsort-controls) | Sprint 2 | Hosting — Property Listing | Property listing page shows unexpected search/sort controls | Open |
| [BUG-10](DEFECTS.md#bug-10--mobile-compact-search-bar-not-visible-in-navbar) | Sprint 3 | Search | Mobile compact search bar not visible in navbar | Open |
| [BUG-11](DEFECTS.md#bug-11--check-in-date-picker-allows-past-dates) | Sprint 3 | Search | Check-in date picker allows selection of past dates | Open |
| [BUG-12](DEFECTS.md#bug-12--check-out-date-picker-allows-dates-before-check-in) | Sprint 3 | Search | Check-out date picker allows dates before the check-in date | Open |
