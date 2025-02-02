/**
 * EXTENSION METHODS
 * 
 * Scala 3 brings first-class support for "extension methods", which allow adding methods to 
 * classes after their definition. Previously, this feature was emulated using implicits.
 */
object ext_methods:
  final case class Email(value: String)

  /**
   * EXERCISE 1
   * 
   * Add an extension method to `Email` to retrieve the username of the email address (the part 
   * of the string before the `@` symbol).
   */
  extension (e: Email) def username: String = e.value.split('@')(0) // .head

  val sherlock = Email("sherlock@holmes.com").username

  /**
   * EXERCISE 2
   * 
   * Add an extension method to `Email` to retrieve the server of the email address (the part of 
   * the string after the `@` symbol).
   */
  // extension
  extension (e: Email) def serverAddress: String = e.value.split('@')(1) // .last

  /**
   * EXERCISE 3
   * 
   * Add an extension method to `Int` called `split` that can package up the high 16 bits and 
   * low 16 bits into a tuple of two ints, containing the two parts.
   */
  extension (i: Int) def split: Tuple2[Int, Int] = ??? // i & 0x00FF -> i << 16

  /**
   * A rational number is one in the form n/m, where n and m are integers.
   */
  final case class Rational(numerator: BigInt, denominator: BigInt)

  /**
   * EXERCISE 4
   * 
   * Extension methods may be operators, such as `+` or `-`. Add a collection of extension methods 
   * to `Rational`, including `+`, to add two rational numbers, `*`, to multiply two rational 
   * numbers, and `-`, to subtract one rational number from another rational number.
   */
  // object Operators:
  extension (r: Rational) def +(that: Rational) : Rational =
    Rational(r.numerator + that.numerator, r.denominator + that.denominator)
  extension (r: Rational) def -(that: Rational) : Rational =
    Rational(r.numerator - that.numerator, r.denominator - that.denominator)
  extension (r: Rational) def *(that: Rational) : Rational =
    Rational(r.numerator * that.numerator, r.denominator * that.denominator)

  val xd = Rational(1, 2) + Rational(3, 4)

  /**
   * EXERCISE 5
   * 
   * Convert this implicit syntax class to use extension methods.
   */
  // implicit class StringOps(self: String):
  //   def equalsIgnoreCase(that: String) = self.toLowerCase == that.toLowerCase

  extension (self: String) def equalsIgnoreCase(that: String) = 
    self.toLowerCase == that.toLowerCase
  // implicit class StringOps(self: String):
  //   def equalsIgnoreCase(that: String) = self.toLowerCase == that.toLowerCase

  /**
   * EXERCISE 6
   * 
   * Import the extension method `isSherlock` into the following object so the code will compile.
   */
  object test:
    import string_extensions.*

    val test: Boolean = "John Watson".isSherlock

  object string_extensions:
    extension (s: String) def isSherlock: Boolean = s.startsWith("Sherlock")

  /**
   * EXERCISE 7
   * 
   * Extension methods may be generic. Define a generic extension method called `uncons`, which 
   * works on any `List[A]`, and which returns an `Option[(A, List[A])]` (either `None` if the list 
   * is empty, or otherwise the head and tail in a tuple).
   */
  object list_extensions:
    val test: Option[(String, List[String])] = List("foo", "bar").uncons
  
  extension[A : scala.reflect.Typeable] (ls: List[A]) def uncons: Option[(A, List[A])] = ls match
    case Nil => None
    case h :: t => Some((h, t))

  /**
   * EXERCISE 8
   * 
   * Add another generic extension method called `zip` to `Option[A]`, which takes an `Option[B]`, 
   * and returns an `Option[(A, B)]`.
   */
  object option_extensions:
    val test: Option[(Int, String)] = Some(123).zip(Some("foo"))
  
  extension[A,B] (si: Option[A]) def zip(other: Option[B]): Option[(A, B)] =
    if si.isEmpty || other.isEmpty then None else Some(si.get -> other.get)
    // si.zip(other)

  /**
   * EXERCISE 9
   * 
   * One possible application of extension methods is adding methods to generic types of a certain
   * shape. For example, adding `flatten` to a `List[List[A]]`, but not to other types.
   * Add `mapInside` method to `List[Option[A]]` to map on the `A` inside the options.
   */
  object list_options_extensions:
    val digits: List[Option[Int]] = List(Some("12"), None, Some("321")).mapInside(_.length)
  
  extension[A, B] (loa: List[Option[A]]) def mapInside(f: A => B) = loa.map(_.map(f))