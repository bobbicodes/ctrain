# ctrain

Run the 4clojure problems without having to load a graphical browser or desktop session. Even do it over ssh!

## Usage

Install Java and [Leiningen](https://leiningen.org/) if needed. Clone this repo and type lein run while in the ctrain directory.
When you answer a problem correctly, it stores its number in a file in the project root called "prob".

## Current bugs

1. It actually evaluates the expressions you enter, so it will freak out and crash if you put in something that doesn't make sense. I have to figure out how to evaluate the answer only if it is a valid answer. But how do you evaluate that?

2. It has no concept of restricted functions in your answer. It's just a dirty free-for-all.
