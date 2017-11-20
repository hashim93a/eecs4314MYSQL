# Assignment 3 Reproduction Instructions

## Before you begin

**WARNING: Some scripts may not generate correctly named files or, as a consequence, may take files with different names. It should be apparent what files need renaming.**

You will want to download and/or install these on your computer:
* [MySQL v8.0.2 Source Code](https://wiki.eecs.yorku.ca/course_archive/2017-18/F/4314/protected:assign:desc) (under Assignment 2, "MySQL v8.0.2 Source Code")
* [Understand](https://scitools.com/non-commercial-license/) from SciTools
* [srcML](http://www.srcml.org/)
* Java
* Perl (to run ``csvTOta.pl``)
* Microsoft Excel

You can begin with the following steps:

* Download the files in this folder from GitHub, extract it into a folder and put the MySQL source code in the same folder.
* Open _Include.java_ in a text editor. Find this line and replace the path ``"C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2"`` with the path of the folder you extracted the files in Step 1 into.
```
private static String mySQLPath = "C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2";
```
* Open _Paths.java_ in a text editor. Find this line and replace the path appropriately.
```
private static String mySQLPath = "C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2";
```
* Run the following commands in a terminal.
```
javac Paths.java
java Paths
```
* Remove all absolute paths from the generated _rel_paths.txt_ using a text editor.
Example:
```
D:\4314stuff\A3Data\mysql-server-mysql-8.0.2\client\base\abstract_connection_program.cc
```
should be replaced with
```
client\base\abstract_connection_program.cc
```

## Extracting Dependencies to CSV
### Understand
* Create a new project. Make sure it uses the language **C/C++**.
   * _(MySQL uses the Java language as well, but to keep consistent with our results only have C/C++ selected.)_
* Add the _mysql-server-mysql-8.0.2_ folder to the project and finish creating the project.
* Go to _Reports>Dependency>File Dependencies>Export CSV_ and click **Relative Name** under _File Names_.
* Export to _understand_dependencies.csv_.
### srcML
* Run the following commands in a terminal (or run _createSRCML.bat_).
```
srcml --verbose mysql-server-mysql-8.0.2 -o srcML_mySQL.xml
srcml --xpath "//cpp:include" srcML_mySQL.xml > srcML_dependencies.xml
```
* Open _srcML_dependencies.xml_ in Excel, remove all the formatting and every column aside from ``cpp:include`` and ``cpp:file``, then save as _srcML_dependencies.csv_.

### Include
* Run the following commands in a terminal.
```
javac Include.java
java Include
```
* Remove all absolute paths from the generated _include_dependencies.csv_ using a text editor.
Example:
```
C:\Users\DRH\Documents\mysql-server-mysql-8.0.2\client\base\abstract_connection_program.cc,abstract_connection_program.h
```
should be replaced with
```
client\base\abstract_connection_program.cc,abstract_connection_program.h
```

## Analyzing Dependencies
You should now have the files:
```
understand_dependencies.csv
srcML_dependencies.csv
include_dependencies.csv
rel_paths.txt
```
Now you are ready to get the ``.ta`` files for analysis.
* Create a new folder called ``src_files`` and move all ``.csv`` files and ``rel_paths.txt`` into it.
* Run the following commands:
```
javac GetRelativeFilepaths.java
java GetRelativeFilepaths
perl csvTOta.pl src_files\understand_dependencies.csv
perl csvTOta.pl dst_files\srcML_dependencies.csv
perl csvTOta.pl dst_files\include_dependencies.csv
```
* Move ``dst_files\srcML_dependencies.raw.ta`` and ``dst_files\include_dependencies.raw.ta`` to the ``src_files`` folder.

### Quantitative Analysis
* Run the following commands:
```
javac AnalyzeDependencies.java
java AnalyzeDependencies
```
* You will receive data from the program which outputs both to standard output and ``dst_files\comparison_output``.

### Qualitative Analysis
* Run the following commands:
```
javac sample.java
java sample
```
* This will pick a sample of 382 dependencies, then find how many dependencies are found by each method. This is outputted to  ``results.txt``, while the sample of dependencies is in ``sample_dependencies.txt``.
