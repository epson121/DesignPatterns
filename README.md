# Design Patterns

University course on Design Patterns. Course follows the patterns described in GoF book on design patterns. It consists of 4 homework assignments in Java programming language.

Homework 1:
*   Virtual File System
 *   Emulation of 2 file systems (not in their entirety) - Ext3 and NTFS
 *   Given a directory as argument, one can see its files and subdirectories. 
 *   Structure manipulation is also possible (add, list, copy, move, delete file and/or folder), and it is affecting the real file system also.
 *   Design patterns used: Abstract Factory, Singleton, Composite

Homework 2:
*  Sports simulation
 * Simulation of a sport competition with clubs given as a txt file
 * Loading of clubs, creating tables, generating results, keeping track of efficiency
 * Clubs can be in different states (competitor, weak competitor and disqualified)
 * Design patterns used: State, Observer, Command and Memento (version with a public class in the same package, without undo)

Homework 3:
*  Url content control
  * Load a web page and reload in interval
  * Parse it and get all <a></a> tags and their href attributes
  * Jump from one page to another
  * Save states and keep track of the statistics
  * Design patterns used: MVC, Memento, Chain of responsibility

Homework 4:
*  Url content control II
  * Load a web page and reload in interval (load from web or from cache)
  * Parse it and get all <a></a> tags and their href attributes
  * Jump from one page to another
  * Save pages in cache and keep track of the statistics
  * Persist the statistics for multiple usage
  * Design patterns used: MVC, Cache, Strategy
