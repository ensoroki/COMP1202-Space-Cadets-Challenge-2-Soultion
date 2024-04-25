刚开始学习Java时的黑历史，受不了了

# COMP1202-Space-Cadets-Challenge-2-Soultion
My solutions of Challenge 2: Bare Bones.

The main difficult part is how to deal with the while loop.
My idea is that, the interpreter will first execute the code line-by-line.
When encountering the while conditions, use a stack to record the line number of the while condition
  then continuing processing the code.
When encountering the end statement, the stack will pop out the line number and check the condition of loop.

I use a hashmap to record the tokens (variables).
The tokens will be first scanned when first scanning the code.

It is my first time using Java dealing with such problems, 
I'm not familiar with this programming language, so most part of the code should be optimized.

Thanks for reading my solution!
