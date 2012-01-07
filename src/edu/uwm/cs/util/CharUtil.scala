package edu.uwm.cs.util

object CharUtil {
  def lit(ch : Char) : String = ch match {
    case '\'' => "'\\''";
    case '\n' => "'\\n'";
    case '\t' => "'\\t'";
    case '\f' => "'\\f'";
    case '\r' => "'\\r'";
    case '\b' => "'\\b'";
    case '\0' => "'\\0'";
    case _ => {
      if (ch < 32) "'\\0" + Integer.toOctalString(ch) + "'"; else "'" + ch + "'";
    }
  }
}