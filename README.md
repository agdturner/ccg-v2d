# [ccg-v2d](https://github.com/agdturner/ccg-v2d)

A modular [2D](https://en.wikipedia.org/wiki/Two-dimensional_space) Euclidean spatial vector geometry Java library.

Spatial dimensions are defined by orthogonal coordinate axes X and Y that meet at the origin point <x,y> where the coordinates x=y=0.

There are two main implementations in the library that are distinguished by the type of numbers used for calculations and to represent coordinates:
1. Coordinates and calculations using Java double precision primitive numbers.
2. Coordinates and calculations using a combination of [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) and [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers.

The code is being developed along with [ccg-r2d](https://github.com/agdturner/ccg-r2d) - rendering code to show the capabilities of the library.

## Dependencies
- [ccg-io](https://github.com/agdturner/ccg-io)
- [ccg-math](https://github.com/agdturner/ccg-math)
- [BigMath](https://github.com/eobermuhlner/big-math)
- Please see the [POM](https://github.com/agdturner/ccg-v2d/blob/master/pom.xml) for details.

## Development plans/ideas
- Add functionality for clipping.
- Add functionality for merging.
- Make a versioned release on Maven Central.

## Contributions welcome
- Please submit issues.

## LICENSE
- [APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) and some externally funded research grants have supported the development of libraries dependencies.
- Thank you Eric for the [BigMath](https://github.com/eobermuhlner/big-math) library.
