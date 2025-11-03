how to add Gson:
1. make direction 'mkdir -p lib'
2. downlod gson 'curl -L -o lib/gson-2.10.1.jar https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar'
3. compile it 'javac -cp lib/gson-2.10.1.jar -d out src/*.java'

how to add JDBC:
1. because we just made lib, so we don't need to make another one and use it instead
2. download JDBC 'curl -L -o lib/mysql-connector-java-8.0.33.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.33/mysql-connector-java-8.0.33.jar'
3. if you want to compile Gson and JDBC use this command 'javac -cp "lib/gson-2.10.1.jar:lib/mysql-connector-java-8.0.33.jar" -d out src/*.java'

how to run server:
1. (Gson only) java -cp "out:lib/gson-2.10.1.jar" Main
2. (Gson and JDBC) java -cp "out:lib/gson-2.10.1.jar:lib/mysql-connector-java-8.0.33.jar" Main