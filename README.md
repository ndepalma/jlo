jlo extended - use liblo in Java applications
====================================

NOTE: This version is a forked, extended version from the one in 7890/jlo. 

Updates: 
* Includes support for both UDP and TCP in both client and server
* Enumeration support for the protocols for a cleaner interface
* More working function calls into the lower level liblo library

liblo is a great OSC library:

* http://liblo.sourceforge.net/

jlo makes use of liblo via JNA

* https://github.com/twall/jna
* http://en.wikipedia.org/wiki/Java_Native_Access

See src/ch/lowres/jlo/test/*.java for example use

liblo and Java must be installed on your system to use jlo.

Any application using jlo would be started like this:

```
 $ java -cp <uri of jlo.jar>:<uri of jna.jar>:<more> app_using_jlo
```

jlo javadoc
-----------

* https://rawgithub.com/7890/jlo/master/doc/index.html
