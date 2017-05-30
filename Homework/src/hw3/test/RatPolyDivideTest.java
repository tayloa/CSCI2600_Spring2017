package hw3.test;

import static org.junit.Assert.*;
import hw3.RatNum;
import hw3.RatPoly;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the RatPoly class.
 * <p>
 */
public final class RatPolyDivideTest {
  private final double JUNIT_DOUBLE_DELTA = 0.00001;

  // get a RatNum for an integer
  private static RatNum num(int i) {
    return new RatNum(i);
  }

  // convenient way to make a RatPoly
  private RatPoly poly(int coef, int expt) {
    return new RatPoly(coef, expt);
  }

  // Convenient way to make a quadratic polynomial, arguments
  // are just the coefficients, highest degree term to lowest
  private RatPoly quadPoly(int x2, int x1, int x0) {
    RatPoly ratPoly = new RatPoly(x2, 2);
    return ratPoly.add(poly(x1, 1)).add(poly(x0, 0));
  }

  // convenience for valueOf
  private RatPoly valueOf(String s) {
    return RatPoly.valueOf(s);
  }

  // convenience for zero RatPoly
  private RatPoly zero() {
    return new RatPoly();
  }

  // only toString is tested here
  private void eq(RatPoly p, String target) {
    String t = p.toString();
    assertEquals(target, t);
  }

  private void eq(RatPoly p, String target, String message) {
    String t = p.toString();
    assertEquals(message, target, t);
  }

  // parses s into p, and then checks that it is as anticipated
  // forall i, valueOf(s).coeff(anticipDegree - i) = anticipCoeffForExpts(i)
  // (anticipDegree - i) means that we expect coeffs to be expressed
  // corresponding to decreasing expts
  private void eqP(String s, int anticipDegree, RatNum[] anticipCoeffs) {
    RatPoly p = valueOf(s);
    assertEquals(anticipDegree, p.degree());
    for (int i = 0; i <= anticipDegree; i++) {
      assertTrue("wrong coeff; \n" + "anticipated: " + anticipCoeffs[i]
          + "; received: " + p.getCoeff(anticipDegree - i)
          + "\n" + " received: " + p + " anticipated:" + s, p.getCoeff(anticipDegree - i).equals(anticipCoeffs[i]));
    }
  }

  // added convenience: express coeffs as ints
  private void eqP(String s, int anticipDegree, int[] intCoeffs) {
    RatNum[] coeffs = new RatNum[intCoeffs.length];
    for (int i = 0; i < coeffs.length; i++) {
      coeffs[i] = num(intCoeffs[i]);
    }
    eqP(s, anticipDegree, coeffs);
  }

  // make sure that unparsing a parsed string yields the string itself
  private void assertToStringWorks(String s) {
    assertEquals(s, valueOf(s).toString());
  }

  RatPoly poly1, neg_poly1, poly2, neg_poly2, poly3, neg_poly3;

  //SetUp Method depends on RatPoly add and 
  //  Because all tests run this method before executing, ALL TESTS WILL FAIL
  //  until .add and .negate do not throw exceptions. Also, any incorrectness
  //  in .add and .negate may have unforseen consiquences elsewhere in the tests,
  //  so it is a good idea to make sure these two methods are correct before
  //  moving on to others.
  //
  //Tests that are intended to verify add or negate should not use variables
  //declared in this setUp method
  @Before
  public void setUp(){
    //poly1 = 1*x^1 + 2*x^2 + 3*x^3 + 4*x^4 + 5*x^5
    poly1 = RatPoly.valueOf("1*x^1+2*x^2+3*x^3+4*x^4+5*x^5");

    //neg_poly1 = -1*x^1 + -2*x^2 + -3*x^3 + -4*x^4 + -5*x^5
    neg_poly1 = poly1.negate();

    //poly2 = 6*x^2 + 7*x^3 + 8*x^4
    poly2 = RatPoly.valueOf("6*x^2+7*x^3+8*x^4");

    //neg_poly2 = -6*x^2 + -7*x^3 + -8*x^4
    neg_poly2 = poly2.negate();

    // poly3 = 9*x^3 + 10*x^4
    poly3 = RatPoly.valueOf("9*x^3+10*x^4");

    // neg_poly3 = -9*x^3 + -10*x^4
    neg_poly3 = poly3.negate();
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Division Test
  ///////////////////////////////////////////////////////////////////////////////////////     
//    
//  @Test
//  public void testDivEvaltoSingleCoeff() {
//    // 0/x = 0
//    eq(poly(0, 1).div(poly(1, 1)), "0");
//
//    // 2/1 = 2
//    eq(poly(2, 0).div(poly(1, 0)), "2"); 
//    // x/x = 1
//    eq(poly(1, 1).div(poly(1, 1)), "1");
//
//    // -x/x = -1
//    eq(poly(-1, 1).div(poly(1, 1)), "-1");
//
//    // x/-x = -1
//    eq(poly(1, 1).div(poly(-1, 1)), "-1");
//
//    // -x/-x = 1
//    eq(poly(-1, 1).div(poly(-1, 1)), "1");
//
//    // x^100/x^1000 = 0
//    eq(poly(1, 100).div(poly(1, 1000)), "0");
//  }
//
//  @Test
//  public void testDivtoSingleTerm() {
//
//    // 5x/5 = x
//    eq(poly(5, 1).div(poly(5, 0)), "x");
//
//    // -x^2/x = -x
//    eq(poly(-1, 2).div(poly(1, 1)), "-x");
//
//    // x^100/x = x^99
//    eq(poly(1, 100).div(poly(1, 1)), "x^99");
//
//    // x^99/x^98 = x
//    eq(poly(1, 99).div(poly(1, 98)), "x");
//
//    // x^10 / x = x^9 (r: 0)
//    eq(poly(1, 10).div(poly(1, 1)), "x^9");
//  }
//
//  @Test
//  public void testDivtoMultipleTerms() {
//    // x^10 / x^3+x^2 = x^7-x^6+x^5-x^4+x^3-x^2+x-1 (r: -x^2)
//    eq(poly(1, 10).div(poly(1, 3).add(poly(1, 2))),
//        "x^7-x^6+x^5-x^4+x^3-x^2+x-1");
//
//    // x^10 / x^3+x^2+x = x^7-x^6+x^4-x^3+x-1 (r: -x)
//    eq(poly(1, 10).div(poly(1, 3).add(poly(1, 2).add(poly(1, 1)))),
//        "x^7-x^6+x^4-x^3+x-1");
//
//    // 5x^2+5x/5 = x^2+x
//    eq(poly(5, 2).add(poly(5, 1)).div(poly(5, 0)), "x^2+x");
//
//    // x^10+x^5 / x = x^9+x^4 (r: 0)
//    eq(poly(1, 10).add(poly(1, 5)).div(poly(1, 1)), "x^9+x^4");
//
//    // x^10+x^5 / x^3 = x^7+x^2 (r: 0)
//    eq(poly(1, 10).add(poly(1, 5)).div(poly(1, 3)), "x^7+x^2");
//
//    // x^10+x^5 / x^3+x+3 = x^7-x^5-3*x^4+x^3+7*x^2+8*x-10
//    // (with remainder: 29*x^2+14*x-30)
//    eq(poly(1, 10).add(poly(1, 5)).div(
//        poly(1, 3).add(poly(1, 1)).add(poly(3, 0))),
//        "x^7-x^5-3*x^4+x^3+7*x^2+8*x-10");
//  }
//
//  @Test
//  public void testDivComplexI() {
//    // (x+1)*(x+1) = x^2+2*x+1
//    eq(poly(1, 2).add(poly(2, 1)).add(poly(1, 0)).div(
//        poly(1, 1).add(poly(1, 0))), "x+1");
//
//    // (x-1)*(x+1) = x^2-1
//    eq(poly(1, 2).add(poly(-1, 0)).div(poly(1, 1).add(poly(1, 0))), "x-1");
//  }
//
//  @Test
//  public void testDivComplexII() {
//    // x^8+2*x^6+8*x^5+2*x^4+17*x^3+11*x^2+8*x+3 =
//    // (x^3+2*x+1) * (x^5+7*x^2+2*x+3)
//    RatPoly large = poly(1, 8).add(poly(2, 6)).add(poly(8, 5)).add(
//        poly(2, 4)).add(poly(17, 3)).add(poly(11, 2)).add(poly(8, 1))
//        .add(poly(3, 0));
//
//    // x^3+2*x+1
//    RatPoly sub1 = poly(1, 3).add(poly(2, 1)).add(poly(1, 0));
//    // x^5+7*x^2+2*x+3
//    RatPoly sub2 = poly(1, 5).add(poly(7, 2)).add(poly(2, 1)).add(
//        poly(3, 0));
//
//    // just a last minute typo check...
//    eq(sub1.mul(sub2), large.toString());
//    eq(sub2.mul(sub1), large.toString());
//
//    eq(large.div(sub2), "x^3+2*x+1");
//    eq(large.div(sub1), "x^5+7*x^2+2*x+3");
//  }
//
//  @Test
//  public void testDivExamplesFromSpec() {
//    // seperated this test case out because it has a dependency on
//    // both "valueOf" and "div" functioning properly
//
//    // example 1 from spec
//    eq(valueOf("x^3-2*x+3").div(valueOf("3*x^2")), "1/3*x");
//    // example 2 from spec
//    eq(valueOf("x^2+2*x+15").div(valueOf("2*x^3")), "0");
//  }
//
//  @Test
//  public void testDivExampleFromPset() {
//    eq(valueOf("x^8+x^6+10*x^4+10*x^3+8*x^2+2*x+8").div(
//        valueOf("3*x^6+5*x^4+9*x^2+4*x+8")), "1/3*x^2-2/9");
//  }
//
//  @Test
//  public void testBigDiv() {
//    // don't "fix" the "infinite loop" in div by simply stopping after
//    // 50 terms!
//    eq(
//        valueOf("x^102").div(valueOf("x+1")),
//        "x^101-x^100+x^99-x^98+x^97-x^96+x^95-x^94+x^93-x^92+x^91-x^90+"
//            + "x^89-x^88+x^87-x^86+x^85-x^84+x^83-x^82+x^81-x^80+x^79-x^78+"
//            + "x^77-x^76+x^75-x^74+x^73-x^72+x^71-x^70+x^69-x^68+x^67-x^66+"
//            + "x^65-x^64+x^63-x^62+x^61-x^60+x^59-x^58+x^57-x^56+x^55-x^54+"
//            + "x^53-x^52+x^51-x^50+x^49-x^48+x^47-x^46+x^45-x^44+x^43-x^42+"
//            + "x^41-x^40+x^39-x^38+x^37-x^36+x^35-x^34+x^33-x^32+x^31-x^30+"
//            + "x^29-x^28+x^27-x^26+x^25-x^24+x^23-x^22+x^21-x^20+x^19-x^18+"
//            + "x^17-x^16+x^15-x^14+x^13-x^12+x^11-x^10+x^9-x^8+x^7-x^6+x^5-"
//            + "x^4+x^3-x^2+x-1");
//  }
//  
//  @Test // p / 0 = NaN
//  public void testDivByZero() {
//     assertEquals(RatPoly.NaN, poly2.div(RatPoly.ZERO));
//     assertEquals(RatPoly.NaN, neg_poly1.div(RatPoly.ZERO));
//     assertEquals(RatPoly.NaN, poly1.div(RatPoly.ZERO));
//  }
//    
//    @Test // Zero Polynomial / Zero Polynomial == NaN
//    public void testDivisionZeroFromZero() {
//      assertEquals(RatPoly.NaN, RatPoly.ZERO.div(RatPoly.ZERO));
//    }
//    
//    //Following test method depends on correctness of negate
//    @Test // p / Zero Polynomial == NaN && Zero Polynomial / p == 0
//    public void testDivisionZeroAndNonZero() {
//      assertEquals(RatPoly.ZERO, RatPoly.ZERO.div(poly1));
//    }
//    
//    @Test // NaN / NaN == NaN
//    public void testDivisionNaNtoNaN() {
//      assertEquals(RatPoly.NaN, RatPoly.NaN.div(RatPoly.NaN));
//    }
//    
//    @Test // p / NaN == NaN && NaN / p == NaN
//    public void testDivisionNaNtoNonNaN() {
//      assertEquals(RatPoly.NaN, RatPoly.NaN.div(poly1));
//        assertEquals(RatPoly.NaN, poly1.div(RatPoly.NaN));
//    }
//    
//    @Test // p / 1 == p
//    public void testDivisionByOne() {
//      assertEquals(poly2, poly2.div(RatPoly.valueOf("1")));
//    }

}
