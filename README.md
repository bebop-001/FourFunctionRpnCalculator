This is a simple 4-function RPN console interactive 
calculator implemented in about 70 lines of Kotlin.

RPN (Reverse Polish Notation) is different than 
Algabric calculators in that the operators come after
the operand.  For instance instead of "1 + 1 =" you would
enter "1 1 +"  The resulting syntax is much easier to 
parse.

If you want to see a stack trace of the operations,
your first token should be "trace" for example:<br>
>&gt; trace 1 1 +<br/>
Trace start [1, 1, +]:[]<br/>
Trace > [1, +], token="1", []<br/>
Trace < [1, +], [1.0]<br/>
Trace > [+], token="1", [1.0]<br/>
Trace < [+], [1.0, 1.0]<br/>
Trace > [], token="+", [1.0, 1.0]<br/>
Trace < [], [2.0]<br/>
[2.0]<br/>
&gt; <br/>

Use "q" to exit

I also implemented comments.  A comment starts with "#_" and
ends with "_#".

