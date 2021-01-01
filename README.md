# [agdt-java-vector2D](https://github.com/agdturner/agdt-java-vector2D)

A Java [2D](https://en.wikipedia.org/wiki/Two-dimensional_space) geometry library. The dimensions are defined by orthogonal coordinate axes X and Y that meet at the origin point <x,y> where the coordinates x=y=0. All points in this space can be defined as immutable [V2D_Point](https://github.com/agdturner/agdt-java-vector2D/blob/master/src/main/java/uk/ac/leeds/ccg/v2d/geometry/V2D_Point.java) instances with each coordinate stored currently as a BigDecimal number. I am considering changing this to allow for coordinates to be [Math_BigRationalSqrt](https://github.com/agdturner/agdt-java-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/Math_BigRationalSqrt.java) numbers, [algebraic numbers](https://en.wikipedia.org/wiki/Algebraic_number) or possibly any [real number](https://en.wikipedia.org/wiki/Real_number).

## Dependencies
- [agdt-java-generic](https://github.com/agdturner/agdt-java-generic)
- [agdt-java-math](https://github.com/agdturner/agdt-java-math)
[//]: # (- [BigMath](https://github.com/eobermuhlner/big-math))
- Please see the [POM](https://github.com/agdturner/agdt-java-vector2d/blob/master/pom.xml) for details.

## Known uses
- For generating visualisations of movements across space for the Digital Welfare project.

## Code status
This code is unstable research software.

## Development plans/ideas
- Develop more comprehensive unit tests.
- Improve documentation.
- Make a versioned release on Maven Central.
- As the [OpenJDK](https://openjdk.java.net/) develops some of the functionality may become redundant.
- Consider if it is appropriate to [contribute](https://openjdk.java.net/contribute/) any of this.

## Development history
The intention is to provide a summary of changes from one version to the next here.
### Origin
This code began development bundled together with lots of other code developed for an academic research project.

## Contributions
- Welcome.

## LICENSE
- [APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library.
- Thank you developers and maintainers of other useful Java libraries that provide inspiration.
- Thank you developers and maintainers of [Apache Maven](https://maven.apache.org/), [Apache NetBeans](https://netbeans.apache.org/), and [git](https://git-scm.com/) which I use for developing code.
- Thank you developers and maintainers of [GitHub](http://github.com) for supporting the development of this code and for providing a means of creating a community of users and  developers.
- Thank you developers, maintainers and contributors of relevent content on:
-- [Wikimedia](https://www.wikimedia.org/) projects, in particular the [English language Wikipedia](https://en.wikipedia.org/wiki/Main_Page)
-- [StackExchange](https://stackexchange.com), in particular [StackOverflow](https://stackoverflow.com/).
- Information that has helped me develop this library is cited in the source code.
- Thank you to those that supported me personally and all who have made a positive contribution to society. Let us try to look after each other, look after this world, make space for wildlife, and engineer knowledge :)
