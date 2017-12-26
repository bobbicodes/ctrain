# ctrain

Run the 4clojure problems without having to load a graphical browser or desktop session. Even do it over ssh!

## Usage

Install Java and Leiningen if needed. Clone this repo and type lein run while in the ctrain directory.
When you answer a problem correctly, it stores its number in a file in the project root called "prob".

## Current bugs

1. It actually evaluates the expressions you enter, so it will freak out and crash if you put in something that doesn't make sense.

2. For now it only does the first of the tests. While I kinda like it because it makes it simpler, I probably ought to fix it.

3. It has no concept of restricted functions in your answer. It's just a dirty free-for-all.
