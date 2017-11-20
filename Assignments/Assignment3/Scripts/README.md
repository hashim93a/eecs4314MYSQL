# Assignment 3 Script Information
Here are descriptions of every script in this folder. For instructions on how we managed to extract the dependencies using these scripts, refer to ``USAGE.md``.

### createSRCML.bat
Extracts MySQL's file dependencies to ``srcML_dependencies.xml`` using srcML. You must further edit the output using a tool such as Microsoft Excel in order to export it to a ``.csv`` file.
* Requires the MySQL source code folder/zip in the same directory. Executes the following commands:
```
srcml --verbose mysql-server-mysql-8.0.2 -o srcML_mySQL.xml
srcml --xpath "//cpp:include" srcML_mySQL.xml > srcML_dependencies.xml
```

### csvTOta.pl
This file is from the c488 lab demo and is a renamed ``transformUnderstand.pl``. It creates a ``.raw.ta`` file from a ``.csv`` file, ignoring the first line of the csv.

### Include.java
Extracts MySQL's file dependencies based on the ``#includes`` directives in ``.c, .cc, .cpp, .h`` and ``.hpp`` files.
* Requires you to edit ``Include.java``. Change the line ``private static String mySQLPath = "C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2";`` to the path where MySQL is located.
* Outputs ``include_dependencies.csv``.

### Paths.java
Extracts the absolute file paths of all ``.c, .cc, .cpp, .h`` and ``.hpp`` files in the MySQL folder. You will have to further edit the output by changing absolute paths to relative paths.
* Requires you to edit ``Paths.java``. Change the line ``private static String mySQLPath = "C:\\Users\\DRH\\Documents\\mysql-server-mysql-8.0.2";`` to the path where MySQL is located.
* Outputs ``Paths.txt``.

### Comparison.java
Finds overlap between the Include script output and the Understand .csv assuming the Understand output uses the Long Name for the file output.
* Requires the following files and directories to run:
```
IncludeDependencies.txt (rename include_dependencies.csv)
UnderstandDependency.txt (rename UnderstandDependency.txt)
```
* Outputs ``Include_Vs_Understand.txt``.

### GetRelativeFilepaths.java
Appends relative file paths to the srcML and include dependency ``.csv`` files, based on the file paths in ``rel_paths.txt``. Our dependency extraction method cannot add these directly, so this script is necessary for later comparison and analysis.
* Requires the following files and directories to run:
```
src_files\
src_files\rel_paths.txt
src_files\srcML_dependencies.csv
src_files\include_dependencies.csv
dst_files\
```
* Outputs the following files:
```
dst_files\srcML_dependencies.csv
dst_files\include_dependencies.csv
```

### AnalyzeDependencies.java
Compares ``.ta`` files which contain dependencies. Outputs formatted comparisons as well as full lists of dependencies for each comparison.
* Requires the following files and directories to run:
```
src_files\
src_files\understand_dependencies.raw.ta
src_files\srcML_dependencies.raw.ta
src_files\include_dependencies.raw.ta
dst_files\
```
* Outputs ``dst_files\comparison_output.txt``.

### sample.java
Takes a sample of 382 dependencies from the list of all dependencies, then outputs a comparison of how many dependencies Understand, srcML and Include found within the sample. This information is used to calculate precision and recall, and is output in ``results.txt``.
* Requires the following files and directories to run:
```
understand_dependencies.txt (renamed understand_dependencies.csv)
srcML_dependencies.txt (renamed srcML_dependencies.csv)
include_dependencies.txt (renamed include_dependencies.csv)
```
* Outputs the following files:
```
sample_dependencies.txt
results.txt
```
