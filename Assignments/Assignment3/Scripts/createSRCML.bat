srcml --verbose mysql-server-mysql-8.0.2 -o srcML_mySQL.xml
srcml --xpath "//cpp:include" srcML_mySQL.xml > srcML_dependencies.xml