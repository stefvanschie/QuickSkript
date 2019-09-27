This file is an addition to Google's Java Style Guide which you can view [here](https://google.github.io/styleguide/javaguide.html).
This is split up into two parts: points from which this style guide differs from Google's and additions to Google's style guide.

### Additions
#### Variable declarations
**Var usage: only when clear**

Replacing the explicit type definition in a local variable declaration/initialisation may be replaced by the `var` word
for type inference, only if it is clear from the initialisation what the actual type would be, even for someone who does
not necessarily know the rest of the codebase. For example, this is a place where this is allowed:
```java
var text = "Hi"; //allowed, it's clear from the right-hand side that this is a String
```
However, this is a place where local type inference is _not_ allowed:
```java
var data = getData(); //disallowed, it's unclear what type data would be
```
Usage of local type inference is always a _may_, never a _must_. When in doubt whether local variable type inference
would be acceptable, don't use it and resort to the explicit type name.

### Differences 
2.3.1 ([original](https://google.github.io/styleguide/javaguide.html#s2.3.1-whitespace-characters)):  
Tab characters *may* be used for indentation.

3 ([original](https://google.github.io/styleguide/javaguide.html#s3-source-file-structure)):  
This project uses MIT license, therefore no license or copyright information at the top.

3.3.1 ([original](https://google.github.io/styleguide/javaguide.html#s3.3.1-wildcard-imports)):  
Wildcard imports, static or otherwise, *may* be used.

3.3.3 ([original](https://google.github.io/styleguide/javaguide.html#s3.3.3-import-ordering-and-spacing)):  
The import statements do not have to be in ASCII sort order, but import statements starting with the same main package name should be grouped.

4.2 ([original](https://google.github.io/styleguide/javaguide.html#s4.2-block-indentation)):  
Indentation uses 4 spaces or one tab.

4.4 ([original](https://google.github.io/styleguide/javaguide.html#s4.4-column-limit)):  
The column limit is 120 characters.

4.6.2 ([original](https://google.github.io/styleguide/javaguide.html#s4.6.2-horizontal-whitespace)):  
Point 8: Only the first one is correct, the second one isn't:
```java
//correct
new int[] {5, 6}

//incorrect
new int[] { 5, 6 }
```

4.8.1 ([original](https://google.github.io/styleguide/javaguide.html#s4.8.1-enum-classes)):  
Constants should always be on separate lines and should never be like an array initializer.

4.8.2.1 ([original](https://google.github.io/styleguide/javaguide.html#s4.8.2-variable-declarations)):  
Multiple variable declarations in one line are allowed.

4.8.3.1 ([original](https://google.github.io/styleguide/javaguide.html#s4.8.3-arrays)):  
An array initializer should never be in a matrix like grid. Either all one the same line, or all on separate ones.

4.8.4.2 ([original](https://google.github.io/styleguide/javaguide.html#s4.8.4-switch)):  
Fall through does not necessarily have to be commented.

4.8.5 ([original](https://google.github.io/styleguide/javaguide.html#s4.8.5-annotations)):  
Annotations should always be above the member class/method/field and there should always be one per line.

5.2.4 ([original](https://google.github.io/styleguide/javaguide.html#s5.2.4-constant-names)):  
Constants are either enum values or fields marked with static and final.

7.1.1 ([original](https://google.github.io/styleguide/javaguide.html#s7.1.1-javadoc-multi-line)):  
A Javadoc should never be on one line. You should always use the first example.
