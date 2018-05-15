# The Problem

Suppose you have a handful of pieces and a rectangular chessboard with a predefined size. You need to find all distinct
layouts of these pieces such that no piece attacks the others. The color of the piece does not matter, and there are no 
pawns among the pieces.

# The Input
 * board dimensions, m×n
 * the list of pieces to lay out on the board

# The Output
 * the number of distinct layouts, such that neither piece can attack the others.
 
# Notes
Your application needs to be written in scala and be able to run on a modern laptop with 4Gb of RAM. The soultion should 
probably be less than 500 LOC, so if your codebase grows bigger, you are most likely taking the wrong approach. A "well-designed" solution will lay out two kings, a queen, a rook, a bishop, and a knight on a 6×9 board within "a reasonable" amount of time. Please note, however, that we favor code clarity over performance.

There are no restrictions on the input/output data formats, but please make sure your application is user-friendly.