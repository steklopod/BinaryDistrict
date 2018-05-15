package wtf.scala.e04.tree

import Node._

import scala.util.Random

object Tree {
  def tree2nodeOption[T](t: Tree[T]): Option[Node[T]] = t match {
    case n: Node[T] => Some(n)
    case Nil => None
  }

  def fillWithRandomInts(n: Int, weightFrom: Int, weightTo: Int, seed: Long = System.currentTimeMillis()): Node[Int] = {
    assert(n > 0, "tree nodes count should be positive")
    assert(weightTo > 0, "random bound should be positive")
    val random = new Random(seed)

    def generateWeight: Int = random.nextInt((weightTo - weightFrom) + 1) - weightTo

    val root = Node(generateWeight)
    Seq.fill(n - 1)(generateWeight).foldLeft(root)(_ addNodeWithWidth _)
  }

  /**
    * Выполняет стрижку дерева. Используйте метод pickBestNodeForRemoveForMaximizeWeight для выбора вершины для удаления.
    * @param tree дерево
    * @param maxRemoveOperations максимальное количество операций удаления
    * @return дерево после стрижки и удаленные вершины
    */
  def trimTree(tree: Tree[Int], maxRemoveOperations: Int): (Tree[Int], Seq[Node[Int]]) = ???

  /**
    * Возвращает лучшую вершину для удаления, ведущую к максимальному увеличению веса дерева
    * @param root дерево
    * @return опционально возвращает ноду для удаления
    */
  def pickBestNodeForRemoveForMaximizeWeight[T](root: Tree[T])(implicit num: Numeric[T]): Option[Node[T]] = ???

  def apply[T](el: T)(implicit num: Numeric[T]): Tree[T] = Node(el)

  def empty[T](el: T): Tree[T] = Nil
}

sealed trait Tree[+T] extends Iterable[Node[T]] {
  def weight: Int
}

case object Nil extends Tree[Nothing] {
  override def weight: Int = 0
  override def iterator: Iterator[Node[Nothing]] = Iterator.empty
}

object Node {
  type Index = Int

  def add[T](root: Node[T], newNodeWeight: T, newIndex: Index)(implicit num: Numeric[T]): Node[T] = {
    import root._
    import num._

    val updatedTree = if (newNodeWeight <= value) {
      root.left match {
        case oldNode: Node[T] =>
          root.copy(left = add(oldNode, newNodeWeight, newIndex))
        case Nil =>
          root.copy(left = apply(newNodeWeight, newIndex))
      }
    } else {
      root.right match {
        case oldNode: Node[T] =>
          root.copy(right = add(oldNode, newNodeWeight, newIndex))
        case Nil =>
          root.copy(right = apply(newNodeWeight, newIndex))
      }
    }
    updatedTree.asInstanceOf[Node[T]]
  }

  def apply[T](value: T)(implicit num: Numeric[T]): Node[T] = new Node[T](1, value)

  private def apply[T](value: T, index: Index)(implicit num: Numeric[T]): Node[T] = new Node[T](index, value)
}

case class Node[+T] private[tree](index: Index,
                                 value: T,
                                 left: Tree[T] = Nil,
                                 right: Tree[T] = Nil)(implicit num: Numeric[T]) extends Tree[T] {

  import Tree._

  def removeNode[U >: T](n: Node[U])(implicit num: Numeric[U]): Tree[U] = {
    replace(n, Nil)
  }

  def addNodeWithWidth[U >: T](w: U)(implicit num: Numeric[U]): Node[U] = {
    add(this, w, maxBy(_.index).index + 1)
  }

  private[tree] def replace[U >: T](oldValue: Node[U], newValue: Tree[U])(implicit num: Numeric[U]): Tree[U] = {
    val newRoot = copy[U]()

    def replace(oldValue: Node[U], newValue: Tree[U], current: Node[U], parentOpt: Option[Node[U]] = None): Option[Tree[U]] = {
      val updatedNodeOpt = if (oldValue == current) {
        Some(newValue)
      } else if (left == oldValue) {
        Some(current.copy(left = newValue))
      } else if (right == oldValue) {
        Some(current.copy(right = newValue))
      } else None
      (parentOpt, updatedNodeOpt) match {
        // if it has parents, we need update them
        case (Some(parent), Some(updatedNode)) =>
          Some(parent.replace(current, newValue))
        // if it's root
        case (None, Some(updatedNode)) =>
          Some(updatedNode)
        case (parentOpt, None) =>
          def findLeft = tree2nodeOption(current.left).flatMap(l => replace(oldValue, newValue, l, Some(current)))

          def findRight = tree2nodeOption(current.right).flatMap(r => replace(oldValue, newValue, r, Some(current)))

          val replacedOpt = findLeft orElse findRight
          parentOpt
            .map(parent => replacedOpt.map(replaced => parent.replace(current, replaced)))
            .getOrElse(replacedOpt)
      }
    }

    replace(oldValue, newValue, newRoot).getOrElse(this)
  }

  def weight: Int = {
    import num._
    iterator.map(_.value).sum.toInt()
  }

  /**
    * @return Вовзращает итератор прохода дерева в глубину слева направо (depth-first search)
    */
  override def iterator: Iterator[Node[T]] = ???

  override def toString(): String = {
    s"Node[$index] = $value Left: [$left], Right: [$right]"
  }
}

/**
  * Дано бинарное дерево с идентификаторами вершин с 1 до N. 1 - корень дерева. Каждому элементу дерева присвоен вес W.
  * K - максимальное возможное количество операций удаления.
  *
  * Нужно реализовать метод удаления поддерева из дерева.
  *
  * def removeNode(n: Node[T]): Tree[T] - где n, корень поддерева, которое будет удалено
  *
  * Вес дерева - сумма всех весов его элементов.
  * Применив этот метод не более K раз, нужно получить дерево с максимально возможным весом на каждом отдельном шаге удаления.
  * То есть необходимо найти один элемент, с которым вес дерева будет максимален и удалить его, повторять до тех пор, пока невозможно будет увеличить вес удалением элемента.
  * Дерево должно генерироваться автоматически, случайным образом с параметрами в пределах заданных значений.
  *
  * На выход должен быть выведен вес начального дерева, вес финального дерева и количество удаленных поддеревьев.
  *
  * 1 < K < 100 - операций удаления
  * 100 <= N <= 1000 (дерево содержит до 1000 нод)
  * -1000 <= W <= 1000 - вес ноды дерева
  */
object NodeApp extends App {
  val maxRemoveOperations = args.lift(0).map(_.toInt).filter(k => k > 1 && k < 100).getOrElse(throw new IllegalArgumentException("Max remove operations should be integer between 1 and 100"))
  val nodesNumber = args.lift(1).map(_.toInt).filter(n => n >= 100 && n <= 1000).getOrElse(throw new IllegalArgumentException("Nodes number should be integer between 100 and 1000"))
  val startTree = Tree.fillWithRandomInts(nodesNumber, -1000, 1000)
  val (resultTree, removedNodes) = Tree.trimTree(startTree, maxRemoveOperations)
  println(s"Start weight ${startTree.weight}, result weight ${resultTree.weight}, removed: ${removedNodes.length}, deep removed: ${removedNodes.map(_.iterator.length).sum}")
}
