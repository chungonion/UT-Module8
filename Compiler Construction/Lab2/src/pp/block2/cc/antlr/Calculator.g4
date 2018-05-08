grammar Calculator;


expr: '(' expr ')'                 #bracketExpr
    | '-' expr                     #negateExpr
    | <assoc=right> expr '^' expr  #expExpr
    | expr '*' expr                #multExpr
    | expr '+' expr                #addExpr
    | expr '-' expr                #subExpr
    | DIGIT                        #numberExpr
    ;

DIGIT: '0'..'9';


// ignore whitespace
WS : [ \t\n\r] -> skip;

// everything else is a typo
TYPO : ~('(' | ')' | '-' | '^' | '*' | '+' | [0-9]);