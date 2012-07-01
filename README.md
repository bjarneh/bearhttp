
    bearhttp - small http web server



## What ##

`bearhttp` is a small web server; basically I wanted to make a
clone of [BareHTTP][1]. It's pure [Java][3] (1.6), and you need 
[Ant][2] to build/test etc.


## Try ##

On Linux/Unix/Mac this should work:

    $ ant javadoc
    $ ant script
    $ ./bin/bearhttp doc/

On any architecture this should work:

    $ ant javadoc
    $ ant
    $ java -jar dist/bearhttp.jar doc/


If you go to http://127.0.0.1:9000/index.html
you hopefully see the javadoc for the project.


[1]: http://www.savarese.org/software/barehttp "BareHTTP home page"
[2]: http://ant.apache.org "Apache Ant Project"
[3]: http://openjdk.java.net "Java OpenJDK"
