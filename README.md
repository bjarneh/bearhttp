
    bearhttp - small http web server



## What ##

`bearhttp` is a small web server; basically I wanted to make a
clone of [BareHTTP][1], but it's written in [Java][3] 1.6, i.e.
the `HttpServer` found in 1.6 is used to dispatch
incoming request. Only one type of handler is implemented currently,
and it handles GET and HEAD requests, returning files and hopefully
the correct mime type. Mime/content types are based on 
`/etc/mime.types` found on Debian/Ubuntu systems.

## Requirements ##

- [Ant][2] is used to build/test


## Try it ##

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
