# ANNResearch
Research Library to Investigate how Artificial Neural Nets Work, and Their Strengths and Limitations

I've decided to revisit Artificial Neural Networks in the hopes they will be a valuable tool for my larger project of creating conversational AI to pass the Turing Test. The need for ANNs in conversational AI stems from the need to identify high level concepts in a sentence, for instance the subject of a sentence. Tools exist which accomplish this task, for instance Stanford CoreNLP (http://stanfordnlp.github.io/CoreNLP/), and I believe they use neural nets - at least the Stanford Lexical Parser does (http://nlp.stanford.edu/software/parser-faq.shtml#rnn). In any case, it seems to me an effective way to accomplish the task, but for my conversational AI I would need more control of the software, so I can customize it to find even higher level concepts, further from the level of pure grammar, such as parts of the sentence which evoke emotion. 

Unsupervised ANNs may also prove incredibly useful, especially since creating labelled training sets for every conceivable concept of interest is impossible. I have no experience coding up unsupervised ANNs, so they are one of the key goals of the ANNResearch project. 

Another for the ANNResearch project is to understand how to make ANNs effective (high success rate) and efficient (in terms of runtime and memory). Since I'm building these from scratch, I have control of every component of the ANN, and can test every one individually to see what makes them tick. 

And the final goal is to see what kinds of problems ANNs can and can't solve, and the training sizes required to be effective. 

README in progress...
