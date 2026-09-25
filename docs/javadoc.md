# Generate API documentation

The source documents every declared class, constructor, and method, including
private helpers. Documentation describes inputs, return values, validation,
task numbering, date representation, persistence, and recoverable errors.
Inherited command execution documentation uses `{@inheritDoc}`.

From the repository root in PowerShell with JDK 25 on PATH:

```powershell
$javaSources = Get-ChildItem src/main/java -Recurse -Filter *.java
javadoc -quiet -private '-Xdoclint:all,-missing' -Werror -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -d _temp/javadoc $javaSources.FullName
```

Open `_temp/javadoc/index.html` to read the generated API reference. The command
checks documentation syntax and links and treats warnings as errors. Missing
comment warnings are disabled because generated default constructors, fields,
and package descriptions are outside this increment's method-header coverage;
declared class and method headers were reviewed separately.

The A-JavaDoc change adds documentation only. The existing console test plan
continues to specify the same behaviour.
