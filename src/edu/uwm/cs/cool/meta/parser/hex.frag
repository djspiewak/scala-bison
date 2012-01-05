  def isHex(ch : Char) : Int = {
    ch >= '0' && ch <= '9' ||
    ch >= 'a' && ch <= 'f' ||
    ch >= 'A' && ch <= 'F' 
  }

  def fromHex(ch : Char) : Int = {
    if (ch >= '0' && ch <= '9') ch - '0';
    else if (ch >= 'a' && ch <= 'f') ch - 'a';
    else if (ch >= 'A' && ch <= 'F') ch - 'A';
    else throw new GrammarSpecificationError("Illegal hex digit '" + ch + "'")
  }
