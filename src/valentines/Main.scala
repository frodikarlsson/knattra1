package valentines

import scala.collection.JavaConverters.{asScalaBufferConverter, mapAsScalaMapConverter}
import scala.collection.mutable

object Main {
  val quals = Vector("eyecolor", "financialStatus", "height", "occupation", "animals")
  var people: Array[Person] = ReadFile.readJson("C:/Users/easte/Downloads/matchProfiles.json").asScala.toArray
  var matchmap: mutable.HashMap[Int, (Int, Double)] = new mutable.HashMap[Int, (Int, Double)]()
  val divider: Int = people.length/3
  def main(args: Array[String]): Unit = {
    var totalScore = 0.0
    var matchMapArray = new Array[(Int, (Int, Double))](1)
    for(i <- Range(0, people.length-divider, divider)) {
      val loopPeople = people.slice(i, i+divider)
      val subsets = loopPeople.toSet.subsets(2).toArray.map(o => {
        val p = o.toArray
        (p(0), p(1))
      }).sortWith(_._1.index < _._1.index)
      for (p <- subsets) {
        var tmpScore = 0.0
        tmpScore = (score(p._1, p._2)+score(p._2, p._1))/2
        if (!matchmap.contains(p._1.index) || tmpScore > matchmap(p._1.index)._2) {
          matchmap.put(p._1.index, (p._2.index, tmpScore))
        }
      }
      // val loopMatchMapArray = matchmap.toArray.sortWith(_._2._2 > _._2._2)
      //loopMatchMapArray.foreach(o=>println("Person " + o._1.index + " and " + o._2._1.index + " with score " + o._2._2))


    }
    matchMapArray=matchmap.toArray.sortWith(_._2._2 > _._2._2)
    matchMapArray.foreach(o=>totalScore+=o._2._2)

    var sb: StringBuilder = new StringBuilder()
    matchMapArray.foreach(o=>{
      sb.append("(")
        .append(o._1)
        .append(":")
        .append(o._2._1)
        .append("),")
    })
    println(sb)
    //println(matchMapArray.filter(_!=null).map(o=>(o._1, o._2._1)).mkString("", ",", ""))
    println(totalScore)
  }
  def doValentines(): Unit ={
    var totalScore = 0.0
    var matchMapArray = new Array[(Int, (Int, Double))](1)
    for(i <- Range(0, people.length-divider, divider)) {
      val loopPeople = people.slice(i, i+divider)
      val subsets = loopPeople.toSet.subsets(2).toArray.map(o => {
        val p = o.toArray
        (p(0), p(1))
      }).sortWith(_._1.index < _._1.index)
      for (p <- subsets) {
        var tmpScore = 0.0
        tmpScore = (score(p._1, p._2)+score(p._2, p._1))/2
        if (!matchmap.contains(p._1.index) || tmpScore > matchmap(p._1.index)._2) {
          matchmap.put(p._1.index, (p._2.index, tmpScore))
        }
      }
      // val loopMatchMapArray = matchmap.toArray.sortWith(_._2._2 > _._2._2)
      //loopMatchMapArray.foreach(o=>println("Person " + o._1.index + " and " + o._2._1.index + " with score " + o._2._2))


    }
    matchMapArray=matchmap.toArray.sortWith(_._2._2 > _._2._2)
    matchMapArray.foreach(o=>totalScore+=o._2._2)

    var sb: StringBuilder = new StringBuilder()
    matchMapArray.foreach(o=>{
      sb.append("(")
        .append(o._1)
        .append(":")
        .append(o._2._1)
        .append("),")
    })
    println(sb)
    //println(matchMapArray.filter(_!=null).map(o=>(o._1, o._2._1)).mkString("", ",", ""))
    println(totalScore)
  }
  def canMatch(a: Person, b: Person): Boolean ={
    val genderWorks = (a.lookingfor==b.gender || a.lookingfor=="Either") &&
      (b.lookingfor==a.gender || b.lookingfor=="Either")
    val ageWorks = ageCompare(a, b) && ageCompare(b,a)
    !a.equals(b) && genderWorks && ageWorks
  }
  def ageCompare(a: Person, b: Person): Boolean ={
    val bBool = for(p <- a.agePreferences) yield p.toLowerCase() match {
      case "very young" => b.age<=a.age-10
      case "younger" => b.age>=a.age-10 && b.age<=a.age-5
      case "same age" => b.age>=a.age-5 && b.age<=a.age+5
      case "older" => b.age>=a.age+5 && b.age<=a.age+10
      case "much older" => b.age>=a.age+10 && b.age<=a.age+15
      case "ancient" => b.age>=a.age+15
      case _ => true
    }
    bBool.contains(true)
  }
  def score(a: Person, b: Person): Double ={
    var score = 0
    val aPref = a.preferences
    val aQual = a.qualities
    val bPref = b.preferences
    val bQual = b.qualities
    //aPref.foreach { case (key, values) => println(key + " -> " + values.mkString("-"))
   // }
   // println(bQual.toString())

    for(i <- quals.indices) {
      val ap = aPref(i).asScala
      val bp = bPref(i).asScala
      val aq = aQual(i).asScala
      val bq = bQual(i).asScala
      val q = quals(i)
      var apbq=ap(q).exists(_.equalsIgnoreCase(bq(q))) || (
        //ap(q).size==1 &&
        ap(q).exists(_.equalsIgnoreCase("None")))

      var bpaq=bp(q).exists(_.equalsIgnoreCase(aq(q))) || (
        //bp(q).size==1 &&
        bp(q).exists(_.equalsIgnoreCase("None")))


      if (apbq && bpaq) score+=1
    }
    score/5.0
  }
}
