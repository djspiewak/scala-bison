package edu.uwm.cs.util

object CharUtil {
  def lit(ch : Char) : String = ch match {
    case '\'' => "'\\''";
    case '\n' => "'\\n'";
    case '\t' => "'\\t'";
    case '\f' => "'\\f'";
    case '\r' => "'\\r'";
    case '\b' => "'\\b'";
    case 0 => "'\\0'";
    case _ => {
      if (ch < 32 || ch > 126) String.format("'\\u%04x'",new java.lang.Integer(ch)) else "'" + ch + "'";
    }
  }
  
  def main(args : Array[String]) = { println ("lit('\u00fc') = "+lit('\u00fc')); }
}