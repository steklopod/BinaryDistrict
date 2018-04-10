package wtf.scala.e02

import java.util.Random

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


  def generateTeam(): Team = {
    new Team(Seq(Car(0), Moto(0), Truck(0)))
  }

  //    TODO
  var r = Random.next

  /**
    * Returns the result of two teams competing on track
    *
    * @param track
    * @param teamA
    * @param teamB
    * @return 1 if teamA wins, -1 if teamB wins, 0 if they finish at the same time
    */

  def compete(track: Track, teamA: Team, teamB: Team): Double = {
    teamA.cars.zip(teamB) match {
      case (Seq((car1: Car, car2: Car), (moto1: Moto, moto2: Moto), (truck1, truck2))) =>

        val carTime1 = track.carPartDistance
    }

  }

  def stepForCars(track: Track, moto: Moto): Int = {

    //        TODO - foldLeft
    val (_, _, carResult) = (0 until 10)
      .foldLeft(car1, track.carPartDistance, 0)[(Car, Int)] {

      //          TODO - distanceLeft
      case ((car, distanceLeft, _), i) =>
        val newDistanceLeft = distanceLeft - car.speed
        if (newDistanceLeft <= 0) {
          (car, distanceLeft, res)
        } else {
//          (acceleratorCar, newDistanceLeft, 0)
        }
//        val acceleratorCar = car.accelerate
//        (acceleratorCar, newDistanceLeft)
    }


  }

  class Team(cars: Seq[Vehicle]) {
    require(cars.length == 3)
  }

  case class Track(carPartDistance: Int, truckPartDistance: Int, truckLoad: Int)

  trait Vehicle {
    def speed: Int

    def accelerate(): this.type
  }

  object Car {
    val maxSpeed = 80
    val minSpeed = 40
    val acceleration = 10
  }

  final case class Car(override val speed: Int) extends Vehicle {
    //  TODO - override
    override def acceleration: Car.this.type = Car(math.min(speed + Car.acceleration, Car.maxSpeed))
  }

  object Moto {
    val maxSpeed = 100
    val minSpeed = 20
    val acceleration = 15
  }

  final case class Moto(override val speed: Int) extends Vehicle {
    override def acceleration: Moto.this.type = Moto(math.min(speed + Moto.acceleration, Moto.maxSpeed))

  }

  object Truck {
    val maxSpeed = 60
    val minSpeed = 10
    val acceleration = 5
  }

  final case class Truck(override val speed: Int) extends Vehicle {

    private def accelerationWithLoad(load: Int): Int = Truck.acceleration - load / 10

    override def acceleration: Truck.this.type = Truck(math.min(speed + Truck.acceleration, Truck.maxSpeed))

  }
