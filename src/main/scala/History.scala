class History {
  private val entries: scala.collection.mutable.ListBuffer[String] = scala.collection.mutable.ListBuffer()

  def addEntry(entry: String): Unit = {
    entries += entry
  }

  def getEntries(): List[String] = entries.toList

  def clear(): Unit = {
    entries.clear()
  }
}
