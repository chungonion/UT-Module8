lexer grammar Lalaland;

LA : LA_PART;
LALA: LA_PART LA_PART;
LALALALI: LA_PART LA_PART LA_PART LI_PART;


fragment LA_PART : 'L' 'a'+ ' '*;
fragment LI_PART : 'L' 'i' ' '*;

