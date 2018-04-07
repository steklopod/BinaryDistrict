package wtf.scala.e04.tree

import org.scalatest.{FunSuite, Matchers}

class TreeSpec extends FunSuite with Matchers {

  test("Node.pickBestNodeForRemoveForMaximizeWeight should pick best node for remove for maximize tree weight") {
    val testTree = Tree.fillWithRandomInts(10, -1000, 1000, 123)
    Tree.pickBestNodeForRemoveForMaximizeWeight(testTree).map(_.index) shouldBe Some(8)
  }

  test("Node.pickBestNodeForRemoveForMaximizeWeight should returns None for positive element tree") {
    Tree.pickBestNodeForRemoveForMaximizeWeight(Tree(1)) shouldBe None
  }

  test("Node.pickBestNodeForRemoveForMaximizeWeight should returns same element for negative element tree") {
    Tree.pickBestNodeForRemoveForMaximizeWeight(Tree(-1)).map(_.index) shouldBe Some(1)
  }

  test("Node.add should adds element to tree") {
    Node.add(Node(-1), 10, 2) == new Node(1, -1, right = Node(2, 10)) shouldBe true
  }

  test("iterator should returns correct tree iterator") {
    val root = Tree.fillWithRandomInts(4, -1000, 1000, 321)
    val elements = root.iterator.toList
    elements(0) == Node(2, -966) shouldBe true
    elements(1) == Node(1, -962, left = Node(2, -966), right = Node(3, -277, left = Node(4, -552))) shouldBe true
    elements(2) == Node(4, -552) shouldBe true
    elements(3) == Node(3, -277, left = Node(4, -552)) shouldBe true
    elements.size shouldBe 4
  }

  test("addNodeWithWidth should adds element with width to tree") {
    val testTree = Tree.fillWithRandomInts(3, -1000, 1000, 123)
    testTree.size shouldBe 3
    (testTree addNodeWithWidth 10000).size shouldBe 4
  }

  test("result removeNode of root should be Nil") {
    val node = Node(-1)
    node removeNode node shouldBe Nil
  }

  test("result removeNode of leaf should be root") {
    val tree = Tree.fillWithRandomInts(2, -1000, 1000, 123)
    val nodes = tree.iterator.toSeq
    val root = nodes(0)
    val removed = nodes(1)
    (tree removeNode removed) == Node(1, root.value) shouldBe true
  }

  test("result removeNode in depth should works correct") {
    val tree = Tree.fillWithRandomInts(4, -1000, 1000, 123)
    val nodes = tree.iterator.toSeq
    val removed = nodes(3)
    val afterRemove = tree removeNode removed
    afterRemove == Node(1, -332, left = Nil, right = Node(2, -161)) shouldBe true
  }

  test("trimTree should works correct - 1") {
    val tree = Tree.fillWithRandomInts(4, -1000, 1000, 5451)
    val (trimmed, removed) = Tree.trimTree(tree, 3)
    trimmed == Node(1, -103, left = Nil, right = Node(3, 535, right = Node(4, 988))) shouldBe true
    removed == List(Node(2, -310)) shouldBe true
  }

  test("trimTree should works correct - 2") {
    val tree = Tree.fillWithRandomInts(100, -1000, 1000, 5221)
    val (trimmed, removed) = Tree.trimTree(tree, 3)
    val a = Node(19, -895, right = Node(26, -823, left = Node(85, -853), right = Node(65, -813)))
    println(a.toString())
    removed.size shouldBe 3
    removed(0) == Node(100, -967) shouldBe true
    removed(1) == Node(36, -964, right = Node(54, -961, right = Node(86, -958))) shouldBe true
    removed(2) == Node(19, -894, right = Node(26, -823, left = Node(85, -853), right = Node(65, -813))) shouldBe true
  }

}
