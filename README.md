
    bearhttp - small http web server



## What ##

`bearhttp` is a small web server in Java, basically I wanted to make a
clone of [BareHTTP][1]. It's written in [Java][3], and you need 
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


Should give you access to the documentation (which is a bit sparse)
on port 9000, i.e. if you go to http://127.0.0.1:9000/index.html
you hopefully see the javadoc of the project.


[1]: http://www.savarese.org/software/barehttp "BareHTTP home page"
[2]: http://ant.apache.org "Apache Ant Project"
[3]: http://openjdk.java.net "Java OpenJDK"
