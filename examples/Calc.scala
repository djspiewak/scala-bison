object Calc extends App {
  {
    val cs = new CalcScanner(System.in);
    val cp = new CalcParser();
    cp.yyreset(cs);
    if (!cp.yyparse()) {
      println("Fatal error.  Stopped.");
    }
  }
}