package za.co.shantanu.fadnis.utils

object ArgumentParser {
  def parseArgs(args: Array[String]): Map[String, String] = {
    args.map(arg => {
      val tokens = arg.split("->")
      (tokens(0).trim, tokens(1).trim)
    }).toMap
  }
}
