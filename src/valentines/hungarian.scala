package valentines

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object hungarian {
  val quals = Vector("eyecolor", "financialStatus", "height", "occupation", "animals")
  var people: ArrayBuffer[Person] = ArrayBuffer[Person](ReadFile.readJson("C:/Users/easte/Downloads/matchProfiles.json").asScala: _*).reverse
  var matchmap: mutable.HashMap[Int, (Int, Double)] = new mutable.HashMap[Int, (Int, Double)]()
//  people=people.sortWith(
//    _.preferences.map(a=>a.size()).sum >
//    _.preferences.map(b=>b.size()).sum
//  )
  def main(args: Array[String]): Unit = {
    doValentine()
  }

  def doValentine(): Unit ={
    for(r <- people){
      var maxScore =(0,0.0)
      for(c <- people){
        if(Main.canMatch(r,c)) {
          val score = Main.score(r, c)
          if(score>maxScore._2) maxScore=(c.index, score)
        }
      }
      matchmap.put(r.index, maxScore)
    }
    val matchMapArray = matchmap.toArray
    println(matchMapArray.map(o => "(" + o._1 + ":" + o._2._1 + ")").mkString(sep=","))
    println(matchMapArray.map(a=>a._2._2).sum)
  }
}
