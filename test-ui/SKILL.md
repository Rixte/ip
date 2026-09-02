# test-ui

## Purpose

This is a project-specific UI testing skill for SamSquare.

The skill tests SamSquare through its actual console interface using the test
cases defined in `test/ui-test-plan.md`.

## Instructions

1. Read `test/ui-test-plan.md` before starting the test session.
2. Run the SamSquare program using the project's normal method.
3. Execute the test cases from `test/ui-test-plan.md` in the order they are listed.
4. For each test case:

    * enter the specified console input;
    * capture the actual console output;
    * compare the actual output against the expected output in the test plan.
5. Treat the expected output in `test/ui-test-plan.md` as the specification.
6. Do not change the expected output just to make a test pass.
7. After each test case, record the console input and actual console output.
8. If a test case fails:

    * stop the test session immediately;
    * do not continue to later test cases;
    * report the name or number of the failed test case;
    * report the actual output;
    * report the expected output.
9. After a failing test has been fixed, run the UI test session again from the
   beginning, unless there is a clear project-specific reason to run a smaller
   relevant set of tests.
10. If all test cases pass, report that the UI test session passed and show the
    console input and output from the session.

## Test Plan Maintenance

When user-facing behaviour changes:

1. Check whether `test/ui-test-plan.md` needs to be updated.
2. Add or modify test cases when new behaviour is introduced.
3. Keep the test plan consistent with the current SamSquare requirements.

## Testing Scope

The tests should cover the actual console behaviour of SamSquare, including:

* adding ToDos;
* adding Deadlines;
* adding Events;
* listing tasks;
* marking tasks as done;
* unmarking tasks;
* storing arbitrary date/time strings;
* handling multiple task types together;
* exiting with `bye`.

## Failure Handling

A test failure must immediately stop the test session.

The failure report must contain:

### Failed Test

The test case that failed.

### Actual Output

The output produced by SamSquare.

### Expected Output

The output specified in `test/ui-test-plan.md`.

Do not hide, skip, or ignore failures.

## Completion Criteria

A code update affecting user-facing behaviour is not considered complete until
the relevant UI tests have been run.

All relevant UI tests must pass before the testing task is considered complete.
