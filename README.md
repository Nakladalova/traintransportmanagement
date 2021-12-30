# traintransportmanagement

The goal of the project was to create a user-friendly application with a graphical interface that can be run from the command line.
The application is connected to the train-transport-management database and contains information about trains.

You can compile the project with command:  mvn clean install
and run the project with:
java -jar target/traintransportmanagement-1.0.0.jar

In pom.xml file was added these dependencies:

<!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.2.24</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.zaxxer/HikariCP -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>5.0.0</version>
        </dependency>
        <!-- for BCrypt -->
        <dependency>
            <groupId>at.favre.lib</groupId>
            <artifactId>bcrypt</artifactId>
            <version>0.9.0</version>
        </dependency>
        <!-- Logging with SLF4J -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.30</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.6</version>
        </dependency>
    </dependencies>
    

And this plugin for bulding a single executable Jar file
    
    <!-- build a single executable JAR -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <executions>
                    <!-- Attach the shade into the package phase -->
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <filters>
                                <filter>
                                    <artifact>*:*</artifact>
                                    <excludes>
                                        <exclude>META-INF/*.SF</exclude>
                                        <exclude>META-INF/*.DSA</exclude>
                                        <exclude>META-INF/*.RSA</exclude>
                                    </excludes>
                                </filter>
                            </filters>
                            <transformers>
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>org.but.feec.javafx.Main</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

