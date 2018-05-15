package wtf.scala.e02

/*
  Design and implement Relay Racing with multiple kinds of vehicles: Car, Moto and Truck

  First part of race is Cars, second - Moto, third - Truck (implementing trait Vehicle)
  On the third part of race Trucks carry some specified amount of load which decreases their speed
  (Load is a Track param)

  Each kind of vehicle should have maxSpeed, minSpeed bounds

  Each team has one Car, one Moto and one Truck.
  The competitions takes place in a special Track.

  Implement all the classes and methods which generate teams and show
  result of two teams competing on a specified track

  You should use at least one parameter for every car (speed) but can use more for example racer skill
 */

object RelayRacing {

  /**
    * Generate racing team
    *
    * Hint: to generate random numbers, use scala.util.Random
    *
    */
  def generateTeam(): Team = ???

  /**
    * Returns the result of two teams competing on track
    * @param track
    * @param teamA
    * @param teamB
    * @return 1 if teamA wins, -1 if teamB wins, 0 if they finish at the same time
    */
  def compete(track: Track, teamA: Team, teamB: Team): Double = ???

}

class Team
class Track

trait Vehicle
