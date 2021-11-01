# [ccg-v2d](https://github.com/agdturner/ccg-v2d)

A modularised Java [2D](https://en.wikipedia.org/wiki/Two-dimensional_space) geometry library. The dimensions are defined by orthogonal coordinate axes X and Y that meet at the origin point <x,y> where the coordinates x=y=0. All points in this space can be defined as immutable [V2D_Point](https://github.com/agdturner/agdt-java-vector2D/blob/master/src/main/java/uk/ac/leeds/ccg/v2d/geometry/V2D_Point.java) instances with each coordinate stored currently as a [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java). I am considering changing this to allow for coordinates to be [Math_BigRationalSqrt](https://github.com/agdturner/agdt-java-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/Math_BigRationalSqrt.java) numbers, [algebraic numbers](https://en.wikipedia.org/wiki/Algebraic_number) or possibly any [real number](https://en.wikipedia.org/wiki/Real_number).

## Dependencies
- [ccg-io](https://github.com/agdturner/ccg-io)
- [ccg-math](https://github.com/agdturner/ccg-math)
- [BigMath](https://github.com/eobermuhlner/big-math)
- Please see the [POM](https://github.com/agdturner/ccg-v2d/blob/master/pom.xml) for details.

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
- Thank you Eric for the [BigMath](https://github.com/eobermuhlner/big-math) library.
