lexer grammar PLI;

PLISSTRING : ('"' (~'"')* '"')+;
WS : [ \t\r\n]+ -> skip ;

//fragment CHARACTERS: LETTER | DIGIT | SYMBOL | SPACE;
//fragment LETTER: LOWER | UPPER;
//fragment LOWER: [a-z];
//fragment UPPER: [A-Z];
//fragment DIGIT: [0-9];
//fragment SPACE: ' ' | '\t';
//fragment SYMBOL: '!' | '#'..'/' | ':'..'@' | '['..'`' | '{'..'~';

