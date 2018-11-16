A=[[1,1,1];[0,1,1];[1,0,0];[0,1,0];[1,0,0];[1,0,1];[1,1,1];[1,1,1];[1,0,1];[0,2,0];[0,1,1]];

% question a
[U,S,V] = svd(A);


%output
disp('Question a: ');
disp('U=');
disp(U);
disp('S=');
disp(S);
disp('V=');
disp(V);


%question b
query=[0,0,0,0,0,1,0,0,0,1,1];


dotProductSVD=query*U*S*V';

%the dot product of q and A.
dotProduct=query*A;

%output
disp('Question b: ');
disp('The inner product scores of d1, d2 and d3 using the decomposed matrices =');
disp(dotProductSVD);

disp('The dot product of q and A =');
disp(dotProduct);

disp('The difference =');
disp(dotProductSVD-dotProduct);


%question c
U2=U(:,1:2);
S2=S(1:2,1:2);
V2=V(:,1:2);

A2=U2*S2*V2';


%output
disp('Question c: ');
disp('U2=');
disp(U2);
disp('S2=');
disp(S2);
disp('V2=');
disp(V2);
disp('A2=');
disp(A2);




%question d
q2=(query*U2/S2);

realV2=V*S'*U'*U2*inv(S2)

%output
disp('Question d: ');
disp('The 2d query is :');
disp(q2);
disp('The 2d d1,d2,d3 :');
disp(V2);




%question e
innerProductOfe=q2*V2';

innerProductOfe2=query*A2;

%output
disp('Question e: ');
disp('The inner product of d1, d2 and d3 to q is :');
disp(innerProductOfe);

disp('The inner product of A2 to q is :');
disp(innerProductOfe2);







