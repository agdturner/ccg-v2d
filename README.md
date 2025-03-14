# [ccg-v2d](https://github.com/agdturner/ccg-v2d)

A modular Java library for [2D](https://en.wikipedia.org/wiki/Two-dimensional_space) Euclidean spatial vector geometry.

The spatial dimensions are defined by orthogonal coordinate axes X and Y that meet at the origin point <x,y> where the coordinates x=y=0.

There are two implementations distinguished by the type of numbers used for calculations and to represent coordinates:
1. Coordinates and calculations using Java double precision primitive numbers. These use a small user defined epsilon (a small number) which is used to determine if there are vector intersections. This is limited by the vaguaries of floating point arithmetic.
2. Coordinates and calculations using a combination of [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) and [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers. These use a user provided Order Of Magnitude (OOM) and RoundingMode (RM) to store process coordinates. With this for example, it is possible to have a coordinate at precisely x=y=$\sqrt{2}$.   

The code is being developed along with [ccg-r2d](https://github.com/agdturner/ccg-r2d) - rendering code that helps demonstrate capabilities of the library.

## Dependencies
- [Java SE 21](https://en.wikipedia.org/wiki/Java_version_history#Java_SE_21)
- There are only a few light weight dependencies, please see the [POM](https://github.com/agdturner/ccg-v2d/blob/master/pom.xml) for details.

## Development plans/ideas
- Add functionality for clipping shapes.
- Add functionality for merging shapes.
- Add functionality for calculating the intersection of two polygons.
- Make a versioned release on Maven Central.
- Community development:
  - Raise awareness
  - Develop use cases

## Contributing
- Thanks for thinking about this.
- If this is to form into a collaborative project, it could do with a Code of Conduct and Contributor Guidelines based on something like this: [Open Source Guide](https://opensource.guide/)

## LICENSE
- [APACHE LICENSE, VERSION 2.0](https://www.apache.org/licenses/LICENSE-2.0)
- Other licences are possible!

## Acknowledgements and thanks
- The [University of Leeds](http://www.leeds.ac.uk) has indirectly supported this work by employing me over the years and encouraging me to develop the skills necessary to produce this library.
- Thank you Eric for the [BigMath](https://github.com/eobermuhlner/big-math) library.
