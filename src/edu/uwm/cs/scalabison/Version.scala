package edu.uwm.cs.scalabison;

object Version {
  val version : String = "0.8";
}

// History:
// 0.76: More debugging information on errors
// 0.77: Catch ALL exceptions, not just in shifts
// 0.78: Don't add any default actions.
// 0.79: use simple name of file, defaultInitial not Integer
// 0.791: don't try recovery when at EOF
// 0.792: Bug introduced in 0.78: need to handle followset too
// 0.793: Fixed announce conflicts & shadowing for accept actions
// 0.794: Fixed bug (identifiers could not include digits)
// 0.8: Updated for Scala 2.9.1, re-bootstrapped
