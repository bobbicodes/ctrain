# ctrain

I wanted to be able to run the problems on 4clojure without having to load a browser or even a desktop session, so I took the data and created a little text-based runner. It only does the first test of each problem, for simplicity. 

## Installation

Simply clone this repository, assuming you have Java and Leiningen installed.

## Usage

cd into project directory and type lein run.
When you answer a problem correctly, it increments the value stored in a file in the project root called "prob".

## Current bugs

1. It actually evaluates the expressions you enter, so it will crash if you put in something that doesn't make sense.

2. For now it only does the first of the tests. While this does make it simpler, it may be completed in the future.

3. Many of them will pass if you enter a blank. This is actually correct, because the expression is indeed equal to itself!

4. It has no concept of restricted functions. It's just a dirty free-for-all.
