package wtf.scala.e02

import scala.util.Random

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

object RelayRacing extends App {

  /**
    * Generate racing team
    *
    * Hint: to generate random numbers, use scala.util.Random
    *
    */
  def generateTeam(): Team = {
    Team(Seq(Car(Random.nextInt(30)), Moto(Random.nextInt(30)), Truck(Random.nextInt(30))))
  }

  def stepsForVehicle(track: Track, truck: Vehicle): Int = {
    val (_, _, carResult) = (0 until 1000).foldLeft((truck, track.carPartDistance, 0)) {
      case ((car, distanceLeft, res), i) =>
        val newDistanceLeft = distanceLeft - car.speed
        val acceleratedCar = car.accelerate(track.truckLoad)
        if (newDistanceLeft <= 0) {
          (car, distanceLeft, i)
        } else {
          (acceleratedCar, newDistanceLeft, 0)
        }
    }
    carResult
  }

  /**
    * Returns the result of two teams competing on track
    *
    * @param track
    * @param teamA
    * @param teamB
    * @return 1 if teamA wins, -1 if teamB wins, 0 if they finish at the same time
    */
  def compete(track: Track, teamA: Team, teamB: Team): Double = {
    val stepsForVehicleWithTrack = stepsForVehicle(track, _: Vehicle)
    val teamScore1 = teamA.cars.map(stepsForVehicleWithTrack).sum
    val teamScore2 = teamB.cars.map(stepsForVehicleWithTrack).sum

    if (teamScore1 == teamScore2) {
      0
    } else if (teamScore1 > teamScore2) {
      1
    } else {
      -1
    }
  }

  val team1 = generateTeam()
  val team2 = generateTeam()

  println(compete(Track(1000, 500, 2000, 15), team1, team2))
}

case class Team(cars: Seq[Vehicle]) {
  require(cars.length == 3)
}

case class Track(carPartDistance: Int, motoPartDistance: Int, truckPartDistance: Int, truckLoad: Int)

trait Vehicle {
  def speed: Int

  def maxSpeed: Int

  def minSpeed: Int

  def acceleration: Int

  private def accelerationWithLoad(load: Int): Int = acceleration - load / 10

  def accelerate(load: Int = 0): Truck = Truck(math.min(speed + accelerationWithLoad(load), maxSpeed))
}

final case class Car(override val speed: Int = 40) extends Vehicle {
  override def maxSpeed: Int = 80

  override def minSpeed: Int = 40

  override def acceleration: Int = 10
}

final case class Moto(override val speed: Int = 20) extends Vehicle {
  override def maxSpeed: Int = 120

  override def minSpeed: Int = 20

  override def acceleration: Int = 15
}

final case class Truck(override val speed: Int = 10) extends Vehicle {
  override def maxSpeed: Int = 60

  override def minSpeed: Int = 10

  override def acceleration: Int = 5
}